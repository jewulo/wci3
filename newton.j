.class public null
.super java/lang/Object

.field private static _runTimer LRunTimer;
.field private static _standardIn LPascalTextIn

.field private static number F

.method public <init>()V

	aload_0
	invokenonvirtual	java/lang/Object/<init>()V
	return

.limit locals 1
.limit stack 1
.end method

.method private static root(I)I

.var 1 is r I
.var 0 is x I
.var 2 is root I

.line 14
	fconst_1
	fstore_1
.line 15
.line 18
	fload_1
	fstore_2

	fload_2
	freturn

.limit locals 3
.limit stack 1
.end method

.method private static print(FI)V

.var 0 is n F
.var 1 is root I

.line 23

	return

.limit locals 2
.limit stack 0
.end method

.method public static main([Ljava/land/String;)V

	new	RunTimer
	dup
	invokenonvirtual	RunTimer/<init>()V
	putstatic	newton/_runTimer LRunTimer;
	new	PascalTextIn
	dup
	invokenonvirtual	PascalTextIn/<init>()V
	putstatic	newton/_standardIn LPascalTextIn;

.line 27

	getstatic	null/_runTimer LRunTimer
	invokevirtual	RunTimer.printElapsedTime()V

	return

.limit locals 1
.limit stack 3
.end method
