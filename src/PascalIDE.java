import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;

import wci.ide.*;

/**
 * <h1>PascalIDE</h1>
 * <p>The simple Pascal Integrated Development Environment</p>
 */
public class PascalIDE
{
    public PascalIDE()
    {
        new IDEFrame();
    }

    public static void main(String[] args)
    {
        new PascalIDE();
    }
}
