/*
 * Created on Mar 13, 2017
 *
 * Copyright (c) Rheinmetall Italia S.p.A. All rights reserved.
 * Use is subject to license terms 
 */
package com.dgis.input.evdev.devices;

/**
 * //TODO First sentence till the "." is a brief description. 
 * //The rest of the phrase is the detailed description.
 *
 *
 * @author Andrea Picchiani - Rheinmetall Italia S.p.A.
 *
 */
public interface IMouseListener {
    public static enum MouseButton{
        LEFT,MIDDLE,RIGHT,LSIDE_BTN,RSIDE_BTN;
    }
    
    public static enum WheelDirection{
        UP,DOWN;
    }
    
}
