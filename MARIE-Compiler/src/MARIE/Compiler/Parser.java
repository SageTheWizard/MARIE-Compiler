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






//#line 2 "MarieICG.y"
package MARIE.Compiler;
import java.io.*;
import java.util.*;
//#line 21 "Parser.java"




public class Parser
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
    0,    1,    1,    2,    2,   23,    3,    5,    5,   24,
    5,   25,   26,    4,    8,    8,    9,    9,   10,   18,
   18,   19,   19,   19,   19,   19,   19,   19,   27,   21,
   21,   28,   29,   13,   14,   30,   31,   15,   16,   16,
   32,   17,   20,    6,    6,   33,    7,   12,   12,   11,
   11,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   34,   22,   35,   22,
   36,   22,   37,   22,   38,   22,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    1,    0,    4,    1,    1,    0,
    5,    0,    0,   11,    1,    0,    3,    1,    2,    2,
    0,    1,    1,    1,    1,    1,    1,    1,    0,    4,
    2,    0,    0,    7,    4,    0,    0,   10,    4,    0,
    0,    4,    3,    2,    0,    0,    4,    3,    1,    1,
    0,    3,    3,    3,    3,    3,    3,    3,    2,    3,
    3,    3,    3,    3,    3,    3,    0,    2,    0,    5,
    0,    5,    0,    2,    0,    2,
};
final static short yydefred[] = {                         3,
    0,    0,    8,    9,    2,    4,    5,    0,    0,   10,
    0,    0,    0,    7,    0,    0,    0,    0,    0,   18,
   11,   19,   13,    0,    0,   17,   45,    0,    0,   44,
    0,   46,    0,    0,    0,   32,    0,    0,   45,   14,
   28,   25,   23,   24,   27,   20,   26,   22,    0,    0,
    0,    0,    0,    0,   59,    0,   68,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   31,    0,   74,
   76,   47,    0,    0,    0,    0,    0,   43,   66,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   54,   55,   56,    0,   30,    0,    0,    0,    0,   42,
    0,   35,    0,   70,    0,   72,    0,    0,    0,    0,
    0,   34,    0,    0,    0,    0,   38,    0,    0,   39,
};
final static short yydgoto[] = {                          1,
    2,    5,    6,    7,   17,   28,   30,   18,   19,   20,
  106,  107,   42,   43,   44,  127,   45,   31,   46,   47,
   48,   49,   11,   13,   12,   25,   56,   61,  117,   50,
  118,   86,   53,   57,   58,   59,   51,   52,
};
final static short yysindex[] = {                         0,
    0, -207,    0,    0,    0,    0,    0, -262,    0,    0,
 -285, -269, -251,    0, -207, -242, -261, -236, -227,    0,
    0,    0,    0, -207, -188,    0,    0, -207, -226,    0,
   75,    0, -216,    0, -216,    0, -216, -216,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -2, -212,
 -164, -134, -145,    0,    0, -112,    0, -135, -136,  136,
 -127,   11,   55, -207, -216, -216, -216, -216, -216, -216,
 -216, -216, -216, -216, -216, -216, -216,    0, -126,    0,
    0,    0, -173, -216, -216, -131, -216,    0,    0,   90,
   28,  147,  156,  156,  -38,  -38,  -38,  -38, -220, -220,
    0,    0,    0, -216,    0, -130, -104,  136,   42,    0,
  136,    0,  136,    0, -216,    0,  -96,  -95,  136,  105,
  -91,    0,  105,  -90,  -77,  -80,    0,  105,  -82,    0,
};
final static short yyrindex[] = {                         0,
    0,  189,    0,    0,    0,    0,    0,    0, -221,    0,
    0,    0,    0,    0,  -78,    0,    0,    0,  -61,    0,
    0,    0,    0,    0,    0,    0,    0, -265,    0,    0,
 -125,    0, -169, -142, -169,    0, -169, -169,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -175,    0,    0,    0,    0,    0,  -65,
    0,    0,    0, -265, -169, -169, -169, -169, -169, -169,
 -169, -169, -169, -169, -169, -169, -169,    0,    0,    0,
    0,    0, -169, -132, -169,    0, -169,    0,    0, -125,
  -34,  -62, -230,  -41,  -97,  -89,  -81,  -49, -186, -129,
    0,    0,    0, -169,    0,    0,  -50, -219,    0,    0,
  -48,    0,  -47,    0, -169,    0,    0,    0, -183, -125,
    0,    0, -125,    0,   60,    0,    0, -125,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,   -1,  198,    0,    0,    0,  217,
    0,    0,    0,    0,    0,    0,    0,  179, -102,    0,
  161,  -33,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=426;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         55,
    8,   60,   14,   62,   63,   21,   21,   21,   21,    9,
   22,   21,   15,   21,   21,   21,   21,  122,   21,   21,
  124,   16,   21,   10,   10,  129,   29,   60,   60,   60,
   60,   91,   92,   93,   94,   95,   96,   97,   98,   99,
  100,  101,  102,  103,   21,   32,   23,   75,   76,   77,
  108,  109,   60,  111,   33,   54,   60,   60,   60,   10,
   12,   24,   29,   49,   79,   38,    6,    3,    4,   49,
  113,   52,   52,   52,   52,   52,   52,   52,   52,   52,
   52,  119,   67,   67,   67,   67,   67,   67,   67,   67,
   67,   67,   67,   67,   67,   27,   52,   33,   34,   48,
   52,   52,   52,   75,   73,   48,   69,   67,   38,   80,
   71,   67,   67,   67,   29,   67,   67,   67,   67,   67,
   67,   67,   67,   67,   67,   67,   67,   67,   53,   53,
   53,   53,   53,   53,   53,   53,   53,   53,   81,   69,
   75,   73,   82,   71,   83,   67,   84,   75,   73,   85,
   51,   36,  114,   53,   87,  104,  110,   53,   53,   53,
   62,   62,   62,   62,   62,   62,   62,   62,   63,   63,
   63,   63,   63,   63,   63,   63,   64,   64,   64,   64,
   64,   64,   64,   64,  115,   62,  120,  121,    1,   62,
   62,   62,  123,   63,  125,   58,   58,   63,   63,   63,
  126,   64,  130,  128,   16,   64,   64,   64,   65,   65,
   65,   65,   65,   65,   65,   65,   61,   61,   61,   61,
   58,   15,   41,   57,   58,   58,   58,   73,   74,   75,
   76,   77,   50,   65,   33,   37,   64,   65,   65,   65,
   26,   61,   90,  105,    0,   61,   61,   61,   57,    0,
    0,    0,   57,   57,   57,   65,   66,   67,   68,   69,
   70,   71,   72,   73,   74,   75,   76,   77,   65,   66,
   67,   68,   69,   70,   71,   72,   73,   74,   75,   76,
   77,    0,    0,    0,    0,   78,   66,   67,   68,   69,
   70,   71,   72,   73,   74,   75,   76,   77,   88,   65,
   66,   67,   68,   69,   70,   71,   72,   73,   74,   75,
   76,   77,   65,   66,   67,   68,   69,   70,   71,   72,
   73,   74,   75,   76,   77,    0,    0,    0,  116,    0,
   40,   40,   40,   40,    0,    0,   40,   89,   40,   40,
   40,   40,    0,   40,   40,   33,   34,   40,    0,    0,
    0,    0,    0,   35,   36,   37,   38,    0,   39,   40,
   33,   34,   41,    0,    0,    0,    0,    0,   35,   36,
   37,   38,    0,   39,  112,   33,   34,   41,    0,    0,
    0,    0,    0,   35,   36,   37,   38,    0,   39,    0,
    0,    0,   41,   65,   66,   67,   68,   69,   70,   71,
   72,   73,   74,   75,   76,   77,   67,   68,   69,   70,
   71,   72,   73,   74,   75,   76,   77,   69,   70,   71,
   72,   73,   74,   75,   76,   77,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
    2,   35,  288,   37,   38,  271,  272,  273,  274,  272,
  272,  277,  282,  279,  280,  281,  282,  120,  284,  285,
  123,  273,  288,  286,  286,  128,   28,  258,  259,  260,
  261,   65,   66,   67,   68,   69,   70,   71,   72,   73,
   74,   75,   76,   77,  287,  272,  283,  268,  269,  270,
   84,   85,  283,   87,  271,  272,  287,  288,  289,  286,
  282,  289,   64,  283,  277,  282,  288,  275,  276,  289,
  104,  258,  259,  260,  261,  262,  263,  264,  265,  266,
  267,  115,  258,  259,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,  284,  283,  271,  272,  283,
  287,  288,  289,  273,  274,  289,  282,  283,  282,  274,
  286,  287,  288,  289,  257,  258,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  270,  258,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  273,  282,
  273,  274,  288,  286,  257,  288,  282,  273,  274,  286,
  283,  277,  283,  283,  282,  282,  288,  287,  288,  289,
  258,  259,  260,  261,  262,  263,  264,  265,  258,  259,
  260,  261,  262,  263,  264,  265,  258,  259,  260,  261,
  262,  263,  264,  265,  289,  283,  283,  283,    0,  287,
  288,  289,  284,  283,  285,  258,  259,  287,  288,  289,
  278,  283,  285,  284,  283,  287,  288,  289,  258,  259,
  260,  261,  262,  263,  264,  265,  258,  259,  260,  261,
  283,  283,  288,  258,  287,  288,  289,  266,  267,  268,
  269,  270,  283,  283,  283,  283,   39,  287,  288,  289,
   24,  283,   64,   83,   -1,  287,  288,  289,  283,   -1,
   -1,   -1,  287,  288,  289,  258,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  270,  258,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,   -1,   -1,   -1,   -1,  288,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  270,  288,  258,
  259,  260,  261,  262,  263,  264,  265,  266,  267,  268,
  269,  270,  258,  259,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,   -1,   -1,   -1,  287,   -1,
  271,  272,  273,  274,   -1,   -1,  277,  283,  279,  280,
  281,  282,   -1,  284,  285,  271,  272,  288,   -1,   -1,
   -1,   -1,   -1,  279,  280,  281,  282,   -1,  284,  285,
  271,  272,  288,   -1,   -1,   -1,   -1,   -1,  279,  280,
  281,  282,   -1,  284,  285,  271,  272,  288,   -1,   -1,
   -1,   -1,   -1,  279,  280,  281,  282,   -1,  284,   -1,
   -1,   -1,  288,  258,  259,  260,  261,  262,  263,  264,
  265,  266,  267,  268,  269,  270,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  262,  263,  264,
  265,  266,  267,  268,  269,  270,
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
"$$1 :",
"var_decl : type_spec IDENT $$1 SEMI",
"type_spec : BOOL",
"type_spec : INT",
"$$2 :",
"type_spec : type_spec LSQUARE $$2 INT_LIT RSQUARE",
"$$3 :",
"$$4 :",
"fun_decl : type_spec IDENT $$3 LCIRCLE params RCIRCLE $$4 LCURLY local_decls stmt_list RCURLY",
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
"$$5 :",
"expr_stmt : IDENT $$5 ASSIGN expr_stmt",
"expr_stmt : expr SEMI",
"$$6 :",
"$$7 :",
"while_stmt : WHILE $$6 LCIRCLE expr $$7 RCIRCLE stmt",
"compound_stmt : LCURLY local_decls stmt_list RCURLY",
"$$8 :",
"$$9 :",
"if_stmt : $$8 IF LCIRCLE expr $$9 RCIRCLE LCURLY stmt RCURLY else_stmt",
"else_stmt : ELSE LCURLY stmt RCURLY",
"else_stmt :",
"$$10 :",
"print_stmt : PRINT expr $$10 SEMI",
"return_stmt : RETURN expr SEMI",
"local_decls : local_decls local_decl",
"local_decls :",
"$$11 :",
"local_decl : type_spec IDENT $$11 SEMI",
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
"$$12 :",
"expr : IDENT $$12",
"$$13 :",
"expr : IDENT $$13 LCIRCLE args RCIRCLE",
"$$14 :",
"expr : IDENT $$14 LSQUARE expr RSQUARE",
"$$15 :",
"expr : $$15 BOOL_LIT",
"$$16 :",
"expr : $$16 INT_LIT",
};

