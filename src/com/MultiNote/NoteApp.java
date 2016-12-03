/**
 *
 * NoteApp.java
 *
 * Created by Marc-Alexandre Blanchard - all right reserved ©
 *
 * 2014
 *
 */
package com.MultiNote;

/**
 *
 * @author Crée par Marc-Alexandre Blanchard
 */
class NoteApp implements Runnable
{

    private final Note n;

    public NoteApp(Settings s)
    {
        this.n = new Note(s);
    }

    @Override
    public void run()
    {
        n.init();
    }

}
