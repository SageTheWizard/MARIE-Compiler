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
import java.io.*;
import java.util.*;
//#line 20 "Parser.java"




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
public final static short NEW=279;
public final static short PRINT=280;
public final static short WHILE=281;
public final static short RETURN=282;
public final static short LCIRCLE=283;
public final static short RCIRCLE=284;
public final static short LCURLY=285;
public final static short RCURLY=286;
public final static short LSQUARE=287;
public final static short RSQUARE=288;
public final static short SEMI=289;
public final static short COMMA=290;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    5,    5,    5,   23,
   24,    4,    8,    8,    9,    9,   10,   18,   18,   19,
   19,   19,   19,   19,   19,   19,   21,   21,   21,   25,
   13,   26,   14,   27,   15,   16,   16,   17,   20,    6,
    6,    7,   12,   12,   11,   11,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    1,    3,    1,    1,    3,    0,
    0,   11,    1,    0,    3,    1,    2,    2,    0,    1,
    1,    1,    1,    1,    1,    1,    3,    2,    6,    0,
    6,    0,    5,    0,    6,    2,    0,    3,    3,    2,
    0,    3,    3,    1,    1,    0,    3,    3,    3,    3,
    3,    3,    2,    3,    3,    3,    3,    3,    3,    3,
    1,    4,    4,    5,    1,    1,
};
final static short yydefred[] = {                         3,
    0,    0,    7,    8,    2,    4,    5,    0,    0,    0,
    0,    6,    9,    0,    0,    0,   16,   17,   10,    0,
    0,   15,   11,   41,    0,    0,   40,    0,    0,    0,
    0,   66,   65,    0,    0,    0,    0,    0,    0,   32,
   12,   26,   23,   21,   22,   25,   18,   24,   20,    0,
   42,    0,   53,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   41,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   28,    0,   27,    0,    0,
    0,    0,    0,    0,   38,    0,   39,   60,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   49,   50,
   51,    0,   62,    0,    0,    0,    0,   30,    0,   63,
    0,    0,    0,   64,    0,   33,   29,    0,   35,   31,
   36,
};
final static short yydgoto[] = {                          1,
    2,    5,    6,    7,   14,   25,   27,   15,   16,   17,
   79,   80,   43,   44,   45,  119,   46,   28,   47,   48,
   49,   50,   21,   24,  115,   63,  106,
};
final static short yysindex[] = {                         0,
    0, -141,    0,    0,    0,    0,    0, -269, -278, -282,
 -141,    0,    0, -255, -276, -241,    0,    0,    0, -141,
 -229,    0,    0,    0, -141, -215,    0,  100, -231,  -80,
 -253,    0,    0, -210, -141,  -80, -197,  -80,  -80,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -50,
    0, -233,    0,  207,  -80,  -80,  -80, -203,  -37,  -80,
  -20,   69,    0,  -80,  -80,  -80,  -80,  -80,  -80,  -80,
  -80,  -80,  -80,  -80,  -80,    0,  -80,    0, -196, -182,
  206,   23,  206, -259,    0,   94,    0,    0, -141,  231,
   81,   81,   99,   99,   99,   99, -167, -167,    0,    0,
    0,   40,    0,  -80, -164, -165,   54,    0,  119,    0,
  206,  207, -157,    0,  176,    0,    0,  176,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,  126,    0,    0,    0,    0,    0,    0,    0,    0,
 -148,    0,    0,    0,    0, -143,    0,    0,    0,    0,
    0,    0,    0,    0,  138,    0,    0,    0,    0,    0,
   -7,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -199,    0,    0, -136,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -135,
 -268,    0, -130,    0,    0,    0,    0,    0,  138, -237,
 -108,  -83, -166, -133, -118, -100, -184, -151,    0,    0,
    0,    0,    0,    0,   10,    0,    0,    0,    0,    0,
 -265,    0,  157,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,   -2,   96,    0,    0,    0,  147,
    0,    0,    0,    0,    0,    0,    0,   79,    2,    0,
  -52,  -29,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=501;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          8,
   53,   78,    9,   54,   11,   13,   59,   19,   61,   62,
   12,   30,   52,   32,   33,   44,   18,   10,   43,   35,
   52,   44,   26,   39,   43,   81,   82,   83,   13,   55,
   86,   10,   58,   56,   90,   91,   92,   93,   94,   95,
   96,   97,   98,   99,  100,  101,   52,  102,   20,   55,
   52,   52,   52,   77,  107,   23,   29,   51,   61,  117,
   61,   61,   61,   61,   61,   61,   61,   61,   61,   61,
   61,   10,   57,   47,  111,   47,   47,   47,   47,   47,
   47,   47,   47,   84,   61,   60,   26,  103,   61,   61,
   61,   56,  112,   56,   56,   56,   56,   56,   56,   47,
   73,   74,   75,   47,   47,   47,   48,  104,   48,   48,
   48,   48,   48,   48,   48,   48,  120,   56,  113,  121,
  118,   56,   56,   56,   57,    1,   57,   57,   57,   57,
   57,   57,   48,    3,    4,   14,   48,   48,   48,   58,
   13,   58,   58,   58,   58,   58,   58,   46,   45,   54,
   57,   54,   54,   34,   57,   57,   57,   59,   89,   59,
   59,   59,   59,   59,   59,   58,   22,  109,    0,   58,
   58,   58,    0,    0,   55,   54,   55,   55,    0,   54,
   54,   54,    0,   59,    0,    0,    0,   59,   59,   59,
   30,   52,   32,   33,    0,    0,    0,    0,   35,    0,
   55,    0,   39,    0,   55,   55,   55,   64,    0,   65,
   66,   67,   68,   69,   70,   71,   72,   73,   74,   75,
   64,    0,   65,   66,   67,   68,   69,   70,   71,   72,
   73,   74,   75,    0,    0,    0,    0,   64,   76,   65,
   66,   67,   68,   69,   70,   71,   72,   73,   74,   75,
   61,   85,   61,   61,   61,   61,   61,   61,   61,   61,
   61,   61,   61,    0,    0,    0,    0,   63,   87,   63,
   63,   63,   63,   63,   63,   63,   63,   63,   63,   63,
   64,   61,   65,   66,   67,   68,   69,   70,   71,   72,
   73,   74,   75,    0,    0,    0,    0,   64,   63,   65,
   66,   67,   68,   69,   70,   71,   72,   73,   74,   75,
  105,   64,    0,   65,   66,   67,   68,   69,   70,   71,
   72,   73,   74,   75,    0,    0,   64,  110,   65,   66,
   67,   68,   69,   70,   71,   72,   73,   74,   75,    0,
    0,  114,   67,   68,   69,   70,   71,   72,   73,   74,
   75,   64,   88,   65,   66,   67,   68,   69,   70,   71,
   72,   73,   74,   75,   71,   72,   73,   74,   75,    0,
   30,   31,   32,   33,    0,    0,   34,  108,   35,   36,
   37,   38,   39,    0,   40,   41,    0,    0,   42,   30,
   31,   32,   33,    0,    0,   34,    0,   35,   36,   37,
   38,   39,    0,   40,  116,    0,    0,   42,   19,   19,
   19,   19,    0,    0,   19,    0,   19,   19,   19,   19,
   19,    0,   19,   19,    0,    0,   19,   37,   37,   37,
   37,    0,    0,   37,    0,   37,   37,   37,   37,   37,
    0,   37,   37,    0,    0,   37,   30,   31,   32,   33,
    0,    0,   34,    0,   35,   36,   37,   38,   39,    0,
   40,    0,    0,   64,   42,   65,   66,   67,   68,   69,
   70,   71,   72,   73,   74,   75,    0,   30,   31,   32,
   33,    0,    0,    0,    0,   35,    0,    0,    0,   39,
   65,   66,   67,   68,   69,   70,   71,   72,   73,   74,
   75,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   30,   54,  272,  257,  283,  288,   36,  284,   38,   39,
  289,  271,  272,  273,  274,  284,  272,  287,  284,  279,
  258,  290,   25,  283,  290,   55,   56,   57,  288,  283,
   60,  287,   35,  287,   64,   65,   66,   67,   68,   69,
   70,   71,   72,   73,   74,   75,  284,   77,  290,  283,
  288,  289,  290,  287,   84,  285,  272,  289,  258,  112,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  287,  283,  258,  104,  260,  261,  262,  263,  264,
  265,  266,  267,  287,  284,  283,   89,  284,  288,  289,
  290,  258,  257,  260,  261,  262,  263,  264,  265,  284,
  268,  269,  270,  288,  289,  290,  258,  290,  260,  261,
  262,  263,  264,  265,  266,  267,  115,  284,  284,  118,
  278,  288,  289,  290,  258,    0,  260,  261,  262,  263,
  264,  265,  284,  275,  276,  284,  288,  289,  290,  258,
  284,  260,  261,  262,  263,  264,  265,  284,  284,  258,
  284,  260,  261,  284,  288,  289,  290,  258,   63,  260,
  261,  262,  263,  264,  265,  284,   20,   89,   -1,  288,
  289,  290,   -1,   -1,  258,  284,  260,  261,   -1,  288,
  289,  290,   -1,  284,   -1,   -1,   -1,  288,  289,  290,
  271,  272,  273,  274,   -1,   -1,   -1,   -1,  279,   -1,
  284,   -1,  283,   -1,  288,  289,  290,  258,   -1,  260,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  258,   -1,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,   -1,   -1,   -1,   -1,  258,  289,  260,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  258,  289,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,   -1,   -1,   -1,   -1,  258,  289,  260,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  258,  289,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,   -1,   -1,   -1,   -1,  258,  289,  260,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  288,  258,   -1,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,  258,  288,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,   -1,
   -1,  288,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  258,  284,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  266,  267,  268,  269,  270,   -1,
  271,  272,  273,  274,   -1,   -1,  277,  284,  279,  280,
  281,  282,  283,   -1,  285,  286,   -1,   -1,  289,  271,
  272,  273,  274,   -1,   -1,  277,   -1,  279,  280,  281,
  282,  283,   -1,  285,  286,   -1,   -1,  289,  271,  272,
  273,  274,   -1,   -1,  277,   -1,  279,  280,  281,  282,
  283,   -1,  285,  286,   -1,   -1,  289,  271,  272,  273,
  274,   -1,   -1,  277,   -1,  279,  280,  281,  282,  283,
   -1,  285,  286,   -1,   -1,  289,  271,  272,  273,  274,
   -1,   -1,  277,   -1,  279,  280,  281,  282,  283,   -1,
  285,   -1,   -1,  258,  289,  260,  261,  262,  263,  264,
  265,  266,  267,  268,  269,  270,   -1,  271,  272,  273,
  274,   -1,   -1,   -1,   -1,  279,   -1,   -1,   -1,  283,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,
};
}
final static short YYFINAL=1;
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
null,null,null,"ASSIGN","OR","AND","EQ","NE","LE","LT","GE","GT","ADD","SUB",
"MUL","DIV","MOD","NOT","IDENT","INT_LIT","BOOL_LIT","BOOL","INT","IF","ELSE",
"NEW","PRINT","WHILE","RETURN","LCIRCLE","RCIRCLE","LCURLY","RCURLY","LSQUARE",
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
"type_spec : type_spec LSQUARE RSQUARE",
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
"if_stmt : IF LCIRCLE expr $$5 RCIRCLE else_stmt",
"else_stmt : ELSE stmt",
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
"expr : NEW type_spec LSQUARE expr RSQUARE",
"expr : BOOL_LIT",
"expr : INT_LIT",
};

