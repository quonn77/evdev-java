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
package com.dgis.input.evdev.devices;

/**
 * Represents the state of a joystick (buttons and axes)
 * <p/>
 * Copyright (C) 2009 Giacomo Ferrari
 *
 * @author Giacomo Ferrari
 */

public class JoystickState {
    private final int numButtons;
    private final int numAxes;
    private final boolean buttonStates[];
    private final int axisStates[];
    private final int axisMinValue[];
    private final int axisMaxValue[];

    JoystickState(int numButtons, int numAxes) {
        this.numButtons = numButtons;
        this.numAxes = numAxes;
        buttonStates = new boolean[numButtons];
        axisStates = new int[numAxes];
        axisMinValue = new int[numAxes];
        axisMaxValue = new int[numAxes];
    }

    /**
     * Gets the number of buttons reported by this joystick.
     *
     * @return The number of buttons reported.
     */
    public int getNumButtons() {
        return numButtons;
    }

    /**
     * Gets the number of axes reported by this joystick.
     *
     * @return The number of axes reported.
     */
    public int getNumAxes() {
        return numAxes;
    }

    /**
     * Queries the state of a joystick button.
     *
     * @param button The button number to check. Valid ranges are 0 to getNumButtons()-1.
     * @return The state of the button.
     */
    public boolean getButtonState(int button) {
        return buttonStates[button];
    }

    /**
     * Queries the state of a joystick axis.
     *
     * @param axis The axis number to check. Valid ranges are 0 to getNumAxes()-1.
     * @return The state of the axis, typically in the range +-32768.
     * @see JoystickState#getAxisMaxValue(int)
     * @see JoystickState#getAxisMinValue(int)
     */
    public int getAxisState(int axis) {
        return axisStates[axis];
    }

    /**
     * Queries the smallest value ever seen on a given axis for calibration purposes.
     *
     * @param axis The axis to check. Valid ranges are 0 to getNumButtons()-1.
     * @return The smallest value ever seen on this axis.
     */
    public int getAxisMinValue(int axis) {
        return axisMinValue[axis];
    }

    /**
     * Queries the largest value ever seen on a given axis for calibration purposes.
     *
     * @param axis The axis to check. Valid ranges are 0 to getNumButtons()-1.
     * @return The largest value ever seen on this axis.
     */
    public int getAxisMaxValue(int axis) {
        return axisMaxValue[axis];
    }

    void setButtonState(int button, boolean state) {
        buttonStates[button] = state;
    }

    void setAxisState(int axis, int state) {
        axisStates[axis] = state;
    }

    void setAxisMinValue(int axis, int min) {
        axisMinValue[axis] = min;
    }

    void setAxisMaxValue(int axis, int max) {
        axisMaxValue[axis] = max;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("Buttons: [ ");
        for (boolean b : buttonStates) buf.append(b ? 1 : 0).append(" ");
        buf.append("] Axes: [ ");
        for (int a : axisStates) buf.append(a).append(" ");
        buf.append("]");
        return buf.toString();
    }
}
