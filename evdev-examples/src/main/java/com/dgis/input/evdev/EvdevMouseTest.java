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
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.dgis.input.evdev.devices.EvdevMouseFilter;
import com.dgis.input.evdev.devices.IMouseListener;
import com.dgis.input.evdev.util.ComponentFinder;

import javafx.scene.input.MouseButton;

/**
 * //TODO First sentence till the "." is a brief description.
 * //The rest of the phrase is the detailed description.
 *
 *
 * @author quonn - Rheinmetall Italia S.p.A.
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
            robot.mouseMove(0, 0);
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
            EvdevMouseFilter mouseHandler = new EvdevMouseFilter(fn);
//            mouseHandler.getDevice().grab();
            mouseHandler.addMouseListener(new IMouseListener() {

                @Override
                public void mouseMoved(int x, int y) {
                    System.out.println("mouseMoved() x=" + x + " y=" + y);
                    localRobot.mouseMove(x, y);

                }

                @Override
                public void mouseDragged(int x, int y) {
                    System.out.println("mouseDragged() x=" + x + " y=" + y);

                }

                @Override
                public void mousePressed(MouseButton btn, int x, int y) {
                    System.out.println("mousePressed() " + btn + " X:" + x + " Y:" + y);

                    Point p = new Point(x, y);

                    Component c = ComponentFinder.findComponentUnderGlassPane(p, tf);
                    if (c instanceof JButton) {
                        SwingUtilities.invokeLater(() -> {
                            ((JButton) c).doClick();
                            tf.repaint();
                        });
                    }
                    System.out.println("Component is " + (c != null ? c.getName() : "Unknown"));
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
