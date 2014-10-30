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

class InputAxisParametersImpl implements InputAxisParameters {

    private EventDevice device;
    private int axis;

    private int value, min, max, fuzz, flat;

    public InputAxisParametersImpl(EventDevice device, int axis) {
        this.device = device;
        this.axis = axis;
        readStatus();
    }

    /**
     * Repopulate values stored in this class with values read from the device.
     */
    private void readStatus() {
        int[] resp = new int[5];
        device.ioctlEVIOCGABS(device.getDevicePath(), resp, axis);
        value = resp[0];
        min = resp[1];
        max = resp[2];
        fuzz = resp[3];
        flat = resp[4];
    }

    /**
     * Repopulate values stored in the device with values read from this class.
     */
    private void writeStatus() {
        throw new Error("Not implemented yet!");
    }

    public int getValue() {
        synchronized (this) {
            readStatus();
            return value;
        }
    }

    public void setValue(int value) {
        synchronized (this) {
            this.value = value;
            writeStatus();
        }
    }

    public int getMin() {
        synchronized (this) {
            readStatus();
            return min;
        }
    }

    public void setMin(int min) {
        synchronized (this) {
            this.min = min;
            writeStatus();
        }
    }

    public int getMax() {
        synchronized (this) {
            readStatus();
            return max;
        }
    }

    public void setMax(int max) {
        synchronized (this) {
            this.max = max;
            writeStatus();
        }
    }

    public int getFuzz() {
        synchronized (this) {
            readStatus();
            return fuzz;
        }
    }

    public void setFuzz(int fuzz) {
        synchronized (this) {
            this.fuzz = fuzz;
            writeStatus();
        }
    }

    public int getFlat() {
        synchronized (this) {
            readStatus();
            return flat;
        }
    }

    public void setFlat(int flat) {
        synchronized (this) {
            this.flat = flat;
            writeStatus();
        }
    }

    @Override
    public String toString() {
        return "Value: " + getValue() + " Min: " + getMin() + " Max: "
                + getMax() + " Fuzz: " + getFuzz() + " Flat: " + getFlat();
    }
}
