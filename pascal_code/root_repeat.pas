BEGIN {Calculate a square root using Newton's method.}
    number := 4;
    root := number;
    partialnum := 0;
    REPEAT
        partialnum := number/root + root;
        root := partialnum/2;
    UNTIL root*root - number < 0.000001
END.