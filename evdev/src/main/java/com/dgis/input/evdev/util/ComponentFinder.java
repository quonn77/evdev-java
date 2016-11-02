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
package com.dgis.input.evdev.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;

import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

/**
 * Find Component under mouse location
 *
 * @author  Alessio Iannone - Rheinmetall Italia S.p.A.
 *
 */
public final class ComponentFinder {
    
    private ComponentFinder(){
        ;
    }
    
    public final static Component findComponentUnderGlassPane(Point p, Component top){
        Component c = null;
        if(top.isShowing()){
            if(top instanceof RootPaneContainer){
                c = ((RootPaneContainer)top).getLayeredPane().findComponentAt(SwingUtilities.convertPoint(top, p, ((RootPaneContainer)top).getLayeredPane()));
            }else{
                c = ((Container)top).findComponentAt(p);
            }
        }
        return c;
    }
}
