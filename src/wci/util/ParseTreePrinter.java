package wci.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.io.PrintStream;

import wci.intermediate.*;
import wci.intermediate.icodeimpl.*;

/**
 * <h1>ParseTreePrinter</h1>
 *
 * <p>Print a parse tree.</p>
 */
public class ParseTreePrinter
{
    private static final int INDENT_WIDTH = 4;
    private static final int LINE_WIDTH = 80;

    private PrintStream ps;         // output print stream
    private int length;             // output line length
    private String indent;          // indent spaces
    private String indentation;     // indentation of a line
    private StringBuilder line;     // output line

    /**
     * Constructor
     * @param ps the output print stream.
     */
    public ParseTreePrinter(PrintStream ps)
    {
        this.ps = ps;
        this.length = 0;
        this.indentation = "";
        this.line = new StringBuilder();

        // The indent is INDENT_WIDTH spaces.
        this.indent = "";
        for (int i = 0; i < INDENT_WIDTH; ++i) {
            this.indent += " ";
        }
    }

    /**
     * Print intermediate code as a parse tree.
     * @param iCode the intermediate code.
     */
    public void print(ICode iCode)
    {
        ps.println("\n===== INTERMEDIATE CODE =====\n");

        printNode((ICodeNodeImpl) iCode.getRoot());
        printLine();
    }

    /**
     * Print a parse tree node
     * @param node the parse tree node
     */
    private void printNode(ICodeNodeImpl node)
    {
        // Opening tag.
        append(indentation); append("<" + node.toString());

    }

    /**
     * Append text to the output line.
     * @param text the text to append.
     */
    private void append(String text)
    {
        int textLength = text.length();
        boolean lineBreak = false;

        // Wrap lines that are too long.
        if (length + textLength > LINE_WIDTH) {
            printLine();
            line.append(indentation);
            length = indentation.length();
            lineBreak = true;
        }

        // Append the text
        if (!(lineBreak && text.equals(" "))) {
            line.append(text);
            length += textLength;
        }
    }

    /**
     * Print an output line.
     */
    private void printLine()
    {
        if (length > 0) {
            ps.println(line);
            line.setLength(0);
            length = 0;
        }
    }


}
