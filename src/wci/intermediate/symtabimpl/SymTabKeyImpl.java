package wci.intermediate.symtabimpl;

import wci.intermediate.SymTabKey;

/**
 * <h1>SymTabKeyImpl</h1>
 *
 * <p>Attribute keys for a symbol table entry.</p>
 */
public enum SymTabKeyImpl
{
    // Constant.
    CONSTANT_VALUE,

    // Procedure or Function.
    ROUTINE_CODE, ROUTINE_SYMTAB, ROUTINE_ICODE,
    ROUTINE_PARAMS, ROUTINE_ROUTINES,

    // Variable or record field value.
    DATA_VALUE
}