//#line 235 "MarieICG.y"



public static MarieLexer lexer;
public static ICGNode tree;
public static Instructions inst;
public static ICGNode funcDeclNode;
public static String id;
public static ICGNode ifNode;
public static ICGNode whileNode;

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
	tree = new ICGNode();
	inst = new Instructions();
}


//#line 439 "Parser.java"
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
//#line 36 "MarieICG.y"
{tree.setBulkChildren((ArrayList<ICGNode>) val_peek(0).obj);
		 tree.generateCode();
		 tree.setGlobalVars(inst);
		}
break;
case 2:
//#line 42 "MarieICG.y"
{((ArrayList<ICGNode>) val_peek(1).obj).add((ICGNode) val_peek(0).obj); yyval.obj = val_peek(1).obj;}
break;
case 3:
//#line 43 "MarieICG.y"
{ArrayList<ICGNode> declsList = new ArrayList<ICGNode>(); yyval.obj = declsList;}
break;
case 4:
//#line 46 "MarieICG.y"
{ yyval.obj = val_peek(0).obj;}
break;
case 5:
//#line 47 "MarieICG.y"
{ yyval.obj = val_peek(0).obj;}
break;
case 6:
//#line 50 "MarieICG.y"
{String id = lexer.lastIdent;}
break;
case 7:
//#line 51 "MarieICG.y"
{inst.addGlobalVar(id, (Integer) val_peek(3).obj); yyval.obj = new ICGNode();}
break;
case 8:
//#line 59 "MarieICG.y"
{yyval.obj = 1;}
break;
case 9:
//#line 60 "MarieICG.y"
{yyval.obj = 1;}
break;
case 10:
//#line 61 "MarieICG.y"
{int size = Integer.parseInt(lexer.yytext()); yyval.obj = size;}
break;
case 12:
//#line 64 "MarieICG.y"
{id = lexer.lastIdent;}
break;
case 13:
//#line 65 "MarieICG.y"
{
			funcDeclNode = new ICGNode();
			inst.putFunctionParamIDs(id, (ArrayList<String>) val_peek(1).obj);
			funcDeclNode.funcDecl(id);
		}
