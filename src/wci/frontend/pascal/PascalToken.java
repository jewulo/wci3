package wci.frontend.pascal;

import wci.frontend.*;

/**
 * <h1>PascalToken</h1>
 *
 * <p>Base class for pascal token classes.</p>
 */
public class PascalToken extends Token
{
    /**
     * Constructor
     *
     * @param source the source from where to fetch the tokens characters.
     * @throws Exception if an error occurred.
     */
    public PascalToken(Source source)
            throws Exception
    {
        super(source);
    }
}
