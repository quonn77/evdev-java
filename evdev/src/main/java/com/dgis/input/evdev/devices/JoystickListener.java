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
 * Listener for Joystick events.
 * Copyright (C) 2009 Giacomo Ferrari
 *
 * @author Giacomo Ferrari
 * @see com.dgis.input.evdev.EventDevice
 */

public interface JoystickListener {
    /**
     * Called whenever a joystick's axis changes state.
     *
     * @param axesChanged Bitmap of axis status. If the nth value in this array is true, then the nth axis changed state.
     * @param state       The updated joystick state.
     */
    void joystickMoved(boolean[] axesChanged, JoystickState state, String source);

    /**
     * Called whenever a joystick's button changes state.
     *
     * @param buttonsChanged Bitmap of button status. If the nth value in this array is true, then the nth button changed state.
     * @param state          The updated joystick state.
     */
    void buttonChanged(boolean[] buttonsChanged, JoystickState state, String source);
}
