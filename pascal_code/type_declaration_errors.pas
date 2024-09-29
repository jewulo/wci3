const
    ten = 10;
    epsilon = 1.0E-6;
    x = 'x';
    limit = epsilon/2;
    pi = pi;
    hello = 'Hello, world!';

type
    typp = typp;
    range1 = 0..tenn;
    range2 = 'a'..'q';
    range3 = 0..10.0;
    range5 = 'q'..'p';
    range6 = foo..bar;

    enum1 = (a, b, c, d, e);
    enum2 = enum1;
    range4 = b..d;
    range3 = e..c;

    week = (monday, tuesday, wednesday, thursday, friday, saturday, sunday);
    weekday = monday..friday;
    weekend = saturday..sunday;

    ar1 = array [integer] of integer;
    ar2 = array [(alpha, beta, gamma)] of range2;
    ar3 = array [enum2] of ar1;
    ar4 = array [(foo, bar, baz)] of (foo, bar);
    ar5 = array [range1] of array [range2] of array [c..e] of enum2;
    ar6 = array [range1, range2, c..e] of enum2;

    rec1 = record
                i       : integer;
                r       : real;
                i       : integer;
           end;

    ar7 = array [range2] record
                            ten : integer;
                            r   : rec1;
                            a   : array[range4] of range2

var
    var1                : integer;
    var2, var3          : range2;
    var4                : enum2;
    var5, var6, var7    : ten..5;
    var8                : (fee, fye, foe, FYE, fum);
    var9                : range3;

    var10 : rec1;
    var11 : record
                b : boolean;
                r : record
                        aa  : ar1;
                        bb  : boolean;
                        r   : real;
                        v1  : ar6;
                        v2  : array [enum1, range1] of ar7;
                    end;
                a : array [1..5] of boolean;
            end;

    var12 : ar1;
    var15 : ar2;
    var16 : ar6;

    number  : range1;
    root    : real;

begin
end.