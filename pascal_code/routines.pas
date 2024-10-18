program RoutinesTest (input, output);

const
    five = 5;

type
    enum = (aplha, beta, gamma);
    arr = array [1..five] of real;

var
    e, k : enum;
    i, m : integer;
    a : arr;
    v, y : real;
    t : boolean;

procedure proc(j, k : integer; var x, y, z : real; var v : arr; var p : boolean; ch : char);
    var 
        i : real;

    begin
        i := 7*k;
        x := 3.14;
    end;

function forwarded(m : integer; VAR t : real) : real; forward;

function func(var x : real; i : real; n : integer) : real;
    var
        z : real;
        p, q : boolean;
    
    procedure nested(var n, m : integer);
        const
            ten = 10;
        
        type
            subrange = five..ten;
        
        var
            a, b, c : integer;
            s : subrange;

        procedure deeply;

            var
                w : real;
                
            begin
                w := i;
                nested(a, m);
                w := forwarded(b, w);
            end;
            
        begin {nested}
            s := m;
            deeply;
            a := s;
        end {nested};
        
    begin {func}
        p := true;
        q := false;
        x := i*z - func(v, -3.15159, five) + n/m;
        func := x;
    end {func}

function forwarded;
    var
        n : integer;

    begin
        forwarded := n*y - t;
    end;

begin {RoutinesTest}
    e := beta;
    proc(i, -7 + m, a[m], v, y, a, t, 'r');
end {RoutinesTest}.









