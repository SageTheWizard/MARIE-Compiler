%%

%class MarieLexerICGN
%byaccj

%{
    public ParserICGN parser;
	public int lineno;
	public String lastIdent;

    public MarieLexerICGN(java.io.Reader r, ParserICGN parser) {
        this(r);
        this.parser = parser;
		this.lineno = 1;
		this.lastIdent = "";
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
"print"    {return Parser.PRINT;}
"bool"     {return Parser.BOOL;}
"int"      {return Parser.INT;}
"while"    {return Parser.WHILE;}
"if"       {return Parser.IF;}
"else"     {return Parser.ELSE;}
"return"   {return Parser.RETURN;}
"true"     {return Parser.BOOL_LIT;}
"false"    {return Parser.BOOL_LIT;}
";"        {return Parser.SEMI;}
","        {return Parser.COMMA;}
// Wrappers
"{"        {return Parser.LCURLY;}
"}"        {return Parser.RCURLY;}
"("        {return Parser.LCIRCLE;}
")"        {return Parser.RCIRCLE;}
"["        {return Parser.LSQUARE;}
"]"        {return Parser.RSQUARE;}
// Int operators
"="        {return Parser.ASSIGN;}
"+"        {return Parser.ADD;}
"-"        {return Parser.SUB;}
"*"        {return Parser.MUL;}
"/"        {return Parser.DIV;}
"%"        {return Parser.MOD;}
// Bool Ops
"=="       {return Parser.EQ;}
"<"        {return Parser.LT;}
">"        {return Parser.GT;}
"<="       {return Parser.LE;}
">="       {return Parser.GE;}
"!="       {return Parser.NE;}
"!"        {return Parser.NOT;}
"&&"       {return Parser.AND;}
"||"       {return Parser.OR;}
// Regex Related code
{INT_LIT}      {parser.yylval = new ParserVal((Object) yytext()); return Parser.INT_LIT;}
{IDENT}        {this.lastIdent = yytext(); parser.yylval = new ParserVal((Object) this.lastIdent); return Parser.IDENT;}
{COMMENT}      {System.out.println("Comment Detected: Ignoring");}
{NEWLINE}      {this.lineno++;}
{WS}   {System.out.println("Ignoring Whitespace");}

// Error handling
\b             {System.err.println("BACKSPACE ERROR!");}
[^]            {System.err.println("Unexpected Character: " + yytext()); return -1;}

