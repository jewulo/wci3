BEGIN {IF syntax errors}
    i := 0;

    { Missing THEN at ; }
    IF i = 5;

    { Unexpected Token [at ";"]. i := 5 is assigment not equality comparison. }
    IF i := 5 ELSE j := 9;

    { Missing THEN [at "ELSE"] }
    { Unexpected token [at "THEN"] }
    IF i = 5 ELSE j := 9 THEN j := 7;
END.
