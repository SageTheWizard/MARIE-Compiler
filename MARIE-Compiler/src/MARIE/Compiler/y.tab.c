#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#line 2 "MarieParser.y"
import java.io.*;
import java.util.*;
#line 9 "y.tab.c"
#define ASSIGN 257
#define OR 258
#define AND 259
#define EQ 260
#define NE 261
#define LT 262
#define LTE 263
#define GT 264
#define GTE 265
#define ADD 266
#define MINUS 267
#define MULTI 268
#define DIV 269
#define MOD 270
#define NOT 271
#define IDENT 272
#define INT_LIT 273
#define BOOL_LIT 274
#define BOOL 275
#define INT 276
#define IF 277
#define ELSE 278
#define NEW 279
#define PRINT 280
#define WHILE 281
#define RETURN 282
#define SEMI 283
#define COMMA 284
#define LCIRCLE 285
#define RCIRCLE 286
#define LCURLY 287
#define RCURLY 288
#define LSQUARE 289
#define RSQUARE 290
#define local_decls 291
#define local_decl 292
#define args 293
#define arg_list 294
#define while_stmt 295
#define compound_stmt 296
#define if_stmt 297
#define print_stmt 298
#define stmt_list 299
#define stmt 300
#define return_stmt 301
#define expr_stmt 302
#define expr 303
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    1,    1,    2,    2,    3,    5,    5,    5,    9,
   10,    4,    6,    6,    7,    7,    8,
};
short yylen[] = {                                         2,
    1,    2,    0,    1,    1,    3,    1,    1,    3,    0,
    0,   11,    1,    0,    3,    1,    2,
};
short yydefred[] = {                                      3,
    0,    0,    7,    8,    2,    4,    5,    0,    0,    0,
    6,    0,    9,    0,    0,    0,   16,   17,   10,    0,
    0,   15,   11,    0,    0,    0,   12,
};
short yydgoto[] = {                                       1,
    2,    5,    6,    7,   14,   15,   16,   17,   21,   24,
};
short yysindex[] = {                                      0,
    0, -270,    0,    0,    0,    0,    0, -272, -281, -287,
    0, -270,    0, -271, -279, -276,    0,    0,    0, -270,
 -278,    0,    0, -280, -289, -275,    0,
};
short yyrindex[] = {                                      0,
    0,   12,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -267,    0,    0,    0, -266,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
short yygindex[] = {                                      0,
    0,    0,    0,    0,   13,    0,    0,   -6,    0,    0,
};
#define YYTABLESIZE 20
short yytable[] = {                                       9,
   18,   11,   13,   12,    3,    4,   19,   20,   23,   26,
   25,    1,   27,   22,    8,    0,   10,   10,   14,   13,
};
short yycheck[] = {                                     272,
  272,  283,  290,  285,  275,  276,  286,  284,  287,  299,
  291,    0,  288,   20,    2,   -1,  289,  289,  286,  286,
};
#define YYFINAL 1
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 303
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"ASSIGN","OR","AND","EQ","NE",
"LT","LTE","GT","GTE","ADD","MINUS","MULTI","DIV","MOD","NOT","IDENT","INT_LIT",
"BOOL_LIT","BOOL","INT","IF","ELSE","NEW","PRINT","WHILE","RETURN","SEMI",
"COMMA","LCIRCLE","RCIRCLE","LCURLY","RCURLY","LSQUARE","RSQUARE","local_decls",
"local_decl","args","arg_list","while_stmt","compound_stmt","if_stmt",
"print_stmt","stmt_list","stmt","return_stmt","expr_stmt","expr",
};
char *yyrule[] = {
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
};
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#line 69 "MarieParser.y"

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


#line 187 "y.tab.c"
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
case 1:
#line 34 "MarieParser.y"
{yyval.obj = program___decllist();}
break;
case 2:
#line 37 "MarieParser.y"
{yyval.obj = decl_list___decl_list_decl();}
break;
case 3:
#line 38 "MarieParser.y"
{yyval.obj = decl_list____eps();}
break;
case 4:
#line 41 "MarieParser.y"
{yyval.obj = decl____var_decl();}
break;
case 5:
#line 42 "MarieParser.y"
{yyval.obj = decl____fun_decl();}
break;
case 6:
#line 45 "MarieParser.y"
{yyval.obj = var_decl____type_spec_IDENT_SEMI(yyvsp[-2].obj, yyvsp[-1].obj);}
break;
case 7:
#line 48 "MarieParser.y"
{yyval.obj = type_spec____BOOL();}
break;
case 8:
#line 49 "MarieParser.y"
{yyval.obj = type_spec____INT();}
break;
case 9:
#line 50 "MarieParser.y"
{yyval.obj = type_spec____type_spec_LSQUARE_RSQUARE();}
break;
case 10:
#line 53 "MarieParser.y"
{yyval.obj = fun_decl____type_spec_IDENT_LCIRCLE_params_RCIRCLE(yyvsp[-4].obj, yyvsp[-3].obj, yyvsp[-1].obj);}
break;
case 11:
#line 54 "MarieParser.y"
{fun_decl____LCURLY(yyvsp[-3].obj);}
break;
case 12:
#line 55 "MarieParser.y"
{fun_decl____RCURLY(yyvsp[-9].obj);}
break;
case 13:
#line 58 "MarieParser.y"
{yyval.obj = yyvsp[0].obj;}
break;
case 14:
#line 59 "MarieParser.y"
{yyval.obj = params____eps();}
break;
case 15:
#line 62 "MarieParser.y"
{yyval.obj = param_list____param_list_COMMA_param(yyvsp[-2].obj, yyvsp[0].obj);}
break;
case 16:
#line 63 "MarieParser.y"
{yyval.obj = param_list____param(yyvsp[0].obj);}
break;
case 17:
#line 66 "MarieParser.y"
{yyval.obj = param____type_spec_IDENT(yyvsp[-1].obj, yyvsp[0].obj);}
break;
#line 395 "y.tab.c"
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
