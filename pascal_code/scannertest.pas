{This is a comment.}

{This is a comment
 that spans several
 source lines.}

 Two {comments in}{a row} here

 {Word tokens}
 Hello world
 begin BEGIN Begin BeGIN begins

 {String tokens}
 'Hello, world.'
 'It''s Friday!'
 ''
 ' '' '' '  ''''''
'This string
spans
source lines'

{Special symbol tokens}
+ - * / := . , ; : = <> , < <= >= > ( ) [ ] { } } ^ ..
+-:=<>=<==...

{Number Tokens}
0 1 20 0000000000000000000032 31415926
3.1415926 3.1415926535897932384626433
0.00031415926E4 0.00031415926e+00004 31415.926e-4
314159260000000000000000000000e-30

{Decimal point or ..}
3.14 3..14

{Bad tokens}
123e99 12345678912345 1234.56E. 3. 5.. .14 314.e-2
What?
'String not closed
