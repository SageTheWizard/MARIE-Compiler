%%

%class MarieLexer
%byaccj

%{
    public MarieParser parser;
	public int lineno;

    public MarieLexer(java.io.Reader r, MarieParser) {
        this(r);
        this.parser;
		this.lineno = 1;
    }
%}

num        = [0-9]
int_lit    = {num}+
newline    = \n
comment    = "//"
identifier = [a-zA-Z_][a-zA-Z0-9_]*
whitespace = [ \t\r]+

%%

// Keywords
"print"    {return Parser.PRINT;}
"bool"     {return Parser.BOOL;}
"int"      {return Parser.INT;}
"while"    {return Parser.WHILE;}
"if"       {return Parser.IF;}
"else"     {return Parser.ELSE;}
"return"   {return Parser.RETURN;}
"true"     {return Parser.TRUE;}
"false"    {return Parser.FALSE;}
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
"-"        {return Parser.MINUS;}
"*"        {return Parser.MULTI;}
"/"        {return Parser.DIV;}
"%"        {return Parser.MOD;}
// Bool Ops
"=="       {return Parser.EQ;}
"<"        {return Parser.LT;}
">"        {return Parser.GT;}
"<="       {return Parser.LTE;}
">="       {return Parser.GTE;}
"!="       {return Parser.NE;}
"!"        {return Parser.NOT;}
"&&"       {return Parser.AND;}
"||"       {return Parser.OR;}
// Regex Related code
{int_lit}      {parser.yylval = new ParserVal((Object) yytext()); return Parser.INT_LIT;}
{identifier}   {parser.yylval = new ParserVal((Object) yytext()); return Parser.IDENT;}
{comment}      {System.out.println("Comment Detected: Ignoring");}
{newline}      {this.lineno++;}
{whitespace}   {System.out.println("Ignoring Whitespace");}

// Error handling
\b             {System.err.println("BACKSPACE ERROR!");}
[^]            {System.err.println("Unexpected Character: " + yytext()); return -1}

