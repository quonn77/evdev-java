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

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dgis.input.evdev.EventDevice;
import com.dgis.input.evdev.InputEvent;
import com.dgis.input.evdev.InputListener;
import com.dgis.input.evdev.devices.IMouseListener.MouseButton;
import com.dgis.input.evdev.devices.IMouseListener.WheelDirection;

/**
 * //TODO First sentence till the "." is a brief description.
 * //The rest of the phrase is the detailed description.
 *
 *
 * @author Alessio Iannone - Rheinmetall Italia S.p.A.
 *
 */
public class EvdevMouseFilter implements InputListener {
    private EventDevice device;

    private ArrayList<IMouseListener> listeners;

    private Point actualMousePosition;

    private boolean pressed;

    /**
     * 
     */
    public EvdevMouseFilter(EventDevice dev) {
        this.device = dev;
        this.pressed=false;
        this.listeners = new ArrayList<>();
        this.actualMousePosition = new Point(0, 0);
        setupDevice();
        
    }
    
    
    public EvdevMouseFilter(String device) throws IOException{
        this(new EventDevice(new File(device)));
    }

    
    /**
     * 
     */
    private void setupDevice() {
        Map<Integer, List<Integer>> supportedEvents = device.getSupportedEvents();
        List<Integer> supportedKeys = supportedEvents.get((int) InputEvent.EV_KEY);
        int numButtons = supportedKeys == null ? 0 : supportedKeys.size();
        System.out.println("Detected " + numButtons + " buttons.");
        this.device.addListener(this);
    }
    
    /**
     * @return the device
     */
    public EventDevice getDevice() {
        return device;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void event(InputEvent e) {
        boolean dispatchPosition = false;
        switch (e.type) {
            case EV_KEY:
                if (e.value == 1) {
                    pressed=true;
                    notifyMousePressed(getMouseButton(e));
                } else {
                    pressed=false;
                    notifyMouseReleased(getMouseButton(e));
                }
                break;
            case EV_REL:
//                System.out.println("Axis Parameter:"+device.getAxisParameters(e.code));
                if (e.code == InputEvent.REL_X) {
                    // X AXIS
                    actualMousePosition.x += e.value;
                    if(actualMousePosition.x>1920){
                        actualMousePosition.x=1920;
                    }else if(actualMousePosition.x<0){
                        actualMousePosition.x=0;
                    }
                    dispatchPosition = true;
                } else if (e.code == InputEvent.REL_Y) {
                    actualMousePosition.y += e.value;
                    if(actualMousePosition.y>1080){
                        actualMousePosition.y=1080;
                    }else if(actualMousePosition.y<0){
                        actualMousePosition.y=0;
                    }
                    dispatchPosition = true;
                } else if (e.code == InputEvent.REL_WHEEL) {
                    notifyMouseWheel(getDirection(e), Math.abs(e.value));
                }
                break;

        }
        if(dispatchPosition &&!pressed){
            notifyMousePosition(actualMousePosition);
        }else if(dispatchPosition && pressed){
            notifyMouseDragged(actualMousePosition);
        }

    }

    /**
     * @param Position
     */
    private synchronized void notifyMouseDragged(Point pos) {
        listeners.forEach((ml) -> {
            ml.mouseDragged(pos.x, pos.y);
        });
    }


    /**
     * @param actualMousePosition2
     */
    private synchronized void notifyMousePosition(Point pos) {
        listeners.forEach((ml) -> {
            ml.mouseMoved(pos.x, pos.y);
        });
    }


    /**
     * @param direction
     * @param abs
     */
    private synchronized void notifyMouseWheel(WheelDirection direction, int abs) {
        listeners.forEach((ml) -> {
            ml.mouseWheel(direction, abs,actualMousePosition.x,actualMousePosition.y);
        });
    }

    /**
     * @param e
     * @return
     */
    private WheelDirection getDirection(InputEvent e) {
        if (e.value > 0)
            return WheelDirection.UP;
        return WheelDirection.DOWN;
    }

    /**
     * @param mouseButton
     */
    private synchronized void notifyMouseReleased(MouseButton mouseButton) {
        listeners.forEach((ml) -> {
            ml.mouseReleased(mouseButton,actualMousePosition.x,actualMousePosition.y);
        });
    }

    /**
     * @param mouseButton
     */
    private synchronized void notifyMousePressed(MouseButton mouseButton) {
        listeners.forEach((ml) -> {
            try{
                ml.mousePressed(mouseButton,actualMousePosition.x,actualMousePosition.y);
            }catch(Exception ex){
                ;
            }
        });

    }

    /**
     * @param e
     * @return
     */
    private MouseButton getMouseButton(InputEvent e) {
        switch (e.code) {
            case InputEvent.BTN_LEFT:
                return MouseButton.LEFT;
            case InputEvent.BTN_RIGHT:
                return MouseButton.RIGHT;
            case InputEvent.BTN_MIDDLE:
                return MouseButton.MIDDLE;
            case InputEvent.BTN_SIDE:
                return MouseButton.LSIDE_BTN;
            case InputEvent.BTN_EXTRA:
                return MouseButton.RSIDE_BTN;
        }
        return null;
    }

    public synchronized void addMouseListener(IMouseListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeMouseListener(IMouseListener listener) {
        listeners.remove(listener);
    }
    
    
    public void waitHandler(){
        Thread t = new Thread(()->{while(true){try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}});
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
