/*
 * Note.java
 *
 * 2013
 *
 * Created by Marc-Alexandre Blanchard - all right reserved ©
 *
 */
package com.MultiNote;

import com.toaster.engine.Toaster;
import com.toaster.exceptions.TooManyToastException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Crée par Marc-Alexandre Blanchard
 */
final class Note extends JFrame implements ActionListener, ChangeListener, KeyListener, MouseInputListener, WindowListener
{
    //Components
    private JButton buttonRemove;
    private JButton buttonAdd;
    private JButton buttonPin;
    private JButton buttonNotifications;
    private JMenuBar menuBar;
    private JMenu menuColorNote;
    private JMenuItem menuCreator;
    private JMenu menuFontSize;
    private JMenu menuOpacity;
    private JSlider fontSizeSlider;
    private JSlider opacitySlider;
    private JMenu menuAbout;
    private JMenuItem menuQuit;
    private JRadioButtonMenuItem menuBGBlack;
    private JRadioButtonMenuItem menuBGBlue;
    private JRadioButtonMenuItem menuBGGreen;
    private JRadioButtonMenuItem menuBGMagenta;
    private JRadioButtonMenuItem menuBGOrange;
    private JRadioButtonMenuItem menuBGPink;
    private JRadioButtonMenuItem menuBGRed;
    private JRadioButtonMenuItem menuBGYellow;
    private JRadioButtonMenuItem menuBGWhite;
    private JRadioButtonMenuItem menuFGBlack;
    private JRadioButtonMenuItem menuFGBlue;
    private JRadioButtonMenuItem menuFGGreen;
    private JRadioButtonMenuItem menuFGMagenta;
    private JRadioButtonMenuItem menuFGOrange;
    private JRadioButtonMenuItem menuFGPink;
    private JRadioButtonMenuItem menuFGRed;
    private JRadioButtonMenuItem menuFGYellow;
    private JRadioButtonMenuItem menuFGWhite;
    private JCheckBoxMenuItem notificationCheckBox;
    private JPanel panel;
    private JTextArea content;
    private JScrollPane scroll;

    //Environnement
    private GraphicsEnvironment ge;
    private GraphicsDevice gd;

    //Settings (Temp and persistent) and toaster
    private boolean panelVisibility = true;
    private final Toaster T;
    private final Settings settings;

    //Manager
    private final NoteManager NM;

    //Used for moving
    private Point lastClickInside;
    //Used for resizing
    private boolean isLeftBorder;
    private boolean isRightBorder;
    private boolean isBottomBorder;
    private boolean onResize;
    private Point lastClickOnScreen;

    /**
     * Default constructor
     */
    public Note()
    {
        T = Toaster.getInstance();
        NM = NoteManager.getInstance();
        this.settings = new Settings();
    }

    public Note(Settings s)
    {
        T = Toaster.getInstance();
        NM = NoteManager.getInstance();
        this.settings = s;
    }

