const
    ten = 10;
    epsilon = 1.0E-6;
    x = 'x';
    limit = epsilon;
    hello = 'Hello, world!';

type
    range1 = 0..ten;
    range2 = 'a'..'q';
    range3 = range1;

    enum1 = (a, b, c, d, e);
    enum2 = enum1;
    range4 = b..d;

    week = (monday, tuesday, wednesday, thursday, friday, saturday, sunday);
    weekday = monday..friday;
    weekend = saturday..sunday;

    ar1 = array [range1] of integer;
    ar2 = array [(alpha, beta, gamma)] of range2;
    ar3 = array [enum2] of ar1;
    ar4 = array [range3] of (foo, bar, baz);
    ar5 = array [range1] of array [range2] of array [c..e] of enum2;
    ar6 = array [range1, range2, c..e] of enum2;

    rec1 = record
                i       : integer;
                r       : real;
                b1, b2  : boolean;
                c       : char
           end;

    ar7 = array [range2] of record
                                ten : integer;
                                r   : rec1;
                                a   : array[range4] of range2
                            end;

var
    var1                : integer;
    var2, var3          : range2;
    var4                : enum2;
    var5, var6, var7    : -7..ten;
    var8                : (fee, fye, foe, fum);
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