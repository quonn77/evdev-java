/**
 * This file is part of evdev-java - Java implementation.
 *
 * evdev-java - Java implementation is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * evdev-java - Java implementation is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with evdev-java - Java implementation.  If not, see <http://www.gnu.org/licenses/>.
 * */
package com.dgis.input.evdev.devices;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dgis.input.evdev.EventDevice;
import com.dgis.input.evdev.InputEvent;
import com.dgis.input.evdev.InputListener;
import com.dgis.input.evdev.devices.IMouseListener.MouseButton;

/**
 * Filter input event and handle them like mouse events.
 *
 * @author Andrea Picchiani - Rheinmetall Italia S.p.A.
 *
 */
public class EvdevRelativeMouseFilter implements InputListener {
    private final static Logger logger = LoggerFactory.getLogger(EvdevRelativeMouseFilter.class);
    private EventDevice device;

    private ArrayList<IRelativeMouseListener> listeners;

    private boolean pressed;

    /**
     * 
     */
    public EvdevRelativeMouseFilter(EventDevice dev) {
        this.device = dev;
        this.pressed = false;
        this.listeners = new ArrayList<>();
        setupDevice();
    }

    public EvdevRelativeMouseFilter(String device) throws IOException {
        this(new EventDevice(new File(device)));
    }

    /**
     * 
     */
    private void setupDevice() {
        Map<Integer, List<Integer>> supportedEvents = device.getSupportedEvents();
        List<Integer> supportedKeys = supportedEvents.get((int) InputEvent.EV_KEY);
        int numButtons = supportedKeys == null ? 0 : supportedKeys.size();
        System.out.println("Device " +device.getDeviceName());
        System.out.println("Detected " + numButtons + " buttons.");
        //this.device.grab();
        this.device.addListener(this);
    }

    /**
     * Retrieve the underlying {@link EventDevice}
     * 
     * @return the device
     */
    public EventDevice getDevice() {
        return device;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void event(InputEvent e) {
        switch (e.type) {
            case EV_KEY:
                if (e.value == 1) {
                    pressed = true;
                    notifyMousePressed(getMouseButton(e));
                } else {
                    pressed = false;
                    notifyMouseReleased(getMouseButton(e));
                }
                break;
            case EV_REL:
                // System.out.println("Axis Parameter:"+device.getAxisParameters(e.code));
                if (e.code == InputEvent.REL_X) {
                    // X AXIS
                    notifyMouseMoveX(e.value);
                } else if (e.code == InputEvent.REL_Y) {
                    notifyMouseMoveY(e.value);
                } 
//                else if (e.code == InputEvent.REL_WHEEL) {
//                    notifyMouseWheel(getDirection(e), Math.abs(e.value));
//                }
                break;

        }
//        if (dispatchPosition && !pressed) {
//            notifyMousePosition(relativePosition);
//        } 
//        else if (dispatchPosition && pressed) {
//            notifyMouseDragged(relativePosition);
//        }

    }

    /**
     * @param Position
     */
//    private synchronized void notifyMouseDragged(Point pos) {
//        listeners.forEach((ml) -> {
//            ml.mouseDragged(pos.x, pos.y);
//        });
//    }
    
    private synchronized void notifyMouseMoveX(int x) {
        listeners.forEach((ml) -> {
            ml.mouseMovedX(x);
        });
    }
    
    private synchronized void notifyMouseMoveY(int y) {
        listeners.forEach((ml) -> {
            ml.mouseMovedY(y);
        });
    }

    /**
     * @param direction
     * @param abs
     */
//    private synchronized void notifyMouseWheel(WheelDirection direction, int abs) {
//        listeners.forEach((ml) -> {
//            ml.mouseWheel(direction, abs, relativePosition.x, relativePosition.y);
//        });
//    }

    /**
     * @param e
     * @return
     */
//    private WheelDirection getDirection(InputEvent e) {
//        if (e.value > 0)
//            return WheelDirection.UP;
//        return WheelDirection.DOWN;
//    }

    /**
     * @param mouseButton
     */
    private synchronized void notifyMouseReleased(MouseButton mouseButton) {
        listeners.forEach((ml) -> {
            ml.mouseReleased(mouseButton);
        });
    }

    /**
     * @param mouseButton
     */
    private synchronized void notifyMousePressed(MouseButton mouseButton) {
        listeners.forEach((ml) -> {
                ml.mousePressed(mouseButton);
        });
    }

    /**
     * @param e
     * @return
     */
    private MouseButton getMouseButton(InputEvent e) {
        switch (e.code) {
            case InputEvent.BTN_LEFT:
                return MouseButton.LEFT;
            case InputEvent.BTN_RIGHT:
                return MouseButton.RIGHT;
            case InputEvent.BTN_MIDDLE:
                return MouseButton.MIDDLE;
            case InputEvent.BTN_SIDE:
                return MouseButton.LSIDE_BTN;
            case InputEvent.BTN_EXTRA:
                return MouseButton.RSIDE_BTN;
        }
        return null;
    }

    /**
     * Register the given {@link IMoveMouseListener} as a listener of the underlying events
     * 
     * @param listener
     */
    public synchronized void addMouseListener(IRelativeMouseListener listener) {
        listeners.add(listener);
    }

    /**
     * Unregister the given {@link IMoveMouseListener} from the listener list
     * 
     * @param listener
     */
    public synchronized void removeMouseListener(IRelativeMouseListener listener) {
        listeners.remove(listener);
    }

    /**
     * Create a waiting thread and join to it. This is useful only if you are using this as a console application
     */
    public void waitHandler() {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
