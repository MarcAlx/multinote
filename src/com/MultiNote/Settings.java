/*
 * Settings.java
 *
 * 2013
 *
 * Created by Marc-Alexandre Blanchard - all right reserved ©
 *
 */
package com.MultiNote;

import java.awt.Color;

/**
 *
 * @author Crée par Marc-Alexandre Blanchard
 */
final class Settings
{

    private int id;
    private int backgroundRed;
    private int backgroundGreen;
    private int backgroundBlue;
    private int foregroundRed;
    private int foregroundGreen;
    private int foregroundBlue;
    private int locationX;
    private int locationY;
    private int width;
    private int height;
    private int opacityLVL;
    private int fontSize;
    private int notificationState;
    private String contentTXT;

    /**
     * default constructor using default parameters
     */
    public Settings()
    {
        this(Parameters.DEFAULT_BG_COLOR.getRed(), Parameters.DEFAULT_BG_COLOR.getGreen(), Parameters.DEFAULT_BG_COLOR.getBlue(), Parameters.DEFAULT_FG_COLOR.getRed(), Parameters.DEFAULT_FG_COLOR.getGreen(), Parameters.DEFAULT_FG_COLOR.getBlue(), Parameters.DEFAULT_LOCATION.x, Parameters.DEFAULT_LOCATION.y, Parameters.DEFAULT_WINDOW_SIZE.width, Parameters.DEFAULT_WINDOW_SIZE.height, Parameters.DEFAULT_FONT_SIZE, Parameters.DEFAULT_NOTIFICATION_STATE, Parameters.DEFAULT_OPACITY_LVL, Parameters.SAMPLE_TXT);
    }

    /**
     *
     * @param bgR foreground red
     * @param bgG foreground green
     * @param bgB foreground blue
     * @param fgR foreground red
     * @param fgG foreground green
     * @param fgB foreground blue
     * @param lX location x
     * @param lY location y
     * @param w width of the window
     * @param h heigth of the window
     * @param fs font size
     * @param ns notification state
     * @param opl opacity level
     * @param ws window size
     * @param ct content text
     */
    public Settings(int bgR, int bgG, int bgB, int fgR, int fgG, int fgB, int lX, int lY, int w, int h, int fs, int ns, int opl, String ct)
    {
        this.backgroundRed = bgR;
        this.backgroundGreen = bgG;
        this.backgroundBlue = bgB;
        this.foregroundRed = fgR;
        this.foregroundGreen = fgG;
        this.foregroundBlue = fgB;
        this.locationX = lX;
        this.locationY = lY;
        this.width = w;
        this.height = h;
        this.fontSize = fs;
        this.notificationState = ns;
        this.opacityLVL = opl;
        this.contentTXT = ct;
    }

    /**
     * @return the backgroundRed
     */
    public int getBackgroundRed()
    {
        return backgroundRed;
    }

    /**
     * @param backgroundRed the backgroundRed to set
     */
    public void setBackgroundRed(int backgroundRed)
    {
        this.backgroundRed = backgroundRed;
    }

    /**
     * @return the backgroundGreen
     */
    public int getBackgroundGreen()
    {
        return backgroundGreen;
    }

    /**
     * @param backgroundGreen the backgroundGreen to set
     */
    public void setBackgroundGreen(int backgroundGreen)
    {
        this.backgroundGreen = backgroundGreen;
    }

    /**
     * @return the backgroundBlue
     */
    public int getBackgroundBlue()
    {
        return backgroundBlue;
    }

    /**
     * @param backgroundBlue the backgroundBlue to set
     */
    public void setBackgroundBlue(int backgroundBlue)
    {
        this.backgroundBlue = backgroundBlue;
    }

    /**
     * @return the foregroundRed
     */
    public int getForegroundRed()
    {
        return foregroundRed;
    }

    /**
     * @param foregroundRed the foregroundRed to set
     */
    public void setForegroundRed(int foregroundRed)
    {
        this.foregroundRed = foregroundRed;
    }

    /**
     * @return the foregroundGreen
     */
    public int getForegroundGreen()
    {
        return foregroundGreen;
    }

    /**
     * @param foregroundGreen the foregroundGreen to set
     */
    public void setForegroundGreen(int foregroundGreen)
    {
        this.foregroundGreen = foregroundGreen;
    }

    /**
     * @return the foregroundBlue
     */
    public int getForegroundBlue()
    {
        return foregroundBlue;
    }

    /**
     * @param foregroundBlue the foregroundBlue to set
     */
    public void setForegroundBlue(int foregroundBlue)
    {
        this.foregroundBlue = foregroundBlue;
    }

    /**
     * @return the locationX
     */
    public int getLocationX()
    {
        return locationX;
    }

    /**
     * @param locationX the locationX to set
     */
    public void setLocationX(int locationX)
    {
        this.locationX = locationX;
    }

    /**
     * @return the locationY
     */
    public int getLocationY()
    {
        return locationY;
    }

    /**
     * @param locationY the locationY to set
     */
    public void setLocationY(int locationY)
    {
        this.locationY = locationY;
    }

    /**
     * @return the opacityLVL
     */
    public int getOpacityLVL()
    {
        return opacityLVL;
    }

    /**
     * @param opacityLVL the opacityLVL to set
     */
    public void setOpacityLVL(int opacityLVL)
    {
        this.opacityLVL = opacityLVL;
    }

    /**
     * @return the fontSize
     */
    public int getFontSize()
    {
        return fontSize;
    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
    }

    /**
     * @return the notificationState
     */
    public int getNotificationState()
    {
        return notificationState;
    }

    /**
     * @param notificationState the notificationState to set
     */
    public void setNotificationState(int notificationState)
    {
        this.notificationState = notificationState;
    }

    /**
     * @return the contentTXT
     */
    public String getContentTXT()
    {
        return contentTXT;
    }

    /**
     * @param contentTXT the contentTXT to set
     */
    public void setContentTXT(String contentTXT)
    {
        this.contentTXT = contentTXT;
    }

    /**
     *
     * @param c the color to set
     */
    public void setBGColor(Color c)
    {
        this.backgroundRed = c.getRed();
        this.backgroundGreen = c.getGreen();
        this.backgroundBlue = c.getBlue();
    }

    /**
     *
     * @param c the color to set
     */
    public void setFGColor(Color c)
    {
        this.foregroundRed = c.getRed();
        this.foregroundGreen = c.getGreen();
        this.foregroundBlue = c.getBlue();
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
}
