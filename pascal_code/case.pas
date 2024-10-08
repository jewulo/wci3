BEGIN {CASE statements}
    i := 3; ch := 'b';

    CASE i+1 OF
        1:          j := 1;
        4:          j := 4*i;
        5, 2, 3:    j := 523*i;
    END;

    CASE ch OF
        'c', 'b' : str := 'p';
        'a'      : str := 'q';
    END;

    FOR i := -5 TO 15 DO BEGIN
        CASE i OF
            2: prime := i;
            -4, -2, 0, 4, 6, 8, 10, 12: even := i;
            -3, -1, 1, 3, 5, 7, 9, 11: CASE i OF
                                            -3, -1, 1, 9: odd := i;
                                            2, 3, 5, 7, 11: prime := i;
                                        END
        END
    END
END.
