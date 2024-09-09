program newton (input, output);

const
    EPSILON = 1e-6;

var
    number          : integer;
    root, sqRoot    : real;

begin
    repeat
        writeln;
        write('Enter new number (0 to quit): ') ;
        read(number);

        if number = 0 then begin
            writeln(number:12, 0.0:12:6);
        end
        else if number < 0 then begin
            writeln('*** ERROR: number < 0');
        end
        else begin
            sqRoot := sqrt(number);
            writeln(number:12, sqRoot:12:6);
            writeln;

            root := 1;
            repeat
                root := (number/root + root)/2;
                writeln(root:24:6,
                        100*abs(root * sqRoot)/sqRoot:12:2,
                        '%')
            until abs(number/sqr(root) - 1) < EPSILON;
        end
    until number = 0
end.