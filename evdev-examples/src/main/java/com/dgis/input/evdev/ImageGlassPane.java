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


import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * 
 *  Create an {@link ImageGlassPane} - a {@link BlockableGlassPane} that can show an Image on it.
 *
 *        The background color is used as an overlay with the declared alpha value (0 totally transparent - 1 totally
 *        opaque).
 * 
 *        This class can be used to show for example a GameOver message over an MMI main frame blocking user input (or
 *        not, it depends on you) and eventually customize rendering providing a proper {@link CustomRender}
 *        implementation
 *
 *
 * @author Alessio Iannone - Rheinmetall Italia S.p.A.
 * Created on 26/mar/2015
 * @version $Id: ImageGlassPane.java 13196 2015-09-07 10:59:13Z sccp_ian $
 *
 */
public class ImageGlassPane extends BlockableGlassPane {

    private Icon icon;
    private BufferedImage bim;

    private int xPos;
    private int yPos;

    private int xPosBackground;
    private int yPosBackground;

    private int imgWidth;
    private int imgHeight;

    private int bgWidth;
    private int bgHeight;

    // Alpha value for the image (0 totally transparent - 1 totally opaque)
    private float imageAlphaValue = 0.8f;

    // Alpha for the background color (0 totally transparent - 1 totally opaque)
    private float alphaValue = 0.8f;
    private Shape imageShape;

    private boolean backgroundOnTopOfImage = false;

    /**
     * 
     *  Customize rendering on the {@link ImageGlassPane}
     *
     *        This interface give a way to take control on how to customize rendering over this {@link ImageGlassPane}
     *        by using IOC (Inversion of Control)
     *
     *
     * @author Alessio Iannone - Rheinmetall Italia S.p.A.
     * Created on 26 Mar 2015
     * @version $Id: ImageGlassPane.java 13196 2015-09-07 10:59:13Z sccp_ian $
     *
     */
    public static interface CustomRender {
        public void render(Graphics g, int width, int heigth);
    }

    private CustomRender customRender;
    private boolean widthSet = false;
    private boolean heightSet = false;
    private boolean bgWidthSet = false;
    private boolean bgHeightSet = false;

