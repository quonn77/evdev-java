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
import java.awt.Component;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.dgis.input.evdev.devices.EvdevMouseFilter;
import com.dgis.input.evdev.devices.IMouseListener;
import com.dgis.input.evdev.util.ComponentFinder;


/**
 * Work in progress. This is a test clss...
 *
 *
 * @author Alessio Iannone
 *
 */
public class EvdevMouseTest {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: EvdevJoystickTest /dev/input/event*");
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
        try {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            final TestFrame tf = new TestFrame();
            tf.setLocation(1921, 0);
            EvdevMouseFilter mouseHandler = new EvdevMouseFilter(fn);
            //mouseHandler.getDevice().grab();
            mouseHandler.addMouseListener(tf.getBgp());
            mouseHandler.addMouseListener(new IMouseListener() {

                @Override
                public void mouseMoved(int x, int y) {
                    System.out.println("mouseMoved() x=" + x + " y=" + y);
//                    localRobot.mouseMove(x, y);

                }

                @Override
                public void mouseDragged(int x, int y) {
                    System.out.println("mouseDragged() x=" + x + " y=" + y);

                }

                @Override
                public void mousePressed(MouseButton btn, int x, int y) {
                    Point okLocation = new Point(tf.getOkButton().getLocation());
                    SwingUtilities.convertPointToScreen(okLocation,tf);
                    System.out.println("mousePressed() " + btn + " X:" + x + " Y:" + y+" Frame location:"+tf.getLocation()+" ButtonOk Location "+okLocation);
                    Point p = new Point(x, y);
                    SwingUtilities.convertPointFromScreen(p, tf);
                    System.out.println("mousePressed() " + btn + " X:" + p.x + " Y:" + p.y);
                    
                    Component comp = ComponentFinder.findComponentUnderGlassPane(p, tf);
//                    comp.dispatchEvent(me);
                    if (comp instanceof JButton) {
                        SwingUtilities.invokeLater(() -> {
                            ((JButton) comp).doClick();
                            tf.repaint();
                        });
                    }
                    if(comp instanceof JComboBox){
                        
                    }
                    System.out.println("Component is " + (comp != null ? comp.getName() : "Unknown"));
                }

                private int getMouseEvent(MouseButton btn) {
                    switch(btn){
                        case LEFT:return MouseEvent.BUTTON1_DOWN_MASK;
                        case MIDDLE: return MouseEvent.BUTTON3_DOWN_MASK;
                        case RIGHT: return MouseEvent.BUTTON2_DOWN_MASK;
                    }
                    return 0;
                }

                @Override
                public void mouseReleased(MouseButton btn, int x, int y) {
                    System.out.println("mouseReleased() " + btn + " X:" + x + " Y:" + y);

                }

                @Override
                public void mouseWheel(WheelDirection dir, int velocity, int x, int y) {
                    System.out.println("mouseWheel() " + dir + " vel:" + velocity + " X:" + x + " Y:" + y);
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
