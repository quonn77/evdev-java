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


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.dgis.input.evdev.devices.EvdevRelativeMouseFilter;
import com.dgis.input.evdev.devices.IRelativeMouseListener;


/**
 * Work in progress. This is a test clss...
 *
 *
 * @author Andrea Picchiani
 *
 */
public class EvdevRelativeMouseTest {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: EvdevDeviceTest /dev/input/event*");
            System.exit(1);
        }

        
        Robot robot=null;
        try {
            robot = new Robot();
//            robot.mouseMove(0, 0);
        } catch (AWTException e1) {
            e1.printStackTrace();
            
        }

        final Robot localRobot = robot;
        String fn = args[0];
        System.out.println(fn);
        try {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            final TestFrame tf = new TestFrame();
            tf.setLocation(1921, 0);
            EvdevRelativeMouseFilter mouseHandler = new EvdevRelativeMouseFilter(fn);
//            mouseHandler.getDevice().grab();
//            mouseHandler.addMouseListener(tf.getBgp());
            mouseHandler.addMouseListener(new IRelativeMouseListener() {

                private int getMouseEvent(MouseButton btn) {
                    switch(btn){
                        case LEFT:return MouseEvent.BUTTON1_DOWN_MASK;
                        case MIDDLE: return MouseEvent.BUTTON3_DOWN_MASK;
                        case RIGHT: return MouseEvent.BUTTON2_DOWN_MASK;
                    }
                    return 0;
                }

                @Override
                public void mouseMovedX(int x) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mouseMovedY(int y) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mousePressed(MouseButton btn) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mouseReleased(MouseButton btn) {
                    // TODO Auto-generated method stub
                    
                }

            });
            SwingUtilities.invokeLater(() -> {

                tf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                tf.setVisible(true);
            });
            mouseHandler.waitHandler();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
