%%

%class MarieLexer
%byaccj

%{
    public MarieParser parser;
	public int lineno;

    public MarieLexer(java.io.Reader r, MarieParser parser) {
        this(r);
        this.parser = parser;
		this.lineno = 1;
    }
%}

NUM        = [0-9]
INT_LIT    = {NUM}+
NEWLINE    = \n
COMMENT    = "//"
IDENT = [a-zA-Z_][a-zA-Z0-9_]*
WS = [ \t\r]+

%%

// Keywords
"print"    {return MarieParser.PRINT;}
"bool"     {return MarieParser.BOOL;}
"int"      {return MarieParser.INT;}
"while"    {return MarieParser.WHILE;}
"if"       {return MarieParser.IF;}
"else"     {return MarieParser.ELSE;}
"return"   {return MarieParser.RETURN;}
"true"     {return MarieParser.BOOL_LIT;}
"false"    {return MarieParser.BOOL_LIT;}
";"        {return MarieParser.SEMI;}
","        {return MarieParser.COMMA;}
// Wrappers
"{"        {return MarieParser.LCURLY;}
"}"        {return MarieParser.RCURLY;}
"("        {return MarieParser.LCIRCLE;}
")"        {return MarieParser.RCIRCLE;}
"["        {return MarieParser.LSQUARE;}
"]"        {return MarieParser.RSQUARE;}
// Int operators
"="        {return MarieParser.ASSIGN;}
"+"        {return MarieParser.ADD;}
"-"        {return MarieParser.SUB;}
"*"        {return MarieParser.MUL;}
"/"        {return MarieParser.DIV;}
"%"        {return MarieParser.MOD;}
// Bool Ops
"=="       {return MarieParser.EQ;}
"<"        {return MarieParser.LT;}
">"        {return MarieParser.GT;}
"<="       {return MarieParser.LE;}
">="       {return MarieParser.GE;}
"!="       {return MarieParser.NE;}
"!"        {return MarieParser.NOT;}
"&&"       {return MarieParser.AND;}
"||"       {return MarieParser.OR;}
// Regex Related code
{INT_LIT}      {parser.yylval = new ParserVal((Object) yytext()); return Parser.INT_LIT;}
{IDENT}        {parser.yylval = new ParserVal((Object) yytext()); return Parser.IDENT;}
{COMMENT}      {System.out.println("Comment Detected: Ignoring");}
{NEWLINE}      {this.lineno++;}
{WS}   {System.out.println("Ignoring Whitespace");}

// Error handling
\b             {System.err.println("BACKSPACE ERROR!");}
[^]            {System.err.println("Unexpected Character: " + yytext()); return -1;}

