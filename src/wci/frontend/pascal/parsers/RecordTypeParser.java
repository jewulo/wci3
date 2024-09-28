package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.pascal.PascalParserTD;
import wci.intermediate.TypeFactory;
import wci.intermediate.TypeSpec;

import static wci.intermediate.typeimpl.TypeFormImpl.RECORD;

public class RecordTypeParser {
    public RecordTypeParser(PascalParserTD parent) {
    }

    public TypeSpec parse(Token token)
        throws Exception
    {
        TypeSpec recordType = TypeFactory.createType(RECORD);

        return recordType;
    }
}