//#line 143 "MarieParser.y"


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
	System.out.println("ERROR: " + error);
}

public MarieParser(Reader r) {
	lexer = new MarieLexer(r, this);
}


//#line 432 "Parser.java"
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
throws SemanticException
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
//#line 33 "MarieParser.y"
{yyval.obj = program___decllist();}
break;
case 2:
//#line 36 "MarieParser.y"
{yyval.obj = decl_list___decl_list_decl();}
break;
case 3:
//#line 37 "MarieParser.y"
{yyval.obj = decl_list____eps();}
break;
case 4:
//#line 40 "MarieParser.y"
{yyval.obj = decl____var_decl();}
break;
case 5:
//#line 41 "MarieParser.y"
{yyval.obj = decl____fun_decl();}
break;
case 6:
//#line 44 "MarieParser.y"
{yyval.obj = var_decl____type_spec_IDENT_SEMI(val_peek(2).obj, val_peek(1).obj);}
break;
case 7:
//#line 47 "MarieParser.y"
{yyval.obj = type_spec____BOOL();}
break;
case 8:
//#line 48 "MarieParser.y"
{yyval.obj = type_spec____INT();}
break;
case 9:
//#line 49 "MarieParser.y"
{yyval.obj = type_spec____type_spec_LSQUARE_RSQUARE(val_peek(2).obj);}
break;
case 10:
//#line 52 "MarieParser.y"
{yyval.obj = fun_decl____type_spec_IDENT_LCIRCLE_params_RCIRCLE(val_peek(4).obj, val_peek(3).obj, val_peek(1).obj);}
break;
case 11:
//#line 53 "MarieParser.y"
{fun_decl____LCURLY(val_peek(3).obj);}
break;
case 12:
//#line 54 "MarieParser.y"
{fun_decl____RCURLY(val_peek(9).obj);}
break;
case 13:
//#line 57 "MarieParser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 14:
//#line 58 "MarieParser.y"
{yyval.obj = params____eps();}
break;
case 15:
//#line 61 "MarieParser.y"
{yyval.obj = param_list____param_list_COMMA_param(val_peek(2).obj, val_peek(0).obj);}
break;
case 16:
//#line 62 "MarieParser.y"
{yyval.obj = param_list____param(val_peek(0).obj);}
break;
case 17:
//#line 65 "MarieParser.y"
{yyval.obj = param____type_spec_IDENT(val_peek(1).obj, val_peek(0).obj);}
break;
case 18:
//#line 68 "MarieParser.y"
{yyval.obj = stmt_list____stmt_list_stmt(); }
break;
case 19:
//#line 69 "MarieParser.y"
{yyval.obj = stmt_list____eps();}
break;
case 20:
//#line 72 "MarieParser.y"
{yyval.obj = stmt____expr_stmt();}
break;
case 24:
//#line 76 "MarieParser.y"
{yyval.obj = stmt____return_stmt();}
break;
case 27:
//#line 81 "MarieParser.y"
{yyval.obj = expr_stmt____IDENT_ASSIGN_expr_stmt(val_peek(2).obj, val_peek(0).obj);}
break;
case 28:
//#line 82 "MarieParser.y"
{yyval.obj = expr_stmt____expr(val_peek(1).obj);}
break;
case 29:
//#line 83 "MarieParser.y"
{yyval.obj = expr_stmt____IDENT_arr_ASSIGN_expr_stmt(val_peek(5).obj, val_peek(3).obj, val_peek(0).obj);}
break;
case 30:
//#line 86 "MarieParser.y"
{yyval.obj = while_stmt____WHILE_LCIRCLE_expr_RCIRCLE(val_peek(1).obj);}
break;
case 32:
//#line 89 "MarieParser.y"
{compound_stmt___LBRACE();}
break;
case 33:
//#line 91 "MarieParser.y"
{compound_stmt___RBRACE();}
break;
case 34:
//#line 93 "MarieParser.y"
{yyval.obj = if_stmt____LCIRCLE_expr(val_peek(0).obj);}
break;
case 39:
//#line 103 "MarieParser.y"
{yyval.obj = return_stmt____RETURN_expr_SEMI(val_peek(1).obj);}
break;
case 41:
//#line 107 "MarieParser.y"
{yyval.obj = local_decls____eps();}
break;
case 42:
//#line 110 "MarieParser.y"
{yyval.obj = local_decl____type_spec_IDENT_SEMI(val_peek(2).obj, val_peek(1).obj);}
break;
case 43:
//#line 113 "MarieParser.y"
{yyval.obj = arg_list____arg_list_COMMA_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 44:
//#line 114 "MarieParser.y"
{yyval.obj = arg_list____expr(val_peek(0).obj);}
break;
case 45:
//#line 117 "MarieParser.y"
{yyval.obj = val_peek(0).obj}
break;
case 46:
//#line 118 "MarieParser.y"
{yyval.obj = args____eps();}
break;
case 47:
//#line 121 "MarieParser.y"
{yyval.obj = expr____expr_ADD_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 48:
//#line 122 "MarieParser.y"
{yyval.obj = expr____expr_SUB_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 49:
//#line 123 "MarieParser.y"
{yyval.obj = expr____expr_MUL_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 50:
//#line 124 "MarieParser.y"
{yyval.obj = expr____expr_DIV_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 51:
//#line 125 "MarieParser.y"
{yyval.obj = expr____expr_MOD_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 52:
//#line 126 "MarieParser.y"
{yyval.obj = expr____expr_OR_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 53:
//#line 127 "MarieParser.y"
{yyval.obj = expr____NOT_expr(val_peek(0).obj);}
break;
case 54:
//#line 128 "MarieParser.y"
{yyval.obj = expr____expr_EQ_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 55:
//#line 129 "MarieParser.y"
{yyval.obj = expr____expr_NE_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 56:
//#line 130 "MarieParser.y"
{yyval.obj = expr____expr_LE_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 57:
//#line 131 "MarieParser.y"
{yyval.obj = expr____expr_LT_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 58:
//#line 132 "MarieParser.y"
{yyval.obj = expr____expr_GE_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 59:
//#line 133 "MarieParser.y"
{yyval.obj = expr____expr_GT_expr(val_peek(2).obj, val_peek(0).obj);}
break;
case 60:
//#line 134 "MarieParser.y"
{yyval.obj = val_peek(1).obj;}
break;
case 61:
//#line 135 "MarieParser.y"
{yyval.obj = expr____IDENT(val_peek(0).obj);}
break;
case 62:
//#line 136 "MarieParser.y"
{yyval.obj = expr____IDENT_LCIRCLE_args_RCIRCLE(val_peek(3).obj, val_peek(1).obj);}
break;
case 63:
//#line 137 "MarieParser.y"
{yyval.obj = expr____IDENT_LSQUARE_expr_RSQUARE(val_peek(3).obj, val_peek(1).obj);}
break;
case 64:
//#line 138 "MarieParser.y"
{yyval.obj = expr____NEW_type_spec_LSQUARE_expr_RSQUARE(val_peek(3).obj, val_peek(1).obj);}
break;
case 65:
//#line 139 "MarieParser.y"
{yyval.obj = expr____BOOL_LIT(val_peek(0).obj);}
break;
case 66:
//#line 140 "MarieParser.y"
{yyval.obj = expr____INT_LIT(val_peek(0).obj);}
break;
//#line 801 "Parser.java"
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