break;
case 14:
//#line 72 "MarieICG.y"
{
			funcDeclNode.setBulkChildren((ArrayList<ICGNode>) val_peek(2).obj);
			funcDeclNode.setBulkChildren((ArrayList<ICGNode>) val_peek(1).obj);
			funcDeclNode.endFunction();
			yyval.obj = funcDeclNode;
		}
break;
case 15:
//#line 80 "MarieICG.y"
{yyval.obj = val_peek(0).obj;}
break;
case 16:
//#line 81 "MarieICG.y"
{yyval.obj = new ArrayList<String>();}
break;
case 17:
//#line 85 "MarieICG.y"
{ArrayList<String> pList = ((ArrayList<String>) val_peek(2).obj); pList.add((String) val_peek(0).obj); yyval.obj = pList;}
break;
case 18:
//#line 87 "MarieICG.y"
{ArrayList<String> params = new ArrayList<>(); params.add((String) val_peek(0).obj); yyval.obj = params;}
break;
case 19:
//#line 90 "MarieICG.y"
{String id = lexer.lastIdent; yyval.obj = id;}
break;
case 20:
//#line 94 "MarieICG.y"
{
			ArrayList<ICGNode> stmts = (ArrayList<ICGNode>) val_peek(1).obj;
			stmts.add((ICGNode) val_peek(0).obj);
			yyval.obj = stmts;
		}
