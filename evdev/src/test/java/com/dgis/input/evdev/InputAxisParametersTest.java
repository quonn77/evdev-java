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

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InputAxisParametersTest {

    @Test
    public void readValue() {
        EventDevice eventDevice = mockEventDevice(0, 4);
        InputAxisParameters axisParameters = new InputAxisParameters(eventDevice, 0);
        assertThat(axisParameters.getValue()).isEqualTo(4);
    }

    @Test
    public void readMin() {
        EventDevice eventDevice = mockEventDevice(1, 5);
        InputAxisParameters axisParameters = new InputAxisParameters(eventDevice, 0);
        assertThat(axisParameters.getMin()).isEqualTo(5);
    }

    @Test
    public void readMax() {
        EventDevice eventDevice = mockEventDevice(2, 6);
        InputAxisParameters axisParameters = new InputAxisParameters(eventDevice, 0);
        assertThat(axisParameters.getMax()).isEqualTo(6);
    }

    @Test
    public void readFuzz() {
        EventDevice eventDevice = mockEventDevice(3, 7);
        InputAxisParameters axisParameters = new InputAxisParameters(eventDevice, 0);
        assertThat(axisParameters.getFuzz()).isEqualTo(7);
    }

    @Test
    public void readFlat() {
        EventDevice eventDevice = mockEventDevice(4, 8);
        InputAxisParameters axisParameters = new InputAxisParameters(eventDevice, 0);
        assertThat(axisParameters.getFlat()).isEqualTo(8);
    }

    private EventDevice mockEventDevice(final int position, final int value) {
        EventDevice eventDevice = mock(EventDevice.class);
        final ArgumentCaptor<int[]> response = ArgumentCaptor.forClass(int[].class);
        when(eventDevice.ioctlEVIOCGABS(any(String.class), response.capture(), any(Integer.class)))
                .then(new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        response.getValue()[position] = value;
                        return true;
                    }
                });
        return eventDevice;
    }

}
