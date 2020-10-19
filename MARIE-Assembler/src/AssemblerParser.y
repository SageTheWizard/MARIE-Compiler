/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2001 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * This is a modified version of the example from                          *
 *   http://www.lincom-asg.com/~rjamison/byacc/                            *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%{
import java.io.*;
%}

%right  NUM


%token <int>    JUMP LOAD STORE ADD SUBT CLEAR JNP STKINC STKDEC STKPSH STKPEK ADDI JUMPI STOREI LOADI
%token <int>    INPUT OUTPUT NOT HALT SKPLT SKPEQ SKPGT JMPRT
%token <obj>    ORG DEC OCT END

%token <int>    OCT_NUM DEC_NUM HEX_NUM
%token <String> LABEL

%type <obj> start prgm line line_ instr non_operand_instr operand num oct_or_dec_num oct_num_state

%%

start                : ORG num prgm	        {$$ = startOrg($2, $3);}
		             | prgm                 {$$ = start($1);}
		             ;
		
prgm                 : line prgm            {$$ = prgmLinePrgm($1, $2)}
		             | line END             {$$ = prgmLineEnd($1);}
		             ;

line                 : LABEL line_          {$$ = lineLabelLine_($1, $2);}
		             | line_			    {$$ = lineLine_($1);}
		             ;
		
line_                : instr operand        {$$ = line_InstrOperand($1, $2);}
		             | non_operand_instr    {$$ = $1;}
		             | num                  {$$ = $1;}
		             ;

instr           	 : JUMP				    {$$ = $1;}
		             | LOAD                 {$$ = $1;}
		             | STORE                {$$ = $1;}
		             | ADD                  {$$ = $1;}
		             | SUBT                 {$$ = $1;}
		             | JNP                  {$$ = $1;}
		             | STKINC               {$$ = $1;}
		             | STKDEC               {$$ = $1;}
		             | STKPSH               {$$ = $1;}
		             | STKPEK               {$$ = $1;}
		             | ADDI                 {$$ = $1;}
		             | JUMPI                {$$ = $1;}
		             | STOREI               {$$ = $1;}
		             | LOADI                {$$ = $1;}
		             ;
		
non_operand_instr    : INPUT                {$$ = $1;}
					 | OUTPUT               {$$ = $1;}
					 | HALT                 {$$ = $1;}
					 | SKPLT                {$$ = $1;}
					 | SKPEQ                {$$ = $1;}
					 | SKPGT                {$$ = $1;}
					 | JMPRT                {$$ = $1;}
					 ;

operand              : num                  {$$ = $1;}
		             | LABEL                {$$ = operandLabel($1);}
		             ;
		   
num                  : HEX_NUM              {$$ = numHex_num($1);}   
                     | oct_or_dec_num       {$$ = $1;}
		             ;

oct_or_dec_num       : oct_num_state        {$$ = $1;}
                     | DEC DEC_NUM          {$$ = numDec_num($1);}
				     ;
				  
oct_num_state        : OCT OCT_NUM          {$$ = numOct_num($1);}
                     ;
%%
    private Lexer lexer;

    private int yylex () {
        int yyl_return = -1;
        try {
            yylval = new ParserVal(0);
            yyl_return = lexer.yylex();
        }
        catch (IOException e) {
            System.out.println("IO error :"+e);
        }
        return yyl_return;
    }

    public void yyerror (String error) {
        System.out.println ("Error at line " + lexer.lineno + ": " + error);
    }

    public Parser(Reader r) {
        lexer = new Lexer(r, this);
    }
