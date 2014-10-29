package com.dgis.input.evdev;

public class NativeEventDevice {

    native boolean ioctlGetID(String device, short[] resp);

    native int ioctlGetEvdevVersion(String device);

    native boolean ioctlGetDeviceName(String device, byte[] resp);

    native boolean ioctlEVIOCGBIT(String device, long[] resp, int start, int stop);

    native boolean ioctlEVIOCGABS(String device, int[] resp, int axis);

}