break;
case 21:
//#line 99 "MarieICG.y"
{yyval.obj = new ArrayList<ICGNode>(); }
break;
case 22:
//#line 103 "MarieICG.y"
{ICGNode exprNode = new ICGNode(); exprNode.setChild(val_peek(0).obj); exprNode.generateCode(); yyval.obj = exprNode;}
break;
case 23:
//#line 105 "MarieICG.y"
{yyval.obj = val_peek(0).obj;}
break;
case 24:
//#line 107 "MarieICG.y"
{ICGNode ifParent = new ICGNode(); ifParent.setChild(val_peek(0).obj); ifParent.generateCode(); yyval.obj = ifParent;}
break;
case 25:
//#line 109 "MarieICG.y"
{ICGNode whileParent = new ICGNode(); whileParent.setChild(val_peek(0).obj); whileParent.generateCode(); yyval.obj = whileParent;}
break;
case 26:
//#line 111 "MarieICG.y"
{ICGNode returnParent = new ICGNode(); returnParent.setChild(val_peek(0).obj); returnParent.generateCode(); yyval.obj = returnParent; }
break;
case 27:
//#line 113 "MarieICG.y"
{ICGNode printParent = new ICGNode(); printParent.setChild(val_peek(0).obj); printParent.generateCode(); yyval.obj = printParent; }
break;
case 29:
//#line 117 "MarieICG.y"
{id = lexer.lastIdent;}
break;
case 30:
//#line 118 "MarieICG.y"
{ICGNode assignNode = new ICGNode(); assignNode.setChild(val_peek(0).obj); assignNode.assign(id, inst); yyval.obj = assignNode;}
break;
case 31:
//#line 120 "MarieICG.y"
{ICGNode exprNode = new ICGNode(); exprNode.setChild(val_peek(1).obj); exprNode.generateCode(); yyval.obj = exprNode;}
break;
case 32:
//#line 134 "MarieICG.y"
{whileNode = new ICGNode(); whileNode.beginWhile(inst.getWhileCtr());}
break;
case 33:
//#line 135 "MarieICG.y"
{whileNode.setChild(val_peek(0).obj);}
break;
case 34:
//#line 136 "MarieICG.y"
{whileNode.setChild(val_peek(0).obj); whileNode.endWhile(inst.getWhileCtrNoInc()); yyval.obj = whileNode;}
break;
case 35:
//#line 142 "MarieICG.y"
{
	        	ICGNode compNode = new ICGNode();
	        	compNode.setBulkChildren((ArrayList<ICGNode>) val_peek(2).obj);
	        	compNode.setBulkChildren((ArrayList<ICGNode>) val_peek(1).obj);
	        	compNode.generateCode();
	        	yyval.obj = compNode;

	        }
