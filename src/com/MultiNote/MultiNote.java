/*
 * MultiNote.java
 *
 * 2013
 *
 * Created by Marc-Alexandre Blanchard - all right reserved ©
 *
 */
package com.MultiNote;

import java.io.File;

/**
 *
 * @author Marc_Alx
 */
public class MultiNote
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        NoteManager NM = NoteManager.getInstance();
        File f = new File(Parameters.FILENAME);
        if (f.exists())
        {
            NM.load();
            NM.createNotes();
        }
        else
        {
            NM.addNote();
        }
    }
}
