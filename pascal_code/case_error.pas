BEGIN {CASE syntax errors}
    i := 0; ch := 'x'; str := 'y';

    CASE i OF
        1 2 3: j := 1;
        4,1,5 IF j = 5 THEN k := 9;
    END;

    CASE ch1 OF
        'x', 'hello', 'y':  str := 'world';
        'z', 'x':           str := 'bye';
    END
END.

