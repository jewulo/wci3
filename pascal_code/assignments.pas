BEGIN
    BEGIN {Temprature conversions.}
    
        five := -1 + 2 - 3 + 4 + 3;
        ratio := five/9.0;

        fahrenheit := 72;
        centigrade := (fahrenheit - 32) * ratio;

        centigrade := 25;
        fahrenheit := centigrade/ratio + 32;

        centigrade := 25;
        fahrenheit := 32 + centigrade/ratio;
    END;

    {Runtime division by zero error.}
    dze := fahrenheit/(ratio - ratio);

    BEGIN {Calculate a square root using Newton's Method.}
        number := 2;
        root := number;
        root := (number/root + root)/2;
        root := (number/root + root)/2;
        root := (number/root + root)/2;
        root := (number/root + root)/2;
        root := (number/root + root)/2;
    END;
    
    ch := 'x';
    str := 'hello world';

END.