break;
case 36:
//#line 151 "MarieICG.y"
{ifNode = new ICGNode();}
break;
case 37:
//#line 151 "MarieICG.y"
{ifNode.setChild(val_peek(0).obj); ifNode.loadIf(inst.getIfCtr()); }
break;
case 38:
//#line 153 "MarieICG.y"
{
	  	ifNode.setChild(val_peek(2).obj);
	  	ifNode.loadEndIf(inst.getIfCtrNoInc());
	  	ifNode.setChild(val_peek(0).obj);
	  	yyval.obj = ifNode;
	  }
break;
case 39:
//#line 161 "MarieICG.y"
{((ICGNode) val_peek(1).obj).generateCode(); yyval.obj = val_peek(1).obj;}
break;
case 41:
//#line 165 "MarieICG.y"
{ICGNode toPrint =  (ICGNode) val_peek(1).obj; toPrint.generateCode(); yyval.obj = toPrint; }
break;
case 43:
//#line 169 "MarieICG.y"
{
			ICGNode returnNode = new ICGNode();
			returnNode.setChild(val_peek(1).obj);
			returnNode.generateCode();
			returnNode.returnFunction();
			yyval.obj = returnNode;
		}
break;
case 44:
//#line 178 "MarieICG.y"
{((ArrayList<ICGNode>) val_peek(1).obj).add((ICGNode) val_peek(0).obj); yyval.obj = val_peek(1).obj;}
break;
case 45:
//#line 179 "MarieICG.y"
{yyval.obj = new ArrayList<ICGNode>();}
break;
case 46:
//#line 182 "MarieICG.y"
{id = lexer.lastIdent;}
break;
case 47:
//#line 183 "MarieICG.y"
{ICGNode decl = new ICGNode(); decl.localDecl(id, inst); yyval.obj = decl;}
break;
case 48:
//#line 187 "MarieICG.y"
{
			ICGNode finalArg = new ICGNode();
			ArrayList<ICGNode> nodes = new ArrayList<>();
			nodes.add(finalArg);
			yyval.obj = nodes;
		}
break;
case 49:
//#line 194 "MarieICG.y"
{
	    		ICGNode argNode = new ICGNode();
	    		argNode.setChild(val_peek(0).obj);
	    		ArrayList<ICGNode> argNodes = new ArrayList<>();
	    		argNodes.add(argNode);
	    		yyval.obj = argNodes;
	    	}
