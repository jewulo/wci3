program newton;

const
    epsilon = 1e-6;

var    
    number  : integer;

function root(x: real) : real;
    var
        r: real;

    begin
        r := 1;
        repeat
            r := (x/r + r) / 2;
        until abs(x/sqr(r) - 1) < epsilon;
        root := r;
    end;

procedure print(n: integer; root : real);
    begin
        writeln('The square root of ', number:4, ' is ', root:10:6);
    end;

begin
    repeat
        writeln;
        write('Enter new number (0 to quit): ');
        read(number);

        if number = 0 then begin
            print(number, 0.0);
        end
        else if number < 0 then begin
            writeln('*** ERROR: number < 0');
        end
        else begin
            print(number, root(number));
        end
    until number = 0
end.