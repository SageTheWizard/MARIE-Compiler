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


%token <obj>    JUMP LOAD STORE ADD SUBT CLEAR JNP STKINC STKDEC STKPSH STKPEK ADDI JUMPI STOREI LOADI
%token <obj>    INPUT OUTPUT NOT HALT SKPLT SKPEQ SKPGT JMPRT
%token <obj>    ORG DEC OCT END

%token <obj>    OCT_NUM DEC_NUM HEX_NUM
%token <obj> LABEL

%type <obj> start prgm line line_ instr non_operand_instr operand num oct_or_dec_num oct_num_state

%%

start                : ORG num prgm	        {$$ = startOrg($2, $3);}
		             | prgm                 {$$ = start($1);}
		             ;
		
prgm                 : line prgm            {$$ = prgmLinePrgm($1, $2);}
		             | line END             {$$ = prgmLineEnd($1);}
		             ;

line                 : LABEL line_          {$$ = lineLabelLine_($1, $2);}
		             | line_			    {$$ = lineLine_($1);}
		             ;
		
line_                : instr operand        {$$ = line_InstrOperand($1, $2);}
		             | non_operand_instr    {$$ = $1;}
		             | num                  {$$ = $1;}
		             ;

instr           	 : JUMP				    {$$ = MARIEValues.JUMP;}
		             | LOAD                 {$$ = MARIEValues.LOAD;}
		             | STORE                {$$ = MARIEValues.STORE;}
		             | ADD                  {$$ = MARIEValues.ADD;}
		             | SUBT                 {$$ = MARIEValues.SUBT;}
					 | CLEAR                {$$ = MARIEValues.CLEAR;}
		             | JNP                  {$$ = MARIEValues.JNP;}
		             | STKINC               {$$ = MARIEValues.STKINC;}
		             | STKDEC               {$$ = MARIEValues.STKDEC;}
		             | STKPSH               {$$ = MARIEValues.STKPSH;}
		             | STKPEK               {$$ = MARIEValues.STKPEK;}
		             | ADDI                 {$$ = MARIEValues.ADDI;}
		             | JUMPI                {$$ = MARIEValues.JUMPI;}
		             | STOREI               {$$ = MARIEValues.STOREI;}
		             | LOADI                {$$ = MARIEValues.LOADI;}
		             ;
		
non_operand_instr    : INPUT                {$$ = MARIEValues.INPUT;}
					 | OUTPUT               {$$ = MARIEValues.OUTPUT;}
					 | HALT                 {$$ = HALT;}
					 | SKPLT                {$$ = MARIEValues.SKPLT;}
					 | SKPEQ                {$$ = MARIEValues.SKPEQ;}
					 | SKPGT                {$$ = MARIEValues.SKPGT;}
					 | JMPRT                {$$ = MARIEValues.JMPRT;}
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
