package wci.frontend.pascal.parsers;

import wci.frontend.pascal.PascalParserTD;
import wci.intermediate.symtabimpl.DefinitionImpl;

public class VariableDeclarationsParser extends DeclarationsParser {
    public VariableDeclarationsParser(PascalParserTD parent) {
        super(parent);
    }

    public void setDefinition(DefinitionImpl definition) {
    }
}
