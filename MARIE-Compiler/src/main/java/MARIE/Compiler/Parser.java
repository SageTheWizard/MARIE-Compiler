//### This file created by BYACC 1.8(/Java extension  1.15)
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






//#line 2 "MarieParser.y"
package MARIE.Compiler;
import java.io.*;
import java.util.*;
//#line 21 "Parser.java"




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
public final static short ASSIGN=257;
public final static short OR=258;
public final static short AND=259;
public final static short EQ=260;
public final static short NE=261;
public final static short LE=262;
public final static short LT=263;
public final static short GE=264;
public final static short GT=265;
public final static short ADD=266;
public final static short SUB=267;
public final static short MUL=268;
public final static short DIV=269;
public final static short MOD=270;
public final static short NOT=271;
public final static short IDENT=272;
public final static short INT_LIT=273;
public final static short BOOL_LIT=274;
public final static short BOOL=275;
public final static short INT=276;
public final static short IF=277;
public final static short ELSE=278;
public final static short PRINT=279;
public final static short WHILE=280;
public final static short RETURN=281;
public final static short LCIRCLE=282;
public final static short RCIRCLE=283;
public final static short LCURLY=284;
public final static short RCURLY=285;
public final static short LSQUARE=286;
public final static short RSQUARE=287;
public final static short SEMI=288;
public final static short COMMA=289;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    5,    5,   23,   24,
    4,    8,    8,    9,    9,   10,   18,   18,   19,   19,
   19,   19,   19,   19,   19,   21,   21,   21,   25,   13,
   26,   14,   27,   15,   16,   16,   17,   20,    6,    6,
    7,   12,   12,   11,   11,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    1,    3,    1,    1,    0,    0,
   11,    1,    0,    3,    1,    2,    2,    0,    1,    1,
    1,    1,    1,    1,    1,    3,    2,    6,    0,    6,
    0,    5,    0,    9,    4,    0,    3,    3,    2,    0,
    3,    3,    1,    1,    0,    3,    3,    3,    3,    3,
    3,    3,    2,    3,    3,    3,    3,    3,    3,    3,
    1,    4,    4,    1,    1,
};
final static short yydefred[] = {                         3,
    0,    0,    7,    8,    2,    4,    5,    0,    0,    0,
    6,    0,    0,    0,   15,   16,    9,    0,    0,   14,
   10,   40,    0,    0,   39,    0,    0,    0,    0,   65,
   64,    0,    0,    0,    0,    0,   31,   11,   25,   22,
   20,   21,   24,   17,   23,   19,    0,   41,    0,   53,
    0,    0,    0,    0,    0,    0,    0,    0,   40,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   27,    0,   26,    0,    0,    0,    0,    0,
   37,    0,   38,   60,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   48,   49,   50,    0,   62,
    0,    0,   33,   29,    0,   63,    0,    0,    0,    0,
   32,   28,   18,   30,    0,    0,    0,   34,   18,    0,
   35,
};
final static short yydgoto[] = {                          1,
    2,    5,    6,    7,   12,   23,   25,   13,   14,   15,
   76,   77,   40,   41,   42,  118,   43,   26,   44,   45,
   46,   47,   19,   22,  110,   59,  109,
};
final static short yysindex[] = {                         0,
    0, -200,    0,    0,    0,    0,    0, -265, -278, -200,
    0, -256, -263, -284,    0,    0,    0, -200, -253,    0,
    0,    0, -200, -216,    0,  165, -208, -260, -254,    0,
    0, -170, -260, -164, -260, -260,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -36,    0, -267,    0,
 -158, -260, -260, -260,  -23, -260,    8,  108,    0, -260,
 -260, -260, -260, -260, -260, -260, -260, -260, -260, -260,
 -260, -260,    0, -260,    0, -202, -205,  304,   65,  134,
    0,  160,    0,    0, -200,   -6,   37,   79,   79,  -65,
  -65,  -65,  -65, -215, -215,    0,    0,    0,   95,    0,
 -260, -138,    0,    0,  183,    0,  304, -158, -159,  273,
    0,    0,    0,    0,  201, -152, -155,    0,    0,  219,
    0,
};
final static short yyrindex[] = {                         0,
    0,  120,    0,    0,    0,    0,    0,    0,    0, -153,
    0,    0,    0, -125,    0,    0,    0,    0,    0,    0,
    0,    0,  237,    0,    0,    0,    0,    0,   21,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -198,    0,
    0, -123,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -122, -266,    0,    0,
    0,    0,    0,    0,  237,  -89, -201, -210,  -68, -124,
 -116, -108,  -76, -166, -156,    0,    0,    0,    0,    0,
    0,   52,    0,    0,    0,    0, -259,    0,    0,    0,
    0,    0,    0,    0,    0,  255,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,   -2,   69,    0,    0,    0,  144,
    0,    0,    0,    0,    0,    0,    0,  -67,   56,    0,
  -49,  -27,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=574;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          8,
   50,   75,   51,   10,   18,   55,    9,   57,   58,   11,
   28,   49,   30,   31,   52,   16,   43,  105,   74,   17,
   24,   36,   43,   42,   78,   79,   80,   52,   82,   42,
   21,   53,   86,   87,   88,   89,   90,   91,   92,   93,
   94,   95,   96,   97,   98,  115,   99,   54,   54,   54,
   54,  120,   70,   71,   72,   27,   52,   52,  112,   61,
   61,   61,   61,   61,   61,   61,   61,   61,   61,   61,
   61,   61,   54,  107,    3,    4,   54,   54,   54,   48,
  100,   52,   24,  101,   61,   52,   52,   52,   61,   61,
   61,   46,   46,   46,   46,   46,   46,   46,   46,   46,
   46,   47,   47,   47,   47,   47,   47,   47,   47,   47,
   47,   54,   28,   29,   30,   31,   46,   56,  108,    1,
   46,   46,   46,   36,  113,  117,   47,   85,  119,   13,
   47,   47,   47,   56,   56,   56,   56,   56,   56,   56,
   56,   57,   57,   57,   57,   57,   57,   57,   57,   58,
   58,   58,   58,   58,   58,   58,   58,   12,   56,   45,
   44,   20,   56,   56,   56,  114,   57,    0,   51,    0,
   57,   57,   57,    0,   58,    0,    0,    0,   58,   58,
   58,   59,   59,   59,   59,   59,   59,   59,   59,   55,
   55,   55,   55,   51,    0,    0,    0,   51,   51,   51,
   68,   69,   70,   71,   72,    0,   59,    0,    0,    0,
   59,   59,   59,    0,   55,    0,    0,    0,   55,   55,
   55,   60,   61,   62,   63,   64,   65,   66,   67,   68,
   69,   70,   71,   72,   60,   61,   62,   63,   64,   65,
   66,   67,   68,   69,   70,   71,   72,    0,    0,    0,
    0,   73,   61,   62,   63,   64,   65,   66,   67,   68,
   69,   70,   71,   72,   81,   60,   61,   62,   63,   64,
   65,   66,   67,   68,   69,   70,   71,   72,   61,   61,
   61,   61,   61,   61,   61,   61,   61,   61,   61,   61,
   61,    0,    0,    0,    0,   83,   62,   63,   64,   65,
   66,   67,   68,   69,   70,   71,   72,    0,   61,   63,
   63,   63,   63,   63,   63,   63,   63,   63,   63,   63,
   63,   63,   60,   61,   62,   63,   64,   65,   66,   67,
   68,   69,   70,   71,   72,    0,    0,    0,    0,   63,
   64,   65,   66,   67,   68,   69,   70,   71,   72,    0,
    0,  102,   60,   61,   62,   63,   64,   65,   66,   67,
   68,   69,   70,   71,   72,   60,   61,   62,   63,   64,
   65,   66,   67,   68,   69,   70,   71,   72,    0,    0,
    0,  106,    0,    0,    0,    0,    0,    0,    0,    0,
   84,   60,   61,   62,   63,   64,   65,   66,   67,   68,
   69,   70,   71,   72,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  103,   60,   61,   62,
   63,   64,   65,   66,   67,   68,   69,   70,   71,   72,
    0,    0,    0,    0,    0,   28,   29,   30,   31,    0,
    0,   32,  104,   33,   34,   35,   36,    0,   37,   38,
    0,    0,   39,   28,   29,   30,   31,    0,    0,   32,
    0,   33,   34,   35,   36,    0,   37,  111,    0,    0,
   39,   28,   29,   30,   31,    0,    0,   32,    0,   33,
   34,   35,   36,    0,   37,  116,    0,    0,   39,   28,
   29,   30,   31,    0,    0,   32,    0,   33,   34,   35,
   36,    0,   37,  121,    0,    0,   39,   18,   18,   18,
   18,    0,    0,   18,    0,   18,   18,   18,   18,    0,
   18,   18,    0,    0,   18,   36,   36,   36,   36,    0,
    0,   36,    0,   36,   36,   36,   36,    0,   36,   36,
    0,    0,   36,   28,   29,   30,   31,    0,    0,   32,
    0,   33,   34,   35,   36,    0,   37,    0,    0,    0,
   39,   60,   61,   62,   63,   64,   65,   66,   67,   68,
   69,   70,   71,   72,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   28,   51,  257,  282,  289,   33,  272,   35,   36,  288,
  271,  272,  273,  274,  282,  272,  283,   85,  286,  283,
   23,  282,  289,  283,   52,   53,   54,  282,   56,  289,
  284,  286,   60,   61,   62,   63,   64,   65,   66,   67,
   68,   69,   70,   71,   72,  113,   74,  258,  259,  260,
  261,  119,  268,  269,  270,  272,  258,  259,  108,  258,
  259,  260,  261,  262,  263,  264,  265,  266,  267,  268,
  269,  270,  283,  101,  275,  276,  287,  288,  289,  288,
  283,  283,   85,  289,  283,  287,  288,  289,  287,  288,
  289,  258,  259,  260,  261,  262,  263,  264,  265,  266,
  267,  258,  259,  260,  261,  262,  263,  264,  265,  266,
  267,  282,  271,  272,  273,  274,  283,  282,  257,    0,
  287,  288,  289,  282,  284,  278,  283,   59,  284,  283,
  287,  288,  289,  258,  259,  260,  261,  262,  263,  264,
  265,  258,  259,  260,  261,  262,  263,  264,  265,  258,
  259,  260,  261,  262,  263,  264,  265,  283,  283,  283,
  283,   18,  287,  288,  289,  110,  283,   -1,  258,   -1,
  287,  288,  289,   -1,  283,   -1,   -1,   -1,  287,  288,
  289,  258,  259,  260,  261,  262,  263,  264,  265,  258,
  259,  260,  261,  283,   -1,   -1,   -1,  287,  288,  289,
  266,  267,  268,  269,  270,   -1,  283,   -1,   -1,   -1,
  287,  288,  289,   -1,  283,   -1,   -1,   -1,  287,  288,
  289,  258,  259,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  258,  259,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,   -1,   -1,   -1,
   -1,  288,  259,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  288,  258,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  270,  258,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,   -1,   -1,   -1,   -1,  288,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,   -1,  288,  258,
  259,  260,  261,  262,  263,  264,  265,  266,  267,  268,
  269,  270,  258,  259,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,   -1,   -1,   -1,   -1,  288,
  262,  263,  264,  265,  266,  267,  268,  269,  270,   -1,
   -1,  287,  258,  259,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,  258,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  270,   -1,   -1,
   -1,  287,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  283,  258,  259,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  283,  258,  259,  260,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
   -1,   -1,   -1,   -1,   -1,  271,  272,  273,  274,   -1,
   -1,  277,  283,  279,  280,  281,  282,   -1,  284,  285,
   -1,   -1,  288,  271,  272,  273,  274,   -1,   -1,  277,
   -1,  279,  280,  281,  282,   -1,  284,  285,   -1,   -1,
  288,  271,  272,  273,  274,   -1,   -1,  277,   -1,  279,
  280,  281,  282,   -1,  284,  285,   -1,   -1,  288,  271,
  272,  273,  274,   -1,   -1,  277,   -1,  279,  280,  281,
  282,   -1,  284,  285,   -1,   -1,  288,  271,  272,  273,
  274,   -1,   -1,  277,   -1,  279,  280,  281,  282,   -1,
  284,  285,   -1,   -1,  288,  271,  272,  273,  274,   -1,
   -1,  277,   -1,  279,  280,  281,  282,   -1,  284,  285,
   -1,   -1,  288,  271,  272,  273,  274,   -1,   -1,  277,
   -1,  279,  280,  281,  282,   -1,  284,   -1,   -1,   -1,
  288,  258,  259,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=289;
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
null,null,null,"ASSIGN","OR","AND","EQ","NE","LE","LT","GE","GT","ADD","SUB",
"MUL","DIV","MOD","NOT","IDENT","INT_LIT","BOOL_LIT","BOOL","INT","IF","ELSE",
"PRINT","WHILE","RETURN","LCIRCLE","RCIRCLE","LCURLY","RCURLY","LSQUARE",
"RSQUARE","SEMI","COMMA",
};
final static String yyrule[] = {
"$accept : program",
"program : decl_list",
"decl_list : decl_list decl",
"decl_list :",
"decl : var_decl",
"decl : fun_decl",
"var_decl : type_spec IDENT SEMI",
"type_spec : BOOL",
"type_spec : INT",
"$$1 :",
"$$2 :",
"fun_decl : type_spec IDENT LCIRCLE params RCIRCLE $$1 LCURLY $$2 local_decls stmt_list RCURLY",
"params : param_list",
"params :",
"param_list : param_list COMMA param",
"param_list : param",
"param : type_spec IDENT",
"stmt_list : stmt_list stmt",
"stmt_list :",
"stmt : expr_stmt",
"stmt : compound_stmt",
"stmt : if_stmt",
"stmt : while_stmt",
"stmt : return_stmt",
"stmt : print_stmt",
"stmt : SEMI",
"expr_stmt : IDENT ASSIGN expr_stmt",
"expr_stmt : expr SEMI",
"expr_stmt : IDENT LSQUARE expr RSQUARE ASSIGN expr_stmt",
"$$3 :",
"while_stmt : WHILE LCIRCLE expr RCIRCLE $$3 stmt",
"$$4 :",
"compound_stmt : LCURLY $$4 local_decls stmt_list RCURLY",
"$$5 :",
"if_stmt : IF LCIRCLE expr RCIRCLE $$5 LCURLY stmt_list RCURLY else_stmt",
"else_stmt : ELSE LCURLY stmt_list RCURLY",
"else_stmt :",
"print_stmt : PRINT expr SEMI",
"return_stmt : RETURN expr SEMI",
"local_decls : local_decls local_decl",
"local_decls :",
"local_decl : type_spec IDENT SEMI",
"arg_list : arg_list COMMA expr",
"arg_list : expr",
"args : arg_list",
"args :",
"expr : expr ADD expr",
"expr : expr SUB expr",
"expr : expr MUL expr",
"expr : expr DIV expr",
"expr : expr MOD expr",
"expr : expr OR expr",
"expr : expr AND expr",
"expr : NOT expr",
"expr : expr EQ expr",
"expr : expr NE expr",
"expr : expr LE expr",
"expr : expr LT expr",
"expr : expr GE expr",
"expr : expr GT expr",
"expr : LCIRCLE expr RCIRCLE",
"expr : IDENT",
"expr : IDENT LCIRCLE args RCIRCLE",
"expr : IDENT LSQUARE expr RSQUARE",
"expr : BOOL_LIT",
"expr : INT_LIT",
};

