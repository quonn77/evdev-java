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
 */
package com.dgis.input.evdev;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Represents configurable parameters of an input axis. set*() should affect the value in the device.
 * <p/>
 * Copyright (C) 2009 Giacomo Ferrari
 *
 * @author Giacomo Ferrari
 */
@ThreadSafe
class InputAxisParameters {

    private final EventDevice device;
    private final int axis;

    private static final int VALUE_INDEX = 0;
    private static final int MIN_INDEX = 1;
    private static final int MAX_INDEX = 2;
    private static final int FUZZ_INDEX = 3;
    private static final int FLAT_INDEX = 4;

    public InputAxisParameters(EventDevice device, int axis) {
        this.device = device;
        this.axis = axis;
    }

    private int readStatus(int i) {
        if (i < 0 || i > 4) {
            throw new IllegalArgumentException("Field index has to be between 0 and 4");
        }
        int[] resp = new int[5];
        synchronized (this) {
            device.ioctlEVIOCGABS(device.getDevicePath(), resp, axis);
        }
        return resp[i];
    }

    public int getValue() {
        return readStatus(VALUE_INDEX);
    }

    public int getMin() {
        return readStatus(MIN_INDEX);
    }

    public int getMax() {
        return readStatus(MAX_INDEX);
    }

    public int getFuzz() {
        return readStatus(FUZZ_INDEX);
    }

    public int getFlat() {
        return readStatus(FLAT_INDEX);
    }

    @Override
    public String toString() {
        return "Value: " + getValue() + " Min: " + getMin() + " Max: "
                + getMax() + " Fuzz: " + getFuzz() + " Flat: " + getFlat();
    }
}
