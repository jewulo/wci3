BEGIN {WHILE statements.}
    i := 0; j := 0;

    WHILE i > j DO k := 1;

    BEGIN {Calculate a square root using Newton's method.}
        number := 2;
        root := number;

        WHILE root*root - number > 0.000001 DO BEGIN
            partialnum := number/root + root;
            root := partialnum/2
        END
    END;
END.