//#line 148 "MarieParser.y"


public static MarieLexer lexer;

private int yylex() {
	int yyl_return = -1;
	try {
		yylval = new ParserVal(0);
		yyl_return = lexer.yylex();
	}
	catch (IOException e) {
		System.out.println("IO ERROR!");
	}
	return yyl_return;
}

public void yyerror (String error) {
	System.out.println("ERROR: " + error + ". Line Number: " + lexer.lineno);
}

public Parser(Reader r) {
	lexer = new MarieLexer(r, this);
}


//#line 445 "Parser.java"
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
public int yyparse()
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
//#line 34 "MarieParser.y"
{yyval.obj = program___decllist();}
break;
case 2:
//#line 37 "MarieParser.y"
{yyval.obj = decl_list___decl_list_decl();}
break;
case 3:
//#line 38 "MarieParser.y"
{yyval.obj = decl_list____eps();}
break;
case 4:
//#line 41 "MarieParser.y"
{yyval.obj = decl____var_decl();}
break;
case 5:
//#line 42 "MarieParser.y"
{yyval.obj = decl____fun_decl();}
break;
case 6:
//#line 45 "MarieParser.y"
{yyval.obj = var_decl____type_spec_IDENT_SEMI(val_peek(2).obj, val_peek(1).obj);}
break;
case 7:
//#line 53 "MarieParser.y"
{yyval.obj = type_spec____BOOL();}
break;
case 8:
//#line 54 "MarieParser.y"
{yyval.obj = type_spec____INT();}
break;
case 9:
//#line 57 "MarieParser.y"
{yyval.obj = fun_decl____type_spec_IDENT_LCIRCLE_params_RCIRCLE(val_peek(4).obj, val_peek(3).obj, val_peek(1).obj);}
break;
case 10:
//#line 58 "MarieParser.y"
{fun_decl____LCURLY(val_peek(3).obj);}
break;
case 11:
//#line 59 "MarieParser.y"
{fun_decl____RCURLY(val_peek(9).obj);}
break;
case 12:
//#line 62 "MarieParser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 13:
//#line 63 "MarieParser.y"
{yyval.obj = params____eps();}
break;
case 14:
//#line 66 "MarieParser.y"
{yyval.obj = param_list____param_list_COMMA_param(val_peek(2).obj, val_peek(0).obj);}
break;
case 15:
//#line 67 "MarieParser.y"
{yyval.obj = param_list____param(val_peek(0).obj);}
break;
case 16:
//#line 70 "MarieParser.y"
{yyval.obj = param____type_spec_IDENT(val_peek(1).obj, val_peek(0).obj);}
break;
case 17:
//#line 73 "MarieParser.y"
{yyval.obj = stmt_list____stmt_list_stmt(); }
break;
case 18:
//#line 74 "MarieParser.y"
{yyval.obj = stmt_list____eps();}
break;
case 19:
//#line 77 "MarieParser.y"
{yyval.obj = stmt____expr_stmt();}
break;
case 23:
//#line 81 "MarieParser.y"
{yyval.obj = stmt____return_stmt();}
break;
case 26:
//#line 86 "MarieParser.y"
{yyval.obj = expr_stmt____IDENT_ASSIGN_expr_stmt(val_peek(2).obj, val_peek(0).obj);}
break;
case 27:
//#line 87 "MarieParser.y"
{yyval.obj = expr_stmt____expr(val_peek(1).obj);}
break;
case 28:
//#line 88 "MarieParser.y"
{yyval.obj = expr_stmt____IDENT_arr_ASSIGN_expr_stmt(val_peek(5).obj, val_peek(3).obj, val_peek(0).obj);}
break;
case 29:
//#line 91 "MarieParser.y"
{yyval.obj = while_stmt____WHILE_LCIRCLE_expr_RCIRCLE(val_peek(1).obj);}
break;
case 31:
//#line 94 "MarieParser.y"
{compound_stmt___LBRACE();}
break;
case 32:
//#line 96 "MarieParser.y"
{compound_stmt___RBRACE();}
break;
case 33:
//#line 98 "MarieParser.y"
{yyval.obj = if_stmt____LCIRCLE_expr(val_peek(1).obj);}
break;
case 37:
//#line 105 "MarieParser.y"
{yyval.obj = val_peek(2).obj;}
break;
case 38:
//#line 108 "MarieParser.y"
{yyval.obj = return_stmt____RETURN_expr_SEMI(val_peek(1).obj);}
break;
case 40:
//#line 112 "MarieParser.y"
{yyval.obj = local_decls____eps();}
break;
case 41:
//#line 115 "MarieParser.y"
{yyval.obj = local_decl____type_spec_IDENT_SEMI(val_peek(2).obj, val_peek(1).obj);}
break;
case 42:
//#line 118 "MarieParser.y"
{yyval.obj = arg_list____arg_list_COMMA_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 43:
//#line 119 "MarieParser.y"
{yyval.obj = arg_list____expr(val_peek(0).obj);}
break;
case 44:
//#line 122 "MarieParser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 45:
//#line 123 "MarieParser.y"
{yyval.obj = args____eps();}
break;
case 46:
//#line 126 "MarieParser.y"
{yyval.obj = expr____expr_ADD_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 47:
//#line 127 "MarieParser.y"
{yyval.obj = expr____expr_SUB_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 48:
//#line 128 "MarieParser.y"
{yyval.obj = expr____expr_MUL_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 49:
//#line 129 "MarieParser.y"
{yyval.obj = expr____expr_DIV_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 50:
//#line 130 "MarieParser.y"
{yyval.obj = expr____expr_MOD_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 51:
//#line 131 "MarieParser.y"
{yyval.obj = expr____expr_OR_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 52:
//#line 132 "MarieParser.y"
{yyval.obj = expr____expr_AND_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 53:
//#line 133 "MarieParser.y"
{yyval.obj = expr____NOT_expr(val_peek(0).obj);}
break;
case 54:
//#line 134 "MarieParser.y"
{yyval.obj = expr____expr_EQ_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 55:
//#line 135 "MarieParser.y"
{yyval.obj = expr____expr_NE_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 56:
//#line 136 "MarieParser.y"
{yyval.obj = expr____expr_LE_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 57:
//#line 137 "MarieParser.y"
{yyval.obj = expr____expr_LT_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 58:
//#line 138 "MarieParser.y"
{yyval.obj = expr____expr_GE_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 59:
//#line 139 "MarieParser.y"
{yyval.obj = expr____expr_GT_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 60:
//#line 140 "MarieParser.y"
{yyval.obj = val_peek(1).obj;}
break;
case 61:
//#line 141 "MarieParser.y"
{yyval.obj = expr____IDENT(val_peek(0).obj);}
break;
case 62:
//#line 142 "MarieParser.y"
{yyval.obj = expr____IDENT_LCIRCLE_args_RCIRCLE(val_peek(3).obj, val_peek(1).obj);}
break;
case 63:
//#line 143 "MarieParser.y"
{yyval.obj = expr____IDENT_LSQUARE_expr_RSQUARE(val_peek(3).obj, val_peek(1).obj);}
break;
case 64:
//#line 144 "MarieParser.y"
{yyval.obj = expr____BOOL_LIT(val_peek(0).obj);}
break;
case 65:
//#line 145 "MarieParser.y"
{yyval.obj = expr____INT_LIT(val_peek(0).obj);}
break;
//#line 814 "Parser.java"
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
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
