/*
 * NoteManager.java
 *
 * 2013
 *
 * Created by Marc-Alexandre Blanchard - all right reserved ©
 *
 */
package com.MultiNote;

import com.toaster.engine.Toaster;
import com.toaster.exceptions.TooManyToastException;
import java.io.File;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author Crée par Marc-Alexandre Blanchard
 */
final class NoteManager
{

    /**
     * A non initialise INSTANCE of a NoteManager
     */
    private static NoteManager NoteManager_INSTANCE = null;
    private int nbNotes;
    private ArrayList<Settings> notesSettings;
    private final XMLManager xMLManager;
    private final Toaster toaster;

    private NoteManager()
    {
        nbNotes = 0;
        notesSettings = new ArrayList<>();
        xMLManager = XMLManager.getInstance();
        toaster = Toaster.getInstance();
    }

    /**
     *
     * @return an instance of a NoteManager
     */
    public static synchronized NoteManager getInstance()
    {
        if (NoteManager_INSTANCE == null)
        {
            NoteManager_INSTANCE = new NoteManager();
        }
        return NoteManager_INSTANCE;
    }

    /**
     * Load settings from file
     */
    public void load()
    {
        notesSettings = xMLManager.getSettingsFromXML();
    }

    /**
     * Save settings to xml
     */
    public void Save()
    {
        xMLManager.saveSettingsToXML(notesSettings);
    }

    /**
     * Add a note to screen
     */
    public void addNote()
    {
        if (nbNotes <= 10)
        {
            Note n = new Note();
            n.getSettings().setId(getNbNotes());
            notesSettings.add(n.getSettings());
            if (getNbNotes() >= 1)
            {
                n.getSettings().setLocationX(n.getSettings().getLocationX() + 5 * nbNotes);
                n.getSettings().setLocationY(n.getSettings().getLocationY() + 5 * nbNotes);
                n.getSettings().setContentTXT("");
            }
            nbNotes++;
            this.Save();
            n.init();
        }
        else
        {
            String message = "Too many notes on screen !";
            if (toaster.getNbToastOnScreen() < toaster.getMaxNbToastOnScreen())
            {
                try
                {
                    toaster.Toast(message);
                }
                catch (TooManyToastException ex)
                {
                }
            }
        }
    }

    /**
     * Remove a note from screen
     *
     * @param idNote the id of the note
     */
    public void removeNote(int idNote)
    {

        if (getNbNotes() == 1)
        {
            notesSettings.remove(0);
            nbNotes--;
            File f = new File(Parameters.FILENAME);
            if (f.exists())
            {
                f.delete();
            }
            this.quit();
        }
        else
        {
            for (int i = 0; i < notesSettings.size(); i++)
            {
                if (notesSettings.get(i).getId() == idNote)
                {
                    notesSettings.remove(notesSettings.get(i));
                }
            }
            for (int i = 0; i < notesSettings.size(); i++)
            {
                notesSettings.get(i).setId(i);
            }
            this.Save();
            nbNotes--;
        }
    }

    /**
     * Create note from the loaded settings
     */
    public void createNotes()
    {
        Note temp;
        nbNotes = 0;
        for (Settings notesSetting : notesSettings)
        {
            SwingUtilities.invokeLater(new NoteApp(notesSetting));
            nbNotes++;
        }
    }

    /**
     * @return the nbNotes
     */
    public int getNbNotes()
    {
        return nbNotes;
    }

    public void quit()
    {
        if (getNbNotes() != 0)
        {
            this.Save();
        }
        System.exit(0);
    }
}