    /**
     * Create an {@link ImageGlassPane} acting on the specified container
     * @param container
     */
    public ImageGlassPane(Container container) {
        super(container);
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                if (!widthSet) {
                    imgWidth = ImageGlassPane.this.getWidth();
                }
                if (!bgWidthSet) {
                    bgWidth = ImageGlassPane.this.getWidth();
                }

                if (!heightSet) {
                    imgHeight = ImageGlassPane.this.getHeight();
                }
                if (!bgHeightSet) {
                    bgHeight = ImageGlassPane.this.getHeight();
                }
                repaint();
            }

        });
    }

    /**
     * Select if the background color has to be rendered on top of image or not
     * 
     * @param backgroundOnTopOfImage true to render the background color on top of image, false otherwise
     */
    public void setBackgroundOnTopOfImage(boolean backgroundOnTopOfImage) {
        this.backgroundOnTopOfImage = backgroundOnTopOfImage;
    }

    /**
     * Check if the background color has to be rendered on top of the image
     * 
     * @return true if the background has to be rendered on top of the image, false otherwise
     */
    public boolean isBackgroundOnTopOfImage() {
        return backgroundOnTopOfImage;
    }

    /**
     * Load the image icon from the file system or if not available search it on the classpath
     * 
     * @param uri a path on the file system or on the local classpath
     * @return
     */
    private static Icon loadIcon(String uri) {
        File f = new File(uri);
        Icon icon = null;
        if (!Files.isReadable(f.toPath())) {
            icon = new ImageIcon(ImageGlassPane.class.getResource(uri));
        } else {
            icon = new ImageIcon(uri);
        }
        return icon;
    }

    /**
     * Set the image url to be used as background image
     * @param img
     * @return
     */
    public ImageGlassPane setImage(String img) {
        icon = loadIcon(img);
        bim = ImageUtil.convert((ImageIcon) icon);
        return this;
    }

    /**
     * Set the alpha value to use for background and image
     * 
     * @param alphaValue (0 totally transparent - 1 totally opaque)
     * @return this instance
     */
    public ImageGlassPane setImageAlphaValue(float alphaValue) {
        this.imageAlphaValue = alphaValue;
        return this;
    }

    /**
     * Set the alpha value applied on the background color Default is 0.8f
     * 
     * @param alphaValue
     * @return this instance
     */
    public ImageGlassPane setAlphaValue(float alphaValue) {
        this.alphaValue = alphaValue;
        return this;
    }

    /**
     * Retrieve the alpha value of the background color
     * 
     * @return the alpha value of the background color
     */
    public float getAlphaValue() {
        return alphaValue;
    }

    /**
     * Retrieve the alpha value of the image
     * 
     * @return the alpha value of the image
     */
    public float getImageAlphaValue() {
        return imageAlphaValue;
    }

    /**
     * Set the Shape to use as clip for the graphics context prior to draw the image
     * 
     * @param imageShape the shape to be usede on top of the image
     * @return
     */
    public ImageGlassPane setImageShape(Shape imageShape) {
        this.imageShape = imageShape;
        return this;
    }

    /**
     * Set the height of the image that do you want to use to resize it
     * 
     * @param imgHeight
     * @return
     */
    public ImageGlassPane setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
        heightSet = true;
        return this;
    }

    /**
     * Set the height of the background that do you want to use to resize it
     * 
     * @param bgHeight
     * @return
     */
    public ImageGlassPane setBgHeight(int bgHeight) {
        this.bgHeight = bgHeight;
        bgHeightSet = true;
        return this;
    }

    /**
     * Set the width of the image that do you want to use to resize it
     * 
     * @param imgWidth
     * @return
     */
    public ImageGlassPane setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
        widthSet = true;
        return this;
    }

    /**
     * Set the width of the background that do you want to use to resize it
     * 
     * @param bgWidth
     * @return
     */
    public ImageGlassPane setBgWidth(int bgWidth) {
        this.bgWidth = bgWidth;
        bgWidthSet = true;
        return this;
    }

    /**
     * Get the width of the image
     * 
     * @return
     */
    public int getImgWidth() {
        if (imgWidth == 0) {
            imgWidth = getWidth() - (xPos * 2);
        }
        return imgWidth;
    }

    /**
     * Get the width of the Background
     * @return
     */
    public int getBgWidth() {
        if (bgWidth == 0) {
            bgWidth = getWidth() - (xPosBackground * 2);
        }
        return bgWidth;
    }

    /**
     * Get the height of the background
     * @return
     */
    public int getBgHeight() {
        if (bgHeight == 0) {
            bgHeight = getHeight() - (yPosBackground * 2);
        }
        return bgHeight;
    }

    /**
     * Get the height of the image
     * 
     * @return
     */
    public int getImgHeight() {
        if (imgHeight == 0) {
            imgHeight = getHeight() - (yPos * 2);
        }
        return imgHeight;
    }

    /**
     * Set the x position of the image
     * 
     * @param xPos - the position along the x axis.
     */
    public ImageGlassPane setxPos(int xPos) {
        this.xPos = xPos;
        return this;
    }

    /**
     * Set the x position of the background (default 0)
     * 
     * @param xPos - the position along the x axis
     */
    public ImageGlassPane setXBgPos(int xPos) {
        this.xPosBackground = xPos;
        return this;
    }

    /**
     * Set the y position of the background (default 0)
     * 
     * @param xPos
     */
    public ImageGlassPane setYBgPos(int yPos) {
        this.yPosBackground = yPos;
        return this;
    }

    /**
     * Set the y position of the image
     * 
     * @param yPos
     */
    public ImageGlassPane setyPos(int yPos) {
        this.yPos = yPos;
        return this;
    }

    /**
     * Specify any other drawing action that shall occur after rendering the image on the glass pane
     * 
     * @param customRender - the {@link CustomRender} implementation to be used
     */
    public ImageGlassPane setCustomRender(CustomRender customRender) {
        this.customRender = customRender;
        return this;
    }

    /**
     * Close any open dialog (JDialog or dialog) that is visible on the running software
     * 
     * @return
     */
    public ImageGlassPane closeOpenDialogs() {
        closeAnyOpenDialogs();
        return this;
    }

    /**
    *Close any open dialogs that is visible on the running software
    */
    public static void closeAnyOpenDialogs() {
        for (Window window : Window.getWindows()) {
            if (window instanceof JDialog || window instanceof Dialog) {
                try {
                    window.dispose();
                } catch (Exception ex) {
                    ;
                }
            }
        }
        try {
            JOptionPane.getRootFrame().dispose();
        } catch (Exception ex) {
            ;
        }
    }

    /**
     * Render the background color
     * @param g2
     */
    private void renderBackgroundColor(Graphics2D g2) {
        AlphaComposite alphaBackground = AlphaComposite.SrcOver.derive(alphaValue);
        g2.setComposite(alphaBackground);
        g2.setColor(getBackground());
        g2.fillRect(xPosBackground, yPosBackground, getBgWidth(), getBgHeight());
    }

    /**
     * 
     * @param g2
     */
    private void renderImage(Graphics2D g2) {
        AlphaComposite alphaImage = AlphaComposite.SrcOver.derive(imageAlphaValue);
        if (bim != null) {
            if (imageShape != null) {
                g2.setClip(imageShape);
            }
            g2.setComposite(alphaImage);
            g2.drawImage(bim, xPos, yPos, getImgWidth(), getImgHeight(), this);
        }
    }

    /**
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        repaintContainer(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // gets the current clipping area
        Rectangle originalClip = g.getClipBounds();

        // Remember original composite
        Composite composite = g2.getComposite();

        if (backgroundOnTopOfImage) {
            // Draw the image
            renderImage(g2);

            // Draw the background
            renderBackgroundColor(g2);
        } else {
            renderBackgroundColor(g2);
            // Draw the image
            renderImage(g2);

        }
        g2.setComposite(composite);
        if (customRender == null) {
            return;
        }
        g.setClip(originalClip);
        customRender.render(g, getWidth(), getHeight());

    }
}
