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


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;


/**
 * //TODO First sentence till the "." is a brief description.
 * //The rest of the phrase is the detailed description.
 *
 *
 * @author quonn - Rheinmetall Italia S.p.A.
 *
 */
public class TestFrame extends JFrame {
    

    /**
    * 
    */
    public TestFrame() {
        super("TestFrame");
        buildUI();
    }

    public Component findComponentUnderGlassPane(Point p, Component top){
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
    
    public Component findComponentUnderMouse(int x, int y){
        Window window = findWindow();
        Point p = new Point(x,y);
        SwingUtilities.convertPointFromScreen(p, window);
        return SwingUtilities.getDeepestComponentAt(window, p.x,p.y);
    }
    /**
     * @return
     */
    private Window findWindow() {
        for(Window w:Window.getWindows()){
            if(w.getMousePosition(true)!=null){
                return w;
            }
        }
        return null;
    }


    /**
     * 
     */
    private void buildUI() {
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0,0), "blankcursor");
        setCursor(blankCursor);
        
        JPanel jpnContainer = new JPanel();
        jpnContainer.setName("Container");
        JLabel jlblContent = new JLabel();
        jlblContent.setName("Content");
        JButton okButton =new JButton("OK");
        okButton.setName("okButton");
        okButton.addActionListener((ae)->{
            jlblContent.setText("Clicked");
        });
        
        JButton resetButton = new JButton("Reset");
        resetButton.setName("resetButton");
        resetButton.addActionListener((ae)->{
            jlblContent.setText("");
        });
        
        jpnContainer.add(resetButton);
        jpnContainer.add(okButton);
        jpnContainer.add(jlblContent);
        getContentPane().add(jpnContainer);
        
        ImageGlassPane bgp =new ImageGlassPane(this.getContentPane());
        bgp.setBackground(Color.BLACK);
        bgp.setAlphaValue(0.1f);
        setGlassPane(bgp);
        bgp.setVisible(true);
        pack();
        
    }
}
