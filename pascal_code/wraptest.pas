program WrapTest

var
    i, j : integer;

procedure proc(var p1 : integer; p2 : integer);

    var
        m, n : integer;

    begin
        m := p1;
        n := p2;
        p1 := 10*m;
        p2 := 10*n;
    end;

begin
    i := 1;
    j := 2;

    proc(i, j);
end.