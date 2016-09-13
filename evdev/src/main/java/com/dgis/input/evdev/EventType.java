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

public enum EventType {

    EV_SYN((short) 0x00),
    EV_KEY((short) 0x01),
    EV_REL((short) 0x02),
    EV_ABS((short) 0x03),
    EV_MSC((short) 0x04),
    EV_SW((short) 0x05),
    EV_LED((short) 0x11),
    EV_SND((short) 0x12),
    EV_REP((short) 0x14),
    EV_FF((short) 0x15),
    EV_PWR((short) 0x16),
    EV_FF_STATUS((short) 0x17),
    EV_MAX((short) 0x1f),
    EV_CNT((short) (0x1f + 1));

    private static final Map<Short, EventType> VALUE_LOOKUP = new HashMap<>();

    static {
        for (EventType eventType : EventType.values()) {
            VALUE_LOOKUP.put(eventType.value, eventType);
        }
    }

    @Getter
    private final short value;

    EventType(short value) {
        this.value = value;
    }

    public static EventType valueOf(short value) {
        if (VALUE_LOOKUP.containsKey(value)) return VALUE_LOOKUP.get(value);
        throw new IllegalArgumentException(format("Unknown event type: %s", value));
    }

}
