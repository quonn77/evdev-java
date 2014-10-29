package com.dgis.input.evdev;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Copyright (C) 2009 Giacomo Ferrari
 * This file is part of evdev-java.
 *  evdev-java is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  evdev-java is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with evdev-java.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Represents a connection to a Linux Evdev device.
 *
 * For additional info, see input/input.txt and input/input-programming.txt in the Linux kernel Documentation.
 * IMPORTANT: If you want higher-level access for your joystick/pad/whatever, check @see com.dgis.input.evdev.devices
 * for useful drivers to make your life easier!
 * Copyright (C) 2009 Giacomo Ferrari
 *
 * @author Giacomo Ferrari
 */
public class EventDevice {

    private final NativeEventDevice nativeEventDevice;

    /** system architecture, for arch-specific struct handling */
    private String arch = "amd64";

    /**
     * Notify these guys about input events.
     */
    private List<InputListener> listeners = new ArrayList<InputListener>();

    /**
     * Device filename we're using.
     */
    private final String device;

    /**
     * Attached to device we're using.
     */
    private FileChannel deviceInput;
    private ByteBuffer inputBuffer;


    /**
     * When this is true, the reader thread should terminate ASAP.
     */
    private volatile boolean terminate = false;

    /**
     * This thread repeatedly calls readEvent().
     */
    private Thread readerThread;

    private short[] idResponse = new short[4];

    private int evdevVersionResponse;

    private String deviceNameResponse;

    /**
     * Maps supported event types (keys) to lists of supported event codes.
     */
    private HashMap<Integer, List<Integer>> supportedEvents = new HashMap<Integer, List<Integer>>();


    /**
     * Ensures only one instance of InputAxisParameters is created for each axis (more would be wasteful).
     */
    private HashMap<Integer, InputAxisParameters> axisParams = new HashMap<Integer, InputAxisParameters>();

