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


import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * {@link ImageUtil} is an helper class used to handle images
 *
 * @author Alessio Iannone - Rheinmetall Italia S.p.A.
 * Created on 25/mag/2013
 * @version $Id: ImageUtil.java 13157 2015-09-03 10:51:42Z sccp_ian $
 *
 */
public class ImageUtil {
    
    
    
    /**
     * Load the icon from the given uri
     * 
     * @param uri this could be a resource on the class path or a file path
     * @return the Icon (a new ImageIcon is created)
     */
    public static Icon loadIcon(String uri){
        File f = new File(uri);
        Icon icon = null;
        if(!Files.isReadable(f.toPath())){
            icon = new ImageIcon(ImageUtil.class.getResource(uri));
        }else{
            icon = new ImageIcon(uri);
        }
        return icon;
    }
    
    
    /**
     * Load a BufferedImage from the given uri
     * 
     * @param uri this could be a resource on the class path or a file path
     * @return the BufferedImage
     */
    public static BufferedImage loadBufferedImage(String uri){
        try{
            return convert((ImageIcon) loadIcon(uri));
        }catch(Exception ex){
            return null;
        }
    }
    /**
     * Resize the input BufferedImage to the specified width and height
     * 
     * @param img the BufferedImage to be resized
     * @param width the new width of the resized BufferedImage
     * @param height the new height of the resized BufferedImage
     * @return
     */
    public final static BufferedImage resizeImage(BufferedImage img, int width, int height) {
        if (img == null) {
            throw new IllegalArgumentException("Img parameter could not be null");
        }
        BufferedImage resized = new BufferedImage(width, height, img.getType());
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        g.drawImage(img, 0, 0, width, height, 0, 0, img.getWidth(), img.getHeight(), null);
        // g.drawImage(img, 0, 0, width, height, 0, 0, img.getWidth(), img.getHeight(), null);
        g.dispose();
        return resized;
    }
    /**
     * Convert the Input {@link ImageIcon} into a {@link BufferedImage} with type TYPE_INT_ARGB
     * 
     * @param icon the {@link ImageIcon} to be converted
     * @return the converted BufferedImaged
     */
    public final static BufferedImage convert(ImageIcon icon){
        if(icon==null){
            throw new IllegalArgumentException("ImageIcon is null");
        }
        BufferedImage buffered = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buffered.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        
        

        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0, 0);
        g.dispose();

        return buffered;
    }

    /**
     * Resize the input ImageIcon to a new size
     * 
     * @param icon the input ImageIcon
     * @param width the new width
     * @param height the new height
     * @return the resized ImageIcon
     */
    public final static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        BufferedImage buffered = convert(icon);
        BufferedImage resized = resizeImage(buffered, width, height);
        ImageIcon result = new ImageIcon(resized);
        return result;

    }
}
