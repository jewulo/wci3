package wci.ide.ideimpl;

import wci.ide.IDEControl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <h1>DebugFrame</h1>
 *
 * <p>The debug window of the Pascal IDE.</p>
 */
public class DebugFrame
    extends JInternalFrame
    implements ActionListener
{
    private IDEControl control;

    /**
     * Constructor.
     */
    public DebugFrame()
    {
        try {
            initGuiComponents();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Constructor.
     */
    public DebugFrame(IDEControl control)
    {
        this();     // call default constructor
        this.control = control;
    }

    /**
     * Single step button event handler.
     */
    private void singleStepAction()
    {

        control.sendToDebuggerProcess("step;");
        control.sendToDebuggerProcess("stack;");
        control.enableConsoleWindowInput();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
