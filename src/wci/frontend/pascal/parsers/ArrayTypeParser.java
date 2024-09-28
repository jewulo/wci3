package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.pascal.PascalParserTD;
import wci.intermediate.TypeFactory;
import wci.intermediate.TypeSpec;

import static wci.intermediate.typeimpl.TypeFormImpl.ARRAY;

public class ArrayTypeParser {
    public ArrayTypeParser(PascalParserTD parent) {
    }

    public TypeSpec parse(Token token)
        throws Exception
    {
        TypeSpec arrayType = TypeFactory.createType(ARRAY);

        return arrayType;
    }
}
