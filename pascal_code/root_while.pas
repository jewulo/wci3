BEGIN {Calculate a square root using Newton's method.}
    number := 2;
    root := number;

    WHILE root*root - number > 0.000001 DO BEGIN
        root := (number/root + root)/2;
    END
END.