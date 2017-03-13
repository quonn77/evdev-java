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
 * */
package com.dgis.input.evdev.devices;

/**
 * //TODO First sentence till the "." is a brief description. 
 * //The rest of the phrase is the detailed description.
 *
 *
 * @author Andrea Picchiani - Rheinmetall Italia S.p.A.
 *
 */
public interface IRelativeMouseListener extends IMouseListener {
    
    public void mouseMovedX(int x);
    public void mouseMovedY(int y);
//    public void mouseDragged(int x, int y);
    public void mousePressed(MouseButton btn);
    public void mouseReleased(MouseButton btn);
//    public void mouseWheel(WheelDirection dir,int velocity,int x, int y);
}
