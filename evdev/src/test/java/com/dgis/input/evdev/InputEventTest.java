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

import static com.dgis.input.evdev.EventType.EV_KEY;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.ShortBuffer;

import org.junit.Test;

public class InputEventTest {

    @Test
    public void testParsingSimpleValue() throws IOException {
        ShortBuffer buffer = ShortBuffer.allocate(12);
        // time seconds
        write(buffer, 1, 2, 0, 0);
        // time micro seconds
        write(buffer, 4, 5, 0, 0);
        // type
        buffer.put(EV_KEY.getValue());
        // code
        buffer.put((short) 9);
        // value
        write(buffer, 10, 11);

        buffer.rewind();

        InputEvent event = InputEvent.parse(buffer, "source", "arch");
        assertThat(event.source).isEqualTo("source");
        assertThat(event.timeSec).isEqualTo((2L << 16) + 1);
        assertThat(event.timeMicroSec).isEqualTo((5L << 16) + 4);
        assertThat(event.type).isEqualTo(EV_KEY);
        assertThat(event.code).isEqualTo((short)9);
        assertThat(event.value).isEqualTo(10 + (11 << 16));
    }

    private void write(ShortBuffer buffer, int s0, int s1, int s2, int s3) {
        buffer.put((short) s0);
        buffer.put((short) s1);
        buffer.put((short) s2);
        buffer.put((short) s3);
    }
    private void write(ShortBuffer buffer, int s0, int s1) {
        buffer.put((short) s0);
        buffer.put((short) s1);
    }

}
