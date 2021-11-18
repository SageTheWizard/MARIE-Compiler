package MARIEAssembler;//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 15 "AssemblerParser.y"
import java.io.*;
//#line 19 "Parser.java"




public class Parser
             extends ParserImpl
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short NUM=257;
public final static short JUMP=258;
public final static short LOAD=259;
public final static short STORE=260;
public final static short ADD=261;
public final static short SUBT=262;
public final static short CLEAR=263;
public final static short JNP=264;
public final static short STKINC=265;
public final static short STKDEC=266;
public final static short STKPSH=267;
public final static short STKPEK=268;
public final static short ADDI=269;
public final static short JUMPI=270;
public final static short STOREI=271;
public final static short LOADI=272;
public final static short INPUT=273;
public final static short OUTPUT=274;
public final static short NEGATE=275;
public final static short HALT=276;
public final static short SKPLT=277;
public final static short SKPEQ=278;
public final static short SKPGT=279;
public final static short JMPRT=280;
public final static short ORG=281;
public final static short HEX=282;
public final static short DEC=283;
public final static short OCT=284;
public final static short END=285;
public final static short OCT_NUM=286;
public final static short DEC_NUM=287;
public final static short HEX_NUM=288;
public final static short LABEL=289;
public final static short NEWLINE=290;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    2,    2,    2,    3,    3,    3,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    5,    5,    5,    5,    5,
    5,    5,    5,    5,    6,    6,    7,    7,    7,    7,
    7,    7,    7,    8,    8,    8,    9,
};
final static short yylen[] = {                            2,
    4,    1,    2,    2,    3,    2,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    2,    2,    2,
    1,    1,    1,    1,    2,    2,    2,
};
final static short yydefred[] = {                         0,
   11,   12,   13,   14,   15,    0,   17,   18,   19,   20,
   21,   22,   23,   24,   25,   26,   27,   34,   28,   29,
   30,   31,   32,    0,    0,    0,    0,   42,   41,   37,
    0,    7,    0,    2,    0,    0,    0,    9,   10,   43,
   44,    0,   40,   39,   38,   46,   45,   47,    0,    4,
    3,    6,   36,    8,   35,    0,    5,    1,
};
final static short yydgoto[] = {                         33,
   34,   35,   36,   37,   38,   54,   39,   40,   41,
};
final static short yysindex[] = {                      -258,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -134, -131, -128, -259,    0,    0,    0,
 -159,    0,    0,    0, -225, -234, -142,    0,    0,    0,
    0, -201,    0,    0,    0,    0,    0,    0, -197,    0,
    0,    0,    0,    0,    0, -192,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0, -152,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   87,    0,  120,    0,    0,    0,  102,    0,    0,
};
final static int YYTABLESIZE=159;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          1,
    2,    3,    4,    5,    6,    7,    8,    9,   10,   11,
   12,   13,   14,   15,   16,   17,   18,   19,   20,   21,
   22,   23,   24,   25,   26,   27,   48,   28,   29,   30,
   31,   32,    1,    2,    3,    4,    5,    6,    7,    8,
    9,   10,   11,   12,   13,   14,   15,   16,   17,   18,
   19,   20,   21,   22,   23,   52,   25,   26,   27,   50,
   28,   29,   30,   31,   32,    1,    2,    3,    4,    5,
    6,    7,    8,    9,   10,   11,   12,   13,   14,   15,
   16,   17,   18,   19,   20,   21,   22,   23,   56,   25,
   26,   27,   57,   28,   29,   30,   31,   32,    1,    2,
    3,    4,    5,    6,    7,    8,    9,   10,   11,   12,
   13,   14,   15,   16,   17,   18,   19,   20,   21,   22,
   23,   51,   25,   26,   27,   42,   28,   29,   30,   16,
   16,   16,    0,   16,   16,   16,   16,   33,   55,   25,
   26,   27,   58,   28,   29,   30,   53,   25,   26,   27,
   49,   28,   29,   30,   43,   44,   45,   46,   47,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        258,
  259,  260,  261,  262,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  286,  286,  287,  288,
  289,  290,  258,  259,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  280,  290,  282,  283,  284,  285,
  286,  287,  288,  289,  290,  258,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  280,  290,  282,
  283,  284,  290,  286,  287,  288,  289,  290,  258,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,   35,  282,  283,  284,   24,  286,  287,  288,  282,
  283,  284,   -1,  286,  287,  288,  289,  290,   37,  282,
  283,  284,   56,  286,  287,  288,  289,  282,  283,  284,
   31,  286,  287,  288,  286,  287,  288,  286,  287,
};
}
final static short YYFINAL=33;
final static short YYMAXTOKEN=290;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"NUM","JUMP","LOAD","STORE","ADD","SUBT","CLEAR","JNP","STKINC",
"STKDEC","STKPSH","STKPEK","ADDI","JUMPI","STOREI","LOADI","INPUT","OUTPUT",
"NEGATE","HALT","SKPLT","SKPEQ","SKPGT","JMPRT","ORG","HEX","DEC","OCT","END",
"OCT_NUM","DEC_NUM","HEX_NUM","LABEL","NEWLINE",
};
final static String yyrule[] = {
"$accept : start",
"start : ORG num NEWLINE prgm",
"start : prgm",
"prgm : line prgm",
"prgm : line END",
"line : LABEL line_ NEWLINE",
"line : line_ NEWLINE",
"line : NEWLINE",
"line_ : instr operand",
"line_ : non_operand_instr",
"line_ : num",
"instr : JUMP",
"instr : LOAD",
"instr : STORE",
"instr : ADD",
"instr : SUBT",
"instr : CLEAR",
"instr : JNP",
"instr : STKINC",
"instr : STKDEC",
"instr : STKPSH",
"instr : STKPEK",
"instr : ADDI",
"instr : JUMPI",
"instr : STOREI",
"instr : LOADI",
"non_operand_instr : INPUT",
"non_operand_instr : OUTPUT",
"non_operand_instr : HALT",
"non_operand_instr : SKPLT",
"non_operand_instr : SKPEQ",
"non_operand_instr : SKPGT",
"non_operand_instr : JMPRT",
"non_operand_instr : CLEAR",
"non_operand_instr : NEGATE",
"operand : num",
"operand : LABEL",
"num : HEX_NUM",
"num : HEX HEX_NUM",
"num : HEX DEC_NUM",
"num : HEX OCT_NUM",
"num : DEC_NUM",
"num : OCT_NUM",
"num : oct_or_dec_num",
"oct_or_dec_num : oct_num_state",
"oct_or_dec_num : DEC DEC_NUM",
"oct_or_dec_num : DEC OCT_NUM",
"oct_num_state : OCT OCT_NUM",
};