    /**
     * Create an EventDevice by connecting to the provided device filename.
     * If the device file is accessible, open it and begin listening for events.
     *
     * @param device The path to the device file. Usually one of /dev/input/event*
     * @throws IOException If the device is not found, or is otherwise inaccessible.
     */
    public EventDevice(String device) throws IOException {
        // check for embedded library:
        arch = System.getProperty("os.arch");
        System.err.println("EventDevice: System: " + arch);
        if (arch.equals("arm")) {
            inputBuffer = ByteBuffer.allocate(InputEvent.STRUCT_SIZE_BYTES_ARM);
        } else {
            inputBuffer = ByteBuffer.allocate(InputEvent.STRUCT_SIZE_BYTES);
        }
        String libPath = "/NATIVE/" + arch + "/libevdev-java.so";
        libPath = "/evdev-native.so";
        System.err.println("EventDevice: libPath: " + libPath);
        InputStream in = EventDevice.class.getResourceAsStream(libPath);
        System.err.println("EventDevice: in: " + in);
        if (in != null) {
            final File nativeLibFile = File.createTempFile("libevdev-java", ".so");
            nativeLibFile.deleteOnExit();

            final OutputStream out = new BufferedOutputStream(new FileOutputStream(nativeLibFile));

            int len = 0;
            byte[] buffer = new byte[8192];
            while ((len = in.read(buffer)) > -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();

            System.load(nativeLibFile.getAbsolutePath());
        } else {
            System.err.println("EventDevice: falling back to java.library.path...");
            System.loadLibrary("evdev-java");
        }
        this.device = device;
        this.nativeEventDevice = new NativeEventDevice();
        inputBuffer.order(ByteOrder.LITTLE_ENDIAN);
        initDevice();
    }

    /**
     * Get various ID info. Then, open the file, get the channel, and start the reader thread.
     *
     * @throws IOException
     */
    private void initDevice() throws IOException {
      if (!nativeEventDevice.ioctlGetID(device, idResponse)) {
            System.err.println("WARN: couldn't get device ID: " + device);
            Arrays.fill(idResponse, (short) 0);
        }
        evdevVersionResponse = nativeEventDevice.ioctlGetEvdevVersion(device);
        byte[] devName = new byte[255];
        if (nativeEventDevice.ioctlGetDeviceName(device, devName)) {
            deviceNameResponse = new String(devName);
        } else {
            System.err.println("WARN: couldn't get device name: " + device);
            deviceNameResponse = "Unknown Device";
        }

        readSupportedEvents();

        FileInputStream fis = new FileInputStream(device);
        deviceInput = fis.getChannel();

        readerThread = new Thread() {
            @Override
            public void run() {
                while (!terminate) {
                    InputEvent ev = readEvent();
                    distributeEvent(ev);
                }
            }
        };
        readerThread.setDaemon(true); /* We don't want this thread to prevent the JVM from terminating */

        readerThread.start();
    }

    /**
     * Get supported events from device, and place into supportedEvents.
     * Adapted from evtest.c.
     */
    private void readSupportedEvents() {
        //System.out.println("Detecting device capabilities...");
        long[][] bit = new long[InputEvent.EV_MAX][NBITS(InputEvent.KEY_MAX)];
        nativeEventDevice.ioctlEVIOCGBIT(device, bit[0], 0, bit[0].length);
        /* Loop over event types */
        for (int i = 0; i < InputEvent.EV_MAX; i++) {
            if (testBit(bit[0], i)) { /* Is this event supported? */
                //System.out.printf("  Event type %d\n", i);
                if (i == 0) continue;
                ArrayList<Integer> supportedTypes = new ArrayList<Integer>();
                nativeEventDevice.ioctlEVIOCGBIT(device, bit[i], i, InputEvent.KEY_MAX);
				/* Loop over event codes for type */
                for (int j = 0; j < InputEvent.KEY_MAX; j++)
                    if (testBit(bit[i], j)) { /* Is this event code supported? */
                        //System.out.printf("    Event code %d\n", j);
                        supportedTypes.add(j);
                    }
                supportedEvents.put(i, supportedTypes);
            }
        }
    }

    private boolean testBit(long[] array, int bit) {
        return ((array[LONG(bit)] >>> OFF(bit)) & 1) != 0;
    }

    private int LONG(int x) {
        return x / (64);
    }

    private int OFF(int x) {
        return x % (64);
    }

    private int NBITS(int x) {
        return ((((x) - 1) / (8 * 8)) + 1);
    }

    /**
     * Distribute an event to all registered listeners.
     *
     * @param ev The event to distribute.
     */
    private void distributeEvent(InputEvent ev) {
        synchronized (listeners) {
            for (InputListener il : listeners) {
                il.event(ev);
            }
        }
    }

    /**
     * Obtain an InputEvent from the input channel. Delegate to InputEvent for parsing.
     *
     * @return
     */
    private InputEvent readEvent() {
        try {
			/* Read exactly the amount of bytes specified by InputEvent.STRUCT_SIZE_BYTES (intrinsic size of inputBuffer)*/
            inputBuffer.clear();
            while (inputBuffer.hasRemaining()) deviceInput.read(inputBuffer);

			/* We want to read now */
            inputBuffer.flip();
			
			/* Delegate parsing to InputEvent.parse() */
            return InputEvent.parse(inputBuffer.asShortBuffer(), device, arch);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        terminate = true;
        try {
            readerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            deviceInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public short getBusID() {
        return idResponse[InputEvent.ID_BUS];
    }

    public String getDeviceName() {
        return deviceNameResponse;
    }

    public short getProductID() {
        return idResponse[InputEvent.ID_PRODUCT];
    }

    public Map<Integer, List<Integer>> getSupportedEvents() {
        return supportedEvents;
    }

    public short getVendorID() {
        return idResponse[InputEvent.ID_VENDOR];
    }

    public int getEvdevVersion() {
        return evdevVersionResponse;
    }

    public short getVersionID() {
        return idResponse[InputEvent.ID_VERSION];
    }

    public InputAxisParameters getAxisParameters(int axis) {
        InputAxisParameters params;
        if ((params = axisParams.get(axis)) == null) {
            params = new InputAxisParametersImpl(this, axis);
            axisParams.put(axis, params);
        }
        return params;
    }

    public void addListener(InputListener list) {
        synchronized (listeners) {
            listeners.add(list);
        }
    }

    public void removeListener(InputListener list) {
        synchronized (listeners) {
            listeners.remove(list);
        }
    }


    public String getDevicePath() {
        return device;
    }

    public boolean ioctlEVIOCGABS(String device, int[] resp, int axis) {
        return nativeEventDevice.ioctlEVIOCGABS(device, resp, axis);
    }

}

