.class public null
.super java/lang/Object

.field private static _runTimer LRunTimer;
.field private static _standardIn LPascalTextIn

.field private static centigrade I
.field private static fahrenheit I
.field private static freezing I
.field private static ratio I
.field private static tempc F
.field private static tempf F

.method public <init>()V

	aload_0
	invokenonvirtual	java/lang/Object/<init>()V
	return

.limit locals 1
.limit stack 1
.end method

.method public static main([Ljava/land/String;)V

	new	RunTimer
	dup
	invokenonvirtual	RunTimer/<init>()V
	putstatic	assignmenttest/_runTimer LRunTimer;
	new	PascalTextIn
	dup
	invokenonvirtual	PascalTextIn/<init>()V
	putstatic	assignmenttest/_standardIn LPascalTextIn;

.line 9
	bipush	72
	dup
	iconst_0
	sipush	200
	invokestatic	RangeChecker/check(III)V
	putstatic	assignmenttest/tempf F
.line 10
	bipush	25
	dup
	iconst_0
	sipush	200
	invokestatic	RangeChecker/check(III)V
	putstatic	assignmenttest/tempc F
.line 11
	ldc	5.0
	ldc	9.0
	fdiv
	putstatic	assignmenttest/ratio I
.line 13
	getstatic	assignmenttest/tempf F
	putstatic	assignmenttest/fahrenheit I
.line 14
	getstatic	assignmenttest/fahrenheit I
	bipush	32
	i2f
	fsub
	getstatic	assignmenttest/ratio I
	fmul
	putstatic	assignmenttest/centigrade I
.line 16
	getstatic	assignmenttest/tempc F
	putstatic	assignmenttest/centigrade I
.line 17
	bipush	32
	i2f
	getstatic	assignmenttest/centigrade I
	getstatic	assignmenttest/ratio I
	fdiv
	fadd
	putstatic	assignmenttest/fahrenheit I
.line 19
	getstatic	assignmenttest/fahrenheit I
	bipush	32
	i2f
	fcmpg
	iflt	L001
	iconst_0

	goto	L002
L001:
	iconst_1
L002:
	putstatic	assignmenttest/freezing I

	getstatic	null/_runTimer LRunTimer
	invokevirtual	RunTimer.printElapsedTime()V

	return

.limit locals 1
.limit stack 4
.end method