    /**
     * Check the text of the note in order to show notifications
     */
    private void createNotificationFromText()
    {
        System.setProperty("line.separator", "\n");
        String[] lines = this.content.getText().split(System.getProperty("line.separator"));
        String message;
        for (String item : lines)
        {
            Pattern pattern = Pattern.compile(Parameters.PHONE_NUMBER_LINE_REGEX);
            Matcher matcher = pattern.matcher(item);
            if (matcher.find())
            {
                pattern = Pattern.compile(Parameters.PHONE_NUMBER_REGEX);
                matcher = pattern.matcher(item);
                if (matcher.find())
                {
                    message = "To call !\n" + item.substring(matcher.start(), item.length());
                    try
                    {
                        T.Toast(message, this.content.getBackground(), this.content.getForeground(), 5000);
                    }
                    catch (TooManyToastException ex)
                    {
                        JOptionPane.showMessageDialog(this, message, "Notification", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }

            pattern = Pattern.compile(Parameters.DATE_LINE_REGEX);
            matcher = pattern.matcher(item);
            if (matcher.find())
            {
                message = "";
                pattern = Pattern.compile(Parameters.DATE_REGEX);
                matcher = pattern.matcher(item);
                if (matcher.find())
                {
                    try
                    {
                        Calendar C = Calendar.getInstance();
                        int dayNB = Integer.parseInt(matcher.group(1));
                        int monthNB = Integer.parseInt(matcher.group(2));
                        if (dayNB == C.get(Calendar.DAY_OF_MONTH) && monthNB == (C.get(Calendar.MONTH) + 1))
                        {
                            message += "/!\\ Today - ";
                        }
                        message += dayNB + "/" + monthNB + "\n" + item.substring(matcher.end(), item.length());

                        T.Toast(message, this.content.getBackground(), this.content.getForeground(), 5000);
                        this.requestFocus();
                    }
                    catch (TooManyToastException ex)
                    {
                        JOptionPane.showMessageDialog(this, message, "Notification", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        }
    }

    /**
     * Draw the GUI of the note
     */
    public void drawNote()
    {
        this.setTitle(Parameters.APPNAME);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        this.panel.setBorder(new LineBorder(Color.white, 2));
        menuBar = new JMenuBar();

        menuColorNote = new JMenu(Parameters.APPNAME);

        ButtonGroup group22 = new ButtonGroup();
        JMenu menu22 = new JMenu("Background");
        menuBGBlack = new JRadioButtonMenuItem("Black");
        group22.add(menuBGBlack);
        menu22.add(menuBGBlack);
        menuBGBlue = new JRadioButtonMenuItem("Blue");
        group22.add(menuBGBlue);
        menu22.add(menuBGBlue);
        menuBGGreen = new JRadioButtonMenuItem("Green");
        group22.add(menuBGGreen);
        menu22.add(menuBGGreen);
        menuBGMagenta = new JRadioButtonMenuItem("Magenta");
        group22.add(menuBGMagenta);
        menu22.add(menuBGMagenta);
        menuBGOrange = new JRadioButtonMenuItem("Orange");
        group22.add(menuBGOrange);
        menu22.add(menuBGOrange);
        menuBGPink = new JRadioButtonMenuItem("Pink");
        group22.add(menuBGPink);
        menu22.add(menuBGPink);
        menuBGRed = new JRadioButtonMenuItem("Red");
        group22.add(menuBGRed);
        menu22.add(menuBGRed);
        menuBGYellow = new JRadioButtonMenuItem("Yellow");
        group22.add(menuBGYellow);
        menu22.add(menuBGYellow);
        menuBGWhite = new JRadioButtonMenuItem("White");
        group22.add(menuBGWhite);
        menu22.add(menuBGWhite);
        menuColorNote.add(menu22);

        ButtonGroup group23 = new ButtonGroup();
        JMenu menu23 = new JMenu("Font color");
        menuFGBlack = new JRadioButtonMenuItem("Black");
        group23.add(menuFGBlack);
        menu23.add(menuFGBlack);
        menuFGBlue = new JRadioButtonMenuItem("Blue");
        group23.add(menuFGBlue);
        menu23.add(menuFGBlue);
        menuFGGreen = new JRadioButtonMenuItem("Green");
        group23.add(menuFGGreen);
        menu23.add(menuFGGreen);
        menuFGMagenta = new JRadioButtonMenuItem("Magenta");
        group23.add(menuFGMagenta);
        menu23.add(menuFGMagenta);
        menuFGOrange = new JRadioButtonMenuItem("Orange");
        group23.add(menuFGOrange);
        menu23.add(menuFGOrange);
        menuFGPink = new JRadioButtonMenuItem("Pink");
        group23.add(menuFGPink);
        menu23.add(menuFGPink);
        menuFGRed = new JRadioButtonMenuItem("Red");
        group23.add(menuFGRed);
        menu23.add(menuFGRed);
        menuFGYellow = new JRadioButtonMenuItem("Yellow");
        group23.add(menuFGYellow);
        menu23.add(menuFGYellow);
        menuFGWhite = new JRadioButtonMenuItem("White");
        group23.add(menuFGWhite);
        menu23.add(menuFGWhite);
        menuColorNote.add(menu23);

        menuFontSize = new JMenu("Font size");
        fontSizeSlider = new JSlider(10, 30, 16);
        fontSizeSlider.setMajorTickSpacing(2);
        fontSizeSlider.setMinorTickSpacing(2);
        fontSizeSlider.setPaintLabels(true);
        fontSizeSlider.setPaintTicks(true);
        menuFontSize.add(fontSizeSlider);
        menuColorNote.add(menuFontSize);

        if (gd.isWindowTranslucencySupported(TRANSLUCENT))
        {
            menuOpacity = new JMenu("Opacity");
            opacitySlider = new JSlider(50, 100, 70);
            opacitySlider.setMajorTickSpacing(10);
            opacitySlider.setMinorTickSpacing(10);
            opacitySlider.setPaintLabels(true);
            opacitySlider.setPaintTicks(true);
            menuOpacity.add(opacitySlider);
            menuColorNote.add(menuOpacity);
        }

        notificationCheckBox = new JCheckBoxMenuItem("Notifications");
        menuColorNote.add(notificationCheckBox);
        menuBar.add(menuColorNote);
        
        menuQuit = new JMenuItem("Quit");
        menuColorNote.add(menuQuit);

        buttonAdd = new JButton("+");
        buttonAdd.setToolTipText("Add a new note");
        menuBar.add(buttonAdd);

        buttonNotifications = new JButton("!");
        buttonNotifications.setToolTipText("Show all notifications");
        menuBar.add(buttonNotifications);

        menuAbout = new JMenu("?");
        menuAbout.setToolTipText("About");
        menuCreator = new JMenuItem("Created & designed by " + Parameters.author);
        menuAbout.add(menuCreator);
        JMenuItem menuVersion = new JMenuItem("Version " + Parameters.VERSION);
        menuAbout.add(menuVersion);
        menuBar.add(menuAbout);

        menuBar.add(Box.createGlue());

        buttonPin = new JButton("☀");
        buttonPin.setToolTipText("Keep Note on top");
        menuBar.add(buttonPin);

        buttonRemove = new JButton("-");
        buttonRemove.setToolTipText("Remove this note");
        menuBar.add(buttonRemove);

        if (!System.getProperty("os.name").startsWith("Windows"))
        {
            buttonAdd.setPreferredSize(new Dimension(40, 30));
            buttonNotifications.setPreferredSize(new Dimension(40, 30));
            menuAbout.setPreferredSize(new Dimension(40, 30));
            buttonPin.setPreferredSize(new Dimension(50, 30));
            buttonRemove.setPreferredSize(new Dimension(40, 30));
        }

        setJMenuBar(menuBar);

        this.content = new JTextArea();
        this.content.setEditable(true);
        this.content.setLineWrap(true);

        this.scroll = new JScrollPane(this.content);
        this.panel.add(this.scroll, BorderLayout.CENTER);

        this.getContentPane().add(panel);

        this.setAlwaysOnTop(false);

        menuQuit.addActionListener(this);
        menuCreator.addActionListener(this);
        fontSizeSlider.addChangeListener(this);
        opacitySlider.addChangeListener(this);
        notificationCheckBox.addActionListener(this);
        buttonNotifications.addActionListener(this);
        menuBGBlack.addActionListener(this);
        menuBGBlue.addActionListener(this);
        menuBGGreen.addActionListener(this);
        menuBGMagenta.addActionListener(this);
        menuBGOrange.addActionListener(this);
        menuBGPink.addActionListener(this);
        menuBGRed.addActionListener(this);
        menuBGYellow.addActionListener(this);
        menuBGWhite.addActionListener(this);
        menuFGBlack.addActionListener(this);
        menuFGBlue.addActionListener(this);
        menuFGGreen.addActionListener(this);
        menuFGMagenta.addActionListener(this);
        menuFGOrange.addActionListener(this);
        menuFGPink.addActionListener(this);
        menuFGRed.addActionListener(this);
        menuFGYellow.addActionListener(this);
        menuFGWhite.addActionListener(this);
        buttonAdd.addActionListener(this);
        buttonRemove.addActionListener(this);
        buttonPin.addActionListener(this);
        this.content.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addWindowListener(this);

        KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
    }

    /**
     * @return the settings
     */
    public Settings getSettings()
    {
        return settings;
    }

    /**
     * Initialise the note
     */
    public void init()
    {
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gd = ge.getDefaultScreenDevice();
        this.drawNote();

        this.setupNote();

        this.setVisible(true);
        if (this.notificationCheckBox.isSelected())
        {
            this.createNotificationFromText();
        }
    }

    /**
     * Keep/Release the window from front
     */
    private void pinIt()
    {
        if (this.isAlwaysOnTop())
        {
            this.setAlwaysOnTop(false);
            this.buttonPin.setText("☀");
            buttonPin.setToolTipText("Keep Note on top");
        }
        else
        {
            this.setAlwaysOnTop(true);
            this.buttonPin.setText("☼");
            buttonPin.setToolTipText("Release Note from top");
        }
    }

    /**
     * Quit the app
     */
    private void quit()
    {
        this.dispose();
        NM.quit();
    }

    /**
     * Save parameters to XML File
     */
    public void save()
    {
        NM.Save();
    }

    /**
     * Set the global background color of the Note
     *
     * @param bgColor bg color
     */
    public void setBGColor(Color bgColor)
    {
        //fg color if new bgcolor is the same as current fg
        Color fgColor;
        if (bgColor.equals(Color.black))
        {
            fgColor = Color.white;
        }
        else
        {
            fgColor = Color.black;
        }

        //Actualisation du bg
        if (!bgColor.equals(Color.black))
        {
            this.menuAbout.setBackground(bgColor);
            this.menuBar.setBackground(bgColor);
            this.menuColorNote.setBackground(bgColor);
        }
        else
        {
            this.menuAbout.setBackground(Color.white);
            this.menuBar.setBackground(Color.white);
            this.menuColorNote.setBackground(Color.white);
        }
        this.content.setBackground(bgColor);

        //Acutalisation du fg
        if (this.content.getForeground().equals(bgColor))
        {
            this.setFGColor(fgColor);
        }

        //Actualisation du menu
        bgColor = this.content.getBackground();
        if (bgColor.equals(Color.black))
        {
            this.menuBGBlack.setSelected(true);
        }
        else if (bgColor.equals(Color.blue))
        {
            this.menuBGBlue.setSelected(true);
        }
        else if (bgColor.equals(Color.green))
        {
            this.menuBGGreen.setSelected(true);
        }
        else if (bgColor.equals(Color.magenta))
        {
            this.menuBGMagenta.setSelected(true);
        }
        else if (bgColor.equals(Color.orange))
        {
            this.menuBGOrange.setSelected(true);
        }
        else if (bgColor.equals(Color.pink))
        {
            this.menuBGPink.setSelected(true);
        }
        else if (bgColor.equals(Color.red))
        {
            this.menuBGRed.setSelected(true);
        }
        else if (bgColor.equals(Color.yellow))
        {
            this.menuBGYellow.setSelected(true);
        }
        else if (bgColor.equals(Color.white))
        {
            this.menuBGWhite.setSelected(true);
        }
        this.getSettings().setBGColor(this.content.getBackground());
    }

    /**
     * Set the global background color of the Note
     *
     * @param BGR Background color Red
     * @param BGG Background color Green
     * @param BGB Background color Blue
     */
    private void setBGColor(int BGR, int BGG, int BGB)
    {
        this.setBGColor(new Color(BGR, BGG, BGB));
    }

    /**
     * Set the global foreground color of the Note
     *
     * @param fgColor fg color
     */
    public void setFGColor(Color fgColor)
    {
        //altfgcolor alternative fgcolor if bgcolor == fgcolor
        Color altfgcolor;
        if (fgColor.equals(Color.black))
        {
            altfgcolor = Color.white;
        }
        else
        {
            altfgcolor = Color.black;
        }

        //Actualisation du fg
        if (!this.content.getBackground().equals(fgColor))
        {
            this.content.setForeground(fgColor);
        }
        else
        {
            this.content.setForeground(altfgcolor);
        }

        //Menu update
        fgColor = this.content.getForeground();
        if (!fgColor.equals(Color.white) && !fgColor.equals(Color.yellow))
        {
            this.buttonAdd.setForeground(fgColor);
            this.buttonPin.setForeground(fgColor);
            this.buttonRemove.setForeground(fgColor);
            this.buttonNotifications.setForeground(fgColor);
        }
        else
        {
            this.buttonAdd.setForeground(Color.black);
            this.buttonPin.setForeground(Color.black);
            this.buttonRemove.setForeground(Color.black);
            this.buttonNotifications.setForeground(Color.black);
        }
        if (fgColor.equals(Color.black))
        {
            this.menuFGBlack.setSelected(true);
        }
        else if (fgColor.equals(Color.blue))
        {
            this.menuFGBlue.setSelected(true);
        }
        else if (fgColor.equals(Color.green))
        {
            this.menuFGGreen.setSelected(true);
        }
        else if (fgColor.equals(Color.magenta))
        {
            this.menuFGMagenta.setSelected(true);
        }
        else if (fgColor.equals(Color.orange))
        {
            this.menuFGOrange.setSelected(true);
        }
        else if (fgColor.equals(Color.pink))
        {
            this.menuFGPink.setSelected(true);
        }
        else if (fgColor.equals(Color.red))
        {
            this.menuFGRed.setSelected(true);
        }
        else if (fgColor.equals(Color.yellow))
        {
            this.menuFGYellow.setSelected(true);
        }
        else if (fgColor.equals(Color.white))
        {
            this.menuFGWhite.setSelected(true);
        }
        this.getSettings().setFGColor(this.content.getForeground());
    }

    /**
     * Set the global foreground color of the Note
     *
     * @param FRR Foreground color Red
     * @param FRG Foreground color Green
     * @param FRB Foreground color Blue
     */
    private void setFGColor(int FRR, int FRG, int FRB)
    {
        this.setFGColor(new Color(FRR, FRG, FRB));
    }

    /**
     * Set the font size of the note
     *
     * @param size The font size to set to the note text
     */
    private void setFontSize(int size)
    {
        this.getSettings().setFontSize(size);
        Font f = new Font("Lucida", Font.PLAIN, this.getSettings().getFontSize());
        content.setFont(f);
    }

    /**
     * Set opacity of the app
     *
     * @param LVL the opacity level to set to the note
     */
    private void setOpacityLVL(int LVL)
    {
        this.getSettings().setOpacityLVL(LVL);
        this.setOpacity(this.getSettings().getOpacityLVL() * 0.01f);
    }

    /**
     * set up the note from the settings
     */
    private void setupNote()
    {
        this.setBGColor(getSettings().getBackgroundRed(), getSettings().getBackgroundGreen(), getSettings().getBackgroundBlue());
        this.setFGColor(getSettings().getForegroundRed(), getSettings().getForegroundGreen(), getSettings().getForegroundBlue());
        Dimension resolution = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        if (getSettings().getLocationX() > 0 && getSettings().getLocationX() < resolution.getWidth()
                && getSettings().getLocationY() > 0 && getSettings().getLocationY() < resolution.getHeight())
        {
            this.setLocation(getSettings().getLocationX(), getSettings().getLocationY());
        }
        if (gd.isWindowTranslucencySupported(TRANSLUCENT))
        {
            this.setUndecorated(true);
            this.setOpacityLVL(getSettings().getOpacityLVL());
            this.opacitySlider.setValue(getSettings().getOpacityLVL());
        }
        this.setFontSize(getSettings().getFontSize());
        this.fontSizeSlider.setValue(getSettings().getFontSize());

        boolean notificationState = true;
        if (getSettings().getNotificationState() == 0)
        {
            notificationState = false;
        }
        this.notificationCheckBox.setSelected(notificationState);
        this.setSize(this.getSettings().getWidth(), this.getSettings().getHeight());
        this.content.setText(getSettings().getContentTXT());
    }

    /**
     * Toggle the visibility of the note panel
     */
    private void toggleNotePanelVisibility()
    {
        if (this.panelVisibility)
        {
            this.setSize(this.getSettings().getWidth(), Parameters.DEFAULT_MINIMIZED_WIDTH);
            this.panelVisibility = false;
            this.panel.setVisible(this.panelVisibility);
            this.setResizable(false);
        }
        else
        {
            this.setSize(this.getSettings().getWidth(), this.getSettings().getHeight());
            this.panelVisibility = true;
            this.panel.setVisible(this.panelVisibility);
            this.setResizable(true);
        }
    }

    //Overrides
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        Object source = ae.getSource();
        if (source == menuQuit)
        {
            this.quit();
        }
        else if (source == buttonPin)
        {
            this.pinIt();
        }
        else if (source == menuBGBlack)
        {
            this.setBGColor(Color.black);
        }
        else if (source == menuBGGreen)
        {
            this.setBGColor(Color.green);
        }
        else if (source == menuBGBlue)
        {
            this.setBGColor(Color.blue);
        }
        else if (source == menuBGMagenta)
        {
            this.setBGColor(Color.magenta);
        }
        else if (source == menuBGOrange)
        {
            this.setBGColor(Color.orange);
        }
        else if (source == menuBGPink)
        {
            this.setBGColor(Color.pink);
        }
        else if (source == menuBGRed)
        {
            this.setBGColor(Color.red);
        }
        else if (source == menuBGYellow)
        {
            this.setBGColor(Color.yellow);
        }
        else if (source == menuBGWhite)
        {
            this.setBGColor(Color.white);
        }
        else if (source == menuFGBlack)
        {
            this.setFGColor(Color.black);
        }
        else if (source == menuFGGreen)
        {
            this.setFGColor(Color.green);
        }
        else if (source == menuFGBlue)
        {
            this.setFGColor(Color.blue);
        }
        else if (source == menuFGMagenta)
        {
            this.setFGColor(Color.magenta);
        }
        else if (source == menuFGOrange)
        {
            this.setFGColor(Color.orange);
        }
        else if (source == menuFGPink)
        {
            this.setFGColor(Color.pink);
        }
        else if (source == menuFGRed)
        {
            this.setFGColor(Color.red);
        }
        else if (source == menuFGYellow)
        {
            this.setFGColor(Color.yellow);
        }
        else if (source == menuFGWhite)
        {
            this.setFGColor(Color.white);
        }
        else if (source == notificationCheckBox)
        {
            if (notificationCheckBox.isSelected())
            {
                this.getSettings().setNotificationState(1);
                this.createNotificationFromText();
            }
            else
            {
                this.getSettings().setNotificationState(0);
            }
        }
        else if (source == buttonNotifications)
        {
            if (T.getNbToastOnScreen() == 0)
            {
                this.createNotificationFromText();
            }
        }
        else if (source == buttonAdd)
        {
            NM.addNote();
        }
        else if (source == buttonRemove)
        {
            int res = JOptionPane.showConfirmDialog(this, "Remove this note ?", "/!\\", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION)
            {
                NM.removeNote(this.settings.getId());
                this.setVisible(false);
            }
        }
        else if (source == menuCreator)
        {
            String message = "Feedback / Contact ?\nmarc-alx@outlook.com";
            try
            {
                T.Toast(message, this.content.getBackground(), this.content.getForeground(), 5000);
            }
            catch (TooManyToastException ex)
            {
                JOptionPane.showMessageDialog(this, message, "Notification", JOptionPane.PLAIN_MESSAGE);
            }
        }
        this.save();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        this.getSettings().setContentTXT(this.content.getText());
        this.save();
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (isBottomBorder || isLeftBorder || isRightBorder)
        {
            this.onResize = true;
            if (isLeftBorder)
            {
                if ((this.getSettings().getWidth() + (lastClickOnScreen.x - e.getXOnScreen()) >= Parameters.MINIMAL_WINDOW_SIZE.width))
                {
                    this.getSettings().setLocationX(this.getSettings().getLocationX() - (lastClickOnScreen.x - e.getXOnScreen()));
                    this.setLocation(this.getSettings().getLocationX(), this.getSettings().getLocationY());
                    this.getSettings().setWidth(this.getSettings().getWidth() + (lastClickOnScreen.x - e.getXOnScreen()));
                }
                else
                {
                    isBottomBorder = false;
                    isLeftBorder = false;
                    isRightBorder = false;
                }
            }
            if (isRightBorder)
            {
                if ((this.getSettings().getWidth() - (lastClickOnScreen.x - e.getXOnScreen()) >= Parameters.MINIMAL_WINDOW_SIZE.width))
                {
                    this.getSettings().setWidth(this.getSettings().getWidth() - (lastClickOnScreen.x - e.getXOnScreen()));
                }
                else
                {
                    isBottomBorder = false;
                    isLeftBorder = false;
                    isRightBorder = false;
                }
            }
            if (isBottomBorder)
            {
                if ((this.getSettings().getHeight() - (lastClickOnScreen.y - e.getYOnScreen())) >= Parameters.MINIMAL_WINDOW_SIZE.height)
                {
                    this.getSettings().setHeight(this.getSettings().getHeight() - (lastClickOnScreen.y - e.getYOnScreen()));
                }
                else
                {
                    isBottomBorder = false;
                    isLeftBorder = false;
                    isRightBorder = false;
                }
            }
            lastClickOnScreen = e.getLocationOnScreen();
            this.setSize(this.getSettings().getWidth(), this.getSettings().getHeight());
        }
        else
        {
            if (!onResize)
            {
                this.getSettings().setLocationX(this.getSettings().getLocationX() + e.getX() - lastClickInside.x);
                this.getSettings().setLocationY(this.getSettings().getLocationY() + e.getY() - lastClickInside.y);
                this.setLocation(this.getSettings().getLocationX(), this.getSettings().getLocationY());
            }
        }
        this.save();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        if (lastClickInside != null)
        {
            if ((e.getX() >= this.getSettings().getWidth() - 1 && e.getX() <= this.getSettings().getWidth()) && (e.getY() >= this.getSettings().getHeight() - 1 && e.getY() <= this.getSettings().getHeight()))
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
            }
            else if ((e.getX() >= 0 && e.getX() <= 4) && (e.getY() >= this.getSettings().getHeight() - 1 && e.getY() <= this.getSettings().getHeight()))
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
            }
            else if (e.getX() >= 0 && e.getX() <= 4 && lastClickInside.getY() >= Parameters.DEFAULT_MINIMIZED_WIDTH)
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
            }
            else if (e.getX() >= this.getSettings().getWidth() - 1 && e.getX() <= this.getSettings().getWidth() && lastClickInside.getY() >= Parameters.DEFAULT_MINIMIZED_WIDTH)
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
            }
            else if (e.getY() >= this.getSettings().getHeight() - 1 && e.getY() <= this.getSettings().getHeight())
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
            }
            else
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        lastClickInside = e.getPoint();
        lastClickOnScreen = e.getLocationOnScreen();
        this.onResize = false;
        if (lastClickInside.getX() >= 0 && lastClickInside.getX() <= 4 && lastClickInside.getY() >= Parameters.DEFAULT_MINIMIZED_WIDTH)
        {
            isLeftBorder = true;
        }
        if (lastClickInside.getX() >= this.getSettings().getWidth() - 1 && lastClickInside.getX() <= this.getSettings().getWidth() && lastClickInside.getY() >= Parameters.DEFAULT_MINIMIZED_WIDTH)
        {
            isRightBorder = true;
        }
        if (lastClickInside.getY() >= this.getSettings().getHeight() - 1 && lastClickInside.getY() <= this.getSettings().getHeight())
        {
            isBottomBorder = true;
        }
        if (e.getClickCount() == 2 && !isBottomBorder && !isLeftBorder && !isRightBorder)
        {
            this.toggleNotePanelVisibility();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        isBottomBorder = false;
        isLeftBorder = false;
        isRightBorder = false;
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        Object source = e.getSource();
        if (source == opacitySlider)
        {
            this.setOpacityLVL(opacitySlider.getValue());
        }
        else if (source == fontSizeSlider)
        {
            this.setFontSize(fontSizeSlider.getValue());
        }
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
        this.menuBar.setVisible(true);
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
        if (this.panelVisibility == true)
        {
            this.menuBar.setVisible(false);
        }
    }

    //UNUSED
    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void windowOpened(WindowEvent e)
    {
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }
}
