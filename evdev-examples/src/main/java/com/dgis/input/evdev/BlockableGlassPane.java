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


import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * 
 *  A JPanel to be used as a glass pane in order to let intercept user input (and block in case)
 * 
 *        The {@link BlockableGlassPane} class add hook to intercept user input. The class could block this input (mouse
 *        click, key press) or propagate it to the underlying component according to class configuration
 * 
 *        To block user input event propagation you have to invoke on the class instance the following method:<br>
 *        <br>
 *        <code>
 *        enableInputBlock();
 *        </code> <br>
 *        <br>
 *        Otherwise to enable user input event propagation you have to invoke this one:<br>
 *        <br>
 *        <code>
 *        disableInputBlock();
 *        </code>
 * 
 * 
 *
 * @author Alessio Iannone
 * Created on 26/mar/2015
 * @version $Id: BlockableGlassPane.java 13196 2015-09-07 10:59:13Z sccp_ian $
 *
 */
public class BlockableGlassPane extends JPanel
        implements MouseListener, MouseMotionListener, FocusListener, ComponentListener {
    // helpers for redispatch logic
    Toolkit toolkit;

    JMenuBar menuBar;

    Container contentPane;

    boolean inDrag = false;

    // trigger for redispatching (allows external control)
    boolean needToRedispatch = false;

    /**
     * Same as new BlockableGlassPane(null,cp,true)
     * 
     * @param cp the Container on which the GlassPane is applied
     */
    public BlockableGlassPane(Container cp) {
        this(null, cp, true);
    }

    /**
     * 
     * @param mb The menubar on which eventually we have to propagate events
     * @param cp the Container on which the GlassPane is applied
     * @param enableInputBlock true to block any user input on the underlying container, false otherwise
     */
    public BlockableGlassPane(JMenuBar mb, Container cp, boolean enableInputBlock) {
        toolkit = Toolkit.getDefaultToolkit();
        toolkit.setDynamicLayout(true);
        menuBar = mb;
        contentPane = cp;
        if (enableInputBlock)
            enableInputBlock();
    }

    /**
     * Disable blocking of user input event. This will enable again action on the underlying component
     */
    public void disableInputBlock() {
        removeMouseListener(this);
        removeMouseMotionListener(this);
        removeFocusListener(this);
        removeComponentListener(this);
    }

    /**
     * This will block any action on the underlying components blocking event propagation. So, for example, clicking on
     * a button will not generate any action.
     */
    public void enableInputBlock() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addFocusListener(this);
        addComponentListener(this);
    }

    public void setVisible(boolean v) {
        // Make sure we grab the focus so that key events don't go astray.
        if (v)
            requestFocus();
        super.setVisible(v);
    }

    // Once we have focus, keep it if we're visible
    public void focusLost(FocusEvent fe) {
        if (isVisible())
            requestFocus();
    }

    /**
     * Ask to repaint the content pane if any
     * 
     * @param g
     */
    protected void repaintContainer(Graphics g) {
        if (contentPane != null) {
            contentPane.paintComponents(g);
        }
    }

    public void focusGained(FocusEvent fe) {
    }

    // We only need to re-dispatch if we're not visible, but having full control
    // over this might prove handy.
    public void setNeedToRedispatch(boolean need) {
        needToRedispatch = need;
    }

    /*
     * (Based on code from the Java Tutorial) We must forward at least the mouse drags that started with mouse presses
     * over the check box. Otherwise, when the user presses the check box then drags off, the check box isn't disarmed
     * -- it keeps its dark gray background or whatever its L&F uses to indicate that the button is currently being
     * pressed.
     */
    public void mouseDragged(MouseEvent e) {
        if (needToRedispatch)
            redispatchMouseEvent(e);
    }

    /**
     * 
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (needToRedispatch)
            redispatchMouseEvent(e);
    }

    public void mouseClicked(MouseEvent e) {
        if (needToRedispatch)
            redispatchMouseEvent(e);
    }

    public void mouseEntered(MouseEvent e) {
        if (needToRedispatch)
            redispatchMouseEvent(e);
    }

    public void mouseExited(MouseEvent e) {
        if (needToRedispatch)
            redispatchMouseEvent(e);
    }

    public void mousePressed(MouseEvent e) {
        if (needToRedispatch)
            redispatchMouseEvent(e);
    }

    public void mouseReleased(MouseEvent e) {
        if (needToRedispatch) {
            redispatchMouseEvent(e);
            inDrag = false;
        }
    }

    private void redispatchMouseEvent(MouseEvent e) {
        boolean inButton = false;
        boolean inMenuBar = false;
        Point glassPanePoint = e.getPoint();
        Component component = null;
        Container container = contentPane;
        Point containerPoint = SwingUtilities.convertPoint(this, glassPanePoint, contentPane);
        int eventID = e.getID();

        if (containerPoint.y < 0 && menuBar != null) {
            inMenuBar = true;
            container = menuBar;
            containerPoint = SwingUtilities.convertPoint(this, glassPanePoint, menuBar);
            testForDrag(eventID);
        }

        // XXX: If the event is from a component in a popped-up menu,
        // XXX: then the container should probably be the menu's
        // XXX: JPopupMenu, and containerPoint should be adjusted
        // XXX: accordingly.
        component = SwingUtilities.getDeepestComponentAt(container, containerPoint.x, containerPoint.y);

        if (component == null) {
            return;
        } else {
            inButton = true;
            testForDrag(eventID);
        }

        if (inMenuBar || inButton || inDrag) {
            Point componentPoint = SwingUtilities.convertPoint(this, glassPanePoint, component);
            component.dispatchEvent(new MouseEvent(component, eventID, e.getWhen(), e.getModifiers(), componentPoint.x,
                    componentPoint.y, e.getClickCount(), e.isPopupTrigger()));
        }

    }

    private void testForDrag(int eventID) {
        if (eventID == MouseEvent.MOUSE_PRESSED) {
            inDrag = true;
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentShown(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentHidden(ComponentEvent e) {
        // TODO Auto-generated method stub

    }
}
