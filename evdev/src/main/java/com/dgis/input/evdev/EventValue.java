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

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public enum EventValue {

    TT(1);

    private static final Map<Integer, EventValue> VALUE_LOOKUP = new HashMap<>();

    static {
        for (EventValue eventValue : EventValue.values()) {
            VALUE_LOOKUP.put(eventValue.value, eventValue);
        }
    }

    @Getter
    private final int value;

    EventValue(int value) {
        this.value = value;
    }

    public static EventValue valueOf(int value) {
        if (VALUE_LOOKUP.containsKey(value)) return VALUE_LOOKUP.get(value);
        throw new IllegalArgumentException(format("Unknown event value: %s", value));
    }



}
