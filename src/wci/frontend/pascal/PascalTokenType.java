package wci.frontend.pascal;

import java.util.Hashtable;
import java.util.HashSet;

import wci.frontend.TokenType;

/**
 * <h1>PascalTokenType</h1>
 *
 * <p>Pascal token types.</p>
 */
public enum PascalTokenType implements TokenType
{
    IDENTIFIER, INTEGER, REAL, STRING,
    ERROR, END_OF_FILE;
}