//#line 99 "AssemblerParser.y"
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
//#line 313 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
throws Exception
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 32 "AssemblerParser.y"
{yyval.obj = startOrg(val_peek(2).obj, val_peek(0).obj);}
break;
case 2:
//#line 33 "AssemblerParser.y"
{yyval.obj = start(val_peek(0).obj);}
break;
case 3:
//#line 36 "AssemblerParser.y"
{yyval.obj = prgmLinePrgm(val_peek(1).obj, val_peek(0).obj);}
break;
case 4:
//#line 37 "AssemblerParser.y"
{yyval.obj = prgmLineEnd(val_peek(1).obj);}
break;
case 5:
//#line 40 "AssemblerParser.y"
{yyval.obj = lineLabelLine_((MARIELabel) val_peek(2).obj, val_peek(1).obj);}
break;
case 6:
//#line 41 "AssemblerParser.y"
{yyval.obj = lineLine_(val_peek(1).obj);}
break;
case 7:
//#line 42 "AssemblerParser.y"
{yyval.obj = newLine(val_peek(0).obj);}
break;
case 8:
//#line 45 "AssemblerParser.y"
{yyval.obj = line_InstrOperand(val_peek(1).obj, val_peek(0).obj);}
break;
case 9:
//#line 46 "AssemblerParser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 10:
//#line 47 "AssemblerParser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 11:
//#line 50 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.JUMP).toUpperCase();}
break;
case 12:
//#line 51 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.LOAD).toUpperCase();}
break;
case 13:
//#line 52 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.STORE).toUpperCase();}
break;
case 14:
//#line 53 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.ADD).toUpperCase();}
break;
case 15:
//#line 54 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.SUBT).toUpperCase();}
break;
case 16:
//#line 55 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.CLEAR).toUpperCase();}
break;
case 17:
//#line 56 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.JNP).toUpperCase();}
break;
case 18:
//#line 57 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.STKINC).toUpperCase();}
break;
case 19:
//#line 58 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.STKDEC).toUpperCase();}
break;
case 20:
//#line 59 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.STKPSH).toUpperCase();}
break;
case 21:
//#line 60 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.STKPEK).toUpperCase();}
break;
case 22:
//#line 61 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.ADDI).toUpperCase();}
break;
case 23:
//#line 62 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.JUMPI).toUpperCase();}
break;
case 24:
//#line 63 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.STOREI).toUpperCase();}
break;
case 25:
//#line 64 "AssemblerParser.y"
{yyval.obj = Integer.toHexString(MARIEValues.LOADI).toUpperCase();}
break;
case 26:
//#line 67 "AssemblerParser.y"
{yyval.obj = padRight(Integer.toHexString(MARIEValues.INPUT).toUpperCase());}
break;
case 27:
//#line 68 "AssemblerParser.y"
{yyval.obj = padRight(Integer.toHexString(MARIEValues.OUTPUT).toUpperCase());}
break;
case 28:
//#line 69 "AssemblerParser.y"
{yyval.obj = padRight(Integer.toHexString(MARIEValues.HALT).toUpperCase());}
break;
case 29:
//#line 70 "AssemblerParser.y"
{yyval.obj = padRight(Integer.toHexString(MARIEValues.SKPLT).toUpperCase());}
break;
case 30:
//#line 71 "AssemblerParser.y"
{yyval.obj = padRight(Integer.toHexString(MARIEValues.SKPEQ).toUpperCase());}
break;
case 31:
//#line 72 "AssemblerParser.y"
{yyval.obj = padRight(Integer.toHexString(MARIEValues.SKPGT).toUpperCase());}
break;
case 32:
//#line 73 "AssemblerParser.y"
{yyval.obj = padRight(Integer.toHexString(MARIEValues.JMPRT).toUpperCase());}
break;
case 33:
//#line 74 "AssemblerParser.y"
{yyval.obj = padRight(Integer.toHexString(MARIEValues.CLEAR).toUpperCase());}
break;
case 34:
//#line 75 "AssemblerParser.y"
{yyval.obj = padRight(Integer.toHexString(MARIEValues.NEGATE).toUpperCase());}
break;
case 35:
//#line 78 "AssemblerParser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 36:
//#line 79 "AssemblerParser.y"
{yyval.obj = operandLabel((MARIELabel) val_peek(0).obj);}
break;
case 37:
//#line 82 "AssemblerParser.y"
{yyval.obj = numHex_num(val_peek(0).obj);}
break;
case 38:
//#line 83 "AssemblerParser.y"
{yyval.obj = numHex_num(val_peek(0).obj);}
break;
case 39:
//#line 84 "AssemblerParser.y"
{yyval.obj = numHex_num(val_peek(0).obj);}
break;
case 40:
//#line 85 "AssemblerParser.y"
{yyval.obj = numHex_num(val_peek(0).obj);}
break;
case 41:
//#line 86 "AssemblerParser.y"
{yyval.obj = numHex_num(val_peek(0).obj);}
break;
case 42:
//#line 87 "AssemblerParser.y"
{yyval.obj = numHex_num(val_peek(0).obj);}
break;
case 43:
//#line 88 "AssemblerParser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 44:
//#line 91 "AssemblerParser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 45:
//#line 92 "AssemblerParser.y"
{yyval.obj = numDec_num(val_peek(0).obj);}
break;
case 46:
//#line 93 "AssemblerParser.y"
{yyval.obj = numDec_num(val_peek(0).obj);}
break;
case 47:
//#line 96 "AssemblerParser.y"
{yyval.obj = numOct_num(val_peek(0).obj);}
break;
//#line 650 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
