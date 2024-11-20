package wci.backend.compiler;

/**
 * <h1>Label</h1>
 * <p>Jasmin Instruction Label</p>
 */
public class Label
{
    private static int index = 0;   // index for generating label strings
    private String label;           // the label string

    /**
     * Constructor
     */
    private Label()
    {
        this.label = "L" + String.format("%03d", ++index);
    }

    /**
     * Returns a new instruction label
     * @return new label
     */
    public static Label newLabel()
    {
        return new Label();
    }

    /**
     * Return the label string
     * @return the label string
     */
    public String toString()
    {
        return this.label;
    }
}
