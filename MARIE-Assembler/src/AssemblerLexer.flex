%%

%class Lexer
%byaccj

%{
    public Parser parser;
	public int lineno;
	java.io.Reader r;

    public Lexer(java.io.Reader r, Parser p) {
        this(r);
        this.parser = p;
		this.lineno = 1;
    }
%}

hex_num    = [0-9A-F]+
dec_num    = [0-9]+
oct_num    = [0-7]+
newline    = \n
comment    = "//"
identifier = [a-zA-Z_][a-zA-Z0-9_]*
whitespace = [ \t\r]+
label = ":"{identifier}

%%
// Instructions
"jump"         {return Parser.JUMP;}
"load"         {return Parser.LOAD;}
"store"        {return Parser.STORE;}
"add"          {return Parser.ADD;}
"subt"         {return Parser.SUBT;}
"clear"        {return Parser.CLEAR;}
"jnp"          {return Parser.JNP;}
"stkinc"       {return Parser.STKINC;}
"stkdec"       {return Parser.STKDEC;}
"stkpsh"       {return Parser.STKPSH;}
"stkpek"       {return Parser.STKPEK;}
"addi"         {return Parser.ADDI;}
"jumpi"        {return Parser.JUMPI;}
"storei"       {return Parser.STOREI;}
"loadi"        {return Parser.LOADI;}
"input"        {return Parser.INPUT;}
"output"       {return Parser.OUTPUT;}
"not"          {return Parser.NOT;}
"halt"         {return Parser.HALT;}
"skplt"        {return Parser.SKPLT;}
"skpeq"        {return Parser.SKPEQ;}
"skpgt"        {return Parser.SKPGT;}
"jmprt"        {return Parser.JMPRT;}
//ORG, DEC, and OCT
"ORG"          {return Parser.ORG;}
"DEC"          {return Parser.DEC;}
"OCT"          {return Parser.OCT;}
"END"          {return Parser.END;}
// Regex Related code
{oct_num}      {parser.yylval = new ParserVal((Object) yytext()); return Parser.OCT_NUM;} //OCT_NUM can also be HEX_NUM and DEC_NUM, account for this when programming
{dec_num}      {parser.yylval = new ParserVal((Object) yytext()); return Parser.DEC_NUM;} //DEC_NUM can also be HEX_NUM, account for this when programming
{hex_num}      {parser.yylval = new ParserVal((Object) yytext()); return Parser.HEX_NUM;}
{label}        {parser.yylval = new ParserVal((Object) yytext()); return Parser.LABEL;}
{comment}      {System.out.println("Comment Detected: Ignoring");}
{newline}      {this.lineno++; return Parser.NEWLINE;}
{whitespace}   {System.out.println("Ignoring Whitespace");}



// Error handling
\b             {System.err.println("BACKSPACE ERROR!");}
[^]            {System.err.println("Unexpected Character: " + yytext()); return -1;}

