package wci.backend.compiler;

/**
 * <h1>Directive</h1>
 * <p>Jasmin Directives.</p>
 */
public enum Directive
{
    CLASS_PUBLIC(".class public"),
    END_CLASS(".end class"),
    SUPER(".super"),
    FIELD_PRIVATE_STATIC(".field private static"),
    METHOD_PUBLIC(".method public"),
    METHOD_STATIC(".method static"),
    METHOD_PUBLIC_STATIC(".method public static"),
    METHOD_PRIVATE_STATIC(".method private static"),
    END_METHOD(".end method"),
    LIMIT_LOCALS(".limit locals"),
    LIMIT_STACK(".limit stack"),
    VAR(".var"),
    LINE(".line");

    private final String text;

    /**
     * Constructor.
     * @param text the text for the directive.
     */
    Directive(String text)
    {
        this.text = text;
    }

    /**
     * @return the string to be emitted.
     */
    @Override
    public String toString()
    {
        return text;
    }
}