break;
case 50:
//#line 203 "MarieICG.y"
{yyval.obj = val_peek(0).obj;}
break;
case 51:
//#line 204 "MarieICG.y"
{yyval.obj = new ArrayList<ICGNode>();}
break;
case 52:
//#line 207 "MarieICG.y"
{ICGNode addOp = new ICGNode(); addOp.setChild(val_peek(2).obj); addOp.setChild(val_peek(0).obj); addOp.add(); yyval.obj = addOp;}
break;
case 53:
//#line 208 "MarieICG.y"
{ICGNode subOp = new ICGNode(); subOp.setChild(val_peek(2).obj); subOp.setChild(val_peek(0).obj); subOp.sub(); yyval.obj = subOp;}
break;
case 54:
//#line 209 "MarieICG.y"
{ICGNode mulOp = new ICGNode(); mulOp.setChild(val_peek(2).obj); mulOp.setChild(val_peek(0).obj); mulOp.mul(inst); yyval.obj = mulOp;}
break;
case 55:
//#line 210 "MarieICG.y"
{ICGNode divOp = new ICGNode(); divOp.setChild(val_peek(2).obj); divOp.setChild(val_peek(0).obj); divOp.div(inst); yyval.obj = divOp;}
break;
case 56:
//#line 211 "MarieICG.y"
{ICGNode modOp = new ICGNode(); modOp.setChild(val_peek(2).obj); modOp.setChild(val_peek(0).obj); modOp.mod(inst); yyval.obj = modOp;}
break;
case 57:
//#line 212 "MarieICG.y"
{ICGNode orOp = new ICGNode(); orOp.setChild(val_peek(2).obj); orOp.setChild(val_peek(0).obj); orOp.or(inst.getBoolCtr()); yyval.obj = orOp;}
break;
case 58:
//#line 213 "MarieICG.y"
{ICGNode andOp = new ICGNode(); andOp.setChild(val_peek(2).obj); andOp.setChild(val_peek(0).obj); andOp.and(inst.getBoolCtr()); yyval.obj = andOp;}
break;
case 59:
//#line 214 "MarieICG.y"
{ICGNode notOp = new ICGNode(); notOp.setChild(val_peek(0).obj); notOp.not(inst); yyval.obj = notOp;}
break;
case 60:
//#line 215 "MarieICG.y"
{ICGNode eqOp = new ICGNode(); eqOp.setChild(val_peek(2).obj); eqOp.setChild(val_peek(0).obj); eqOp.equalityOps(Parser.EQ, inst.getBoolCtr()); yyval.obj = eqOp;}
break;
case 61:
//#line 216 "MarieICG.y"
{ICGNode neOp = new ICGNode(); neOp.setChild(val_peek(2).obj); neOp.setChild(val_peek(0).obj); neOp.equalityOps(Parser.NE, inst.getBoolCtr()); yyval.obj = neOp;}
break;
case 62:
//#line 217 "MarieICG.y"
{ICGNode leOp = new ICGNode(); leOp.setChild(val_peek(2).obj); leOp.setChild(val_peek(0).obj); leOp.equalityOps(Parser.LE, inst.getBoolCtr()); yyval.obj = leOp;}
break;
case 63:
//#line 218 "MarieICG.y"
{ICGNode ltOp = new ICGNode(); ltOp.setChild(val_peek(2).obj); ltOp.setChild(val_peek(0).obj); ltOp.equalityOps(Parser.LT, inst.getBoolCtr()); yyval.obj = ltOp;}
break;
case 64:
//#line 219 "MarieICG.y"
{ICGNode geOp = new ICGNode(); geOp.setChild(val_peek(2).obj); geOp.setChild(val_peek(0).obj); geOp.equalityOps(Parser.GE, inst.getBoolCtr()); yyval.obj = geOp;}
break;
case 65:
//#line 220 "MarieICG.y"
{ICGNode gtOp = new ICGNode(); gtOp.setChild(val_peek(2).obj); gtOp.setChild(val_peek(0).obj); gtOp.equalityOps(Parser.GT, inst.getBoolCtr()); yyval.obj = gtOp;}
break;
case 66:
//#line 221 "MarieICG.y"
{yyval.obj = val_peek(2).obj;}
break;
case 67:
//#line 222 "MarieICG.y"
{id = lexer.lastIdent;}
break;
case 68:
//#line 222 "MarieICG.y"
{ICGNode idNode = new ICGNode(); idNode.loadId(id); yyval.obj = idNode;}
break;
case 69:
//#line 223 "MarieICG.y"
{id = lexer.lastIdent;}
break;
case 70:
//#line 224 "MarieICG.y"
{
	    		ICGNode functionCall = new ICGNode();
	    		ArrayList<ICGNode> args = (ArrayList<ICGNode>) val_peek(1).obj;
	    		functionCall.callFunction(id, args, inst);
	    		yyval.obj = functionCall;
	    	}
break;
case 71:
//#line 230 "MarieICG.y"
{id = lexer.lastIdent;}
break;
case 73:
//#line 231 "MarieICG.y"
{ICGNode boolLit = new ICGNode(); boolLit.loadLiteral(lexer.yytext()); yyval.obj = boolLit;}
break;
case 75:
//#line 232 "MarieICG.y"
{ICGNode intLit = new ICGNode(); intLit.loadLiteral(lexer.yytext()); yyval.obj = intLit;}
break;
//#line 914 "Parser.java"
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
