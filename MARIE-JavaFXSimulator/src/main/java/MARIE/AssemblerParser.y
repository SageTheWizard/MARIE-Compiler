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
%token <obj>    INPUT OUTPUT NEGATE HALT SKPLT SKPEQ SKPGT JMPRT
%token <obj>    ORG HEX DEC OCT END

%token <obj>    OCT_NUM DEC_NUM HEX_NUM
%token <obj> LABEL NEWLINE

%type <obj> start prgm line line_ instr non_operand_instr operand num oct_or_dec_num oct_num_state

%%

start                : ORG num NEWLINE prgm	        {$$ = startOrg($2, $4);}
		             | prgm                     {$$ = start($1);}
		             ;
		
prgm                 : line prgm            {$$ = prgmLinePrgm($1, $2);}
		             | line END             {$$ = prgmLineEnd($1);}
		             ;

line                 : LABEL line_ NEWLINE         {$$ = lineLabelLine_((MARIELabel) $1, $2);}
		             | line_ NEWLINE       {$$ = lineLine_($1);}
		             | NEWLINE             {$$ = newLine($1);}
		             ;
		
line_                : instr operand        {$$ = line_InstrOperand($1, $2);}
		             | non_operand_instr    {$$ = $1;}
		             | num                  {$$ = $1;}
		             ;

instr           	 : JUMP			    {$$ = Integer.toHexString(MARIEValues.JUMP).toUpperCase();}
		             | LOAD                 {$$ = Integer.toHexString(MARIEValues.LOAD).toUpperCase();}
		             | STORE                {$$ = Integer.toHexString(MARIEValues.STORE).toUpperCase();}
		             | ADD                  {$$ = Integer.toHexString(MARIEValues.ADD).toUpperCase();}
		             | SUBT                 {$$ = Integer.toHexString(MARIEValues.SUBT).toUpperCase();}
			     | CLEAR                {$$ = Integer.toHexString(MARIEValues.CLEAR).toUpperCase();}
		             | JNP                  {$$ = Integer.toHexString(MARIEValues.JNP).toUpperCase();}
		             | STKINC               {$$ = Integer.toHexString(MARIEValues.STKINC).toUpperCase();}
		             | STKDEC               {$$ = Integer.toHexString(MARIEValues.STKDEC).toUpperCase();}
		             | STKPSH               {$$ = Integer.toHexString(MARIEValues.STKPSH).toUpperCase();}
		             | STKPEK               {$$ = Integer.toHexString(MARIEValues.STKPEK).toUpperCase();}
		             | ADDI                 {$$ = Integer.toHexString(MARIEValues.ADDI).toUpperCase();}
		             | JUMPI                {$$ = Integer.toHexString(MARIEValues.JUMPI).toUpperCase();}
		             | STOREI               {$$ = Integer.toHexString(MARIEValues.STOREI).toUpperCase();}
		             | LOADI                {$$ = Integer.toHexString(MARIEValues.LOADI).toUpperCase();}
		             ;
		
non_operand_instr    : INPUT                {$$ = padRight(Integer.toHexString(MARIEValues.INPUT).toUpperCase());}
					 | OUTPUT               {$$ = padRight(Integer.toHexString(MARIEValues.OUTPUT).toUpperCase());}
					 | HALT                 {$$ = padRight(Integer.toHexString(MARIEValues.HALT).toUpperCase());}
					 | SKPLT                {$$ = padRight(Integer.toHexString(MARIEValues.SKPLT).toUpperCase());}
					 | SKPEQ                {$$ = padRight(Integer.toHexString(MARIEValues.SKPEQ).toUpperCase());}
					 | SKPGT                {$$ = padRight(Integer.toHexString(MARIEValues.SKPGT).toUpperCase());}
					 | JMPRT                {$$ = padRight(Integer.toHexString(MARIEValues.JMPRT).toUpperCase());}
					 | CLEAR                {$$ = padRight(Integer.toHexString(MARIEValues.CLEAR).toUpperCase());}
					 | NEGATE               {$$ = padRight(Integer.toHexString(MARIEValues.NEGATE).toUpperCase());}
					 ;

operand              : num                  {$$ = $1;}
		             | LABEL                {$$ = operandLabel((MARIELabel) $1);}
		             ;
		   
num                  : HEX_NUM              {$$ = numHex_num($1);}
		     | HEX HEX_NUM          {$$ = numHex_num($2);}
		     | HEX DEC_NUM          {$$ = numHex_num($2);}
		     | HEX OCT_NUM          {$$ = numHex_num($2);}
		     | DEC_NUM		    {$$ = numHex_num($1);}
		     | OCT_NUM              {$$ = numHex_num($1);}
                     | oct_or_dec_num       {$$ = $1;}
		             ;

oct_or_dec_num       : oct_num_state        {$$ = $1;}
                     | DEC DEC_NUM          {$$ = numDec_num($2);}
                     | DEC OCT_NUM          {$$ = numDec_num($2);}
				     ;
				  
oct_num_state        : OCT OCT_NUM          {$$ = numOct_num($2);}
                     ;
%%
    String primaryErr = "";

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
    	if(primaryErr.equals(""))
    	{
    	    primaryErr = "Error at line " + lexer.lineno + ": " + error;
    	}
    	System.out.println("Error at line " + lexer.lineno + ": " + error);
    }

    public Parser(Reader r) {
    	this.lexer = new Lexer(r, this);
    }
