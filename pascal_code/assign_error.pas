BEGIN
    BEGIN {Temperature conversions.}
        five  := -1 + 2 - 3 + 4 - -3;
        ratio := five/9.0;

        fahrenheit := 72;
        centigrade := (((fahrenheit - 32)))*ratio;

        centigrade := 25;;;
        fahrenheit := centigrade/ratio + 32;

        centigrade := 25
        fahrenheit := 32 + centigrade/ratio;

        centigrade := 25;
        fahrenheit := celsius/ratio + 32
    END

    dze fahrenheit/((ratio - ratio) := ;
END.