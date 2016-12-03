/*
 * Parameters.java
 *
 * 2013
 *
 * Created by Marc-Alexandre Blanchard - all right reserved ©
 *
 */
package com.MultiNote;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author Crée par Marc-Alexandre Blanchard
 */
final class Parameters
{

    /**
     * Author
     */
    final static String author = "Marc-Alexandre Blanchard";
    /**
     * Name of the app
     */
    final static String APPNAME = "MultiNote";
    /**
     * Filename of the file setting
     */
    final static String FILENAME = "MultiNote.xml";
    /**
     * Regex to check phone number when parsing text
     */
    final static String PHONE_NUMBER_REGEX = "([0-9]{10})|[0-9][0-9](/[0-9][0-9]){4}|[0-9][0-9](-[0-9][0-9]){4}|[0-9][0-9](\\.[0-9][0-9]){4}|[0-9][0-9](_[0-9][0-9]){4}|[0-9][0-9](,[0-9][0-9]){4}|[0-9][0-9]('[0-9][0-9]){4}|[0-9][0-9](\\ [0-9][0-9]){4}";
    /**
     * Regex to check phone number when parsing text
     */
    final static String PHONE_NUMBER_LINE_REGEX = "(.*([^0-9]))?" + PHONE_NUMBER_REGEX + "(([^0-9]).*)?";
    /**
     * Regex to check date when parsing text
     */
    final static String DATE_REGEX = "(3[0-1]|[1-2][0-9]|0?[1-9])/(1[0-2]|0?[1-9])";
    /**
     * Regex to check date when parsing text
     */
    final static String DATE_LINE_REGEX = "(.*([^0-9]))?" + DATE_REGEX + "(([^0-9]).*)?";
    /**
     * Sample text for the first start
     */
    final static String SAMPLE_TXT = "Welcome to " + APPNAME + "\n\nA simple software to take notes, fully personalizable.\n\nYour notes and your preferences are automatically saved.\n\n01/01 Enjoy !\n";
    /**
     * Version number of the App
     */
    final static float VERSION = 7.00f;
    /**
     * default background color
     */
    final static Color DEFAULT_BG_COLOR = Color.WHITE;
    /**
     * default foreground color
     */
    final static Color DEFAULT_FG_COLOR = Color.BLACK;
    /**
     * default opacity level
     */
    final static int DEFAULT_OPACITY_LVL = 70;
    /**
     * default font size
     */
    final static int DEFAULT_FONT_SIZE = 16;
    /**
     * default height when windows is minimized
     */
    final static int DEFAULT_MINIMIZED_WIDTH = 28;
    /**
     * default dimension of the window
     */
    final static Dimension DEFAULT_WINDOW_SIZE = new Dimension(500, 500);
    /**
     * default dimension of the window
     */
    final static Dimension MINIMAL_WINDOW_SIZE = new Dimension(350, 240);
    /**
     * default notification state
     */
    final static int DEFAULT_NOTIFICATION_STATE = 1;
    /**
     * Default location of the window
     */
    final static Point DEFAULT_LOCATION = new Point((int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4), (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4));

    /**
     * Private constructor to disallow instanciation
     */
    private Parameters()
    {
    }
}
