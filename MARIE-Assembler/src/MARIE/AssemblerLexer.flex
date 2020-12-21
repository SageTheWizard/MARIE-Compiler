%%

%class Lexer
%byaccj

%{
    public Parser parser;
	public int lineno;
	public int lineOffset;
	private boolean countWhitespace = true;
	java.io.Reader r;

    public Lexer(java.io.Reader r, Parser p) {
        this(r);
        this.parser = p;
		this.lineno = 0;
		this.lineOffset = 0;
    }
%}

hex_num    = "-"?[0-9A-F]+
dec_num    = "-"?[0-9]+
oct_num    = "-"?[0-7]+
newline    = \n
comment    = "//" //TODO fix
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
"negate"       {return Parser.NEGATE;}
"halt"         {return Parser.HALT;}
"skplt"        {return Parser.SKPLT;}
"skpeq"        {return Parser.SKPEQ;}
"skpgt"        {return Parser.SKPGT;}
"jmprt"        {return Parser.JMPRT;}
//ORG, DEC, and OCT
"ORG"          {lineOffset++; return Parser.ORG;}
"HEX"          {return Parser.HEX;}
"DEC"          {return Parser.DEC;}
"OCT"          {return Parser.OCT;}
"END"          {countWhitespace = false; return Parser.END;}
// Regex Related code
{oct_num}      {parser.yylval = new ParserVal((Object) yytext()); return Parser.OCT_NUM;} //OCT_NUM can also be HEX_NUM and DEC_NUM, account for this when programming
{dec_num}      {parser.yylval = new ParserVal((Object) yytext()); return Parser.DEC_NUM;} //DEC_NUM can also be HEX_NUM, account for this when programming
{hex_num}      {parser.yylval = new ParserVal((Object) yytext()); return Parser.HEX_NUM;}
{label}        {parser.yylval = new ParserVal(new MARIELabel(this.lineno - lineOffset, yytext() + ":")); return Parser.LABEL;}//we add the colon to the end so we can separate similarly-named variables, ex :var, :var2, etc.
{comment}      {lineOffset++;}
{newline}      {this.lineno++; if(countWhitespace){return Parser.NEWLINE;}}
{whitespace}   {}



// Error handling
\b             {System.err.println("BACKSPACE ERROR!");}
[^]            {System.err.println("Unexpected Character: " + yytext() + " on line: " + lineno); return -1;}

