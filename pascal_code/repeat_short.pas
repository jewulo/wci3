
BEGIN {Calculate a square root using Newton's method.}
    number := 4;
    root := number;        

    REPEAT
        partialnum := number/root + root;
        root := partialnum/2;
    UNTIL root*root - number < 0.000001
END.