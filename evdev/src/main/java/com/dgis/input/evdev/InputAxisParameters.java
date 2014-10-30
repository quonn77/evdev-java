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

/**
 * Represents configurable parameters of an input axis. set*() should affect the value in the device.
 * <p/>
 * Copyright (C) 2009 Giacomo Ferrari
 *
 * @author Giacomo Ferrari
 */

interface InputAxisParameters {

    int getValue();

    void setValue(int value);

    int getMin();

    void setMin(int min);

    int getMax();

    void setMax(int max);

    int getFuzz();

    void setFuzz(int fuzz);

    int getFlat();

    void setFlat(int flat);

}
