%{
package MARIE.Compiler;
import java.io.*;
import java.util.*;
%}

%right ASSIGN
%left OR
%left AND
%left EQ NE
%left LE LT GE GT
%left ADD SUB
%left MUL DIV MOD
%right NOT

%token <obj> ASSIGN
%token <obj> EQ NE LE LT GE GT
%token <obj> ADD SUB MUL DIV MOD
%token <obj> OR AND NOT

%token <obj> IDENT INT_LIT BOOL_LIT

%token <obj> BOOL INT
%token <obj> IF ELSE PRINT WHILE RETURN
%token <obj> LCIRCLE RCIRCLE LCURLY RCURLY LSQUARE RSQUARE SEMI COMMA

%type <obj> program decl_list decl
%type <obj> var_decl fun_decl type_spec local_decls local_decl
%type <obj> params param_list param args arg_list
%type <obj> while_stmt compound_stmt if_stmt else_stmt print_stmt stmt_list stmt return_stmt expr_stmt
%type <obj> expr
%%

program   : decl_list                   {$$ = program___decllist();}
	  ;

decl_list : decl_list decl              {$$ = decl_list___decl_list_decl();}
	  |                             {$$ = decl_list____eps();}
	  ;

decl	  : var_decl                    {$$ = decl____var_decl();}
	  | fun_decl                    {$$ = decl____fun_decl();}
	  ;

var_decl  : type_spec IDENT SEMI        {$$ = var_decl____type_spec_IDENT_SEMI($1, $2);}
          ;
/*
type_spec_global : BOOL                        {$$ = type_spec____BOOL();}
	         | INT                         {$$ = type_spec____INT();}
	         | type_spec LSQUARE INT_LIT RSQUARE   {$$ = type_spec____type_spec_LSQUARE_RSQUARE($1);}
	         ;
*/
type_spec : BOOL                        {$$ = type_spec____BOOL();}
	  | INT                         {$$ = type_spec____INT();}
	  ;

fun_decl  : type_spec IDENT LCIRCLE params RCIRCLE {$$ = fun_decl____type_spec_IDENT_LCIRCLE_params_RCIRCLE($1, $2, $4);}
		LCURLY {fun_decl____LCURLY($4);}
		local_decls stmt_list RCURLY {fun_decl____RCURLY($2);}
	  ;

params    : param_list                  {$$ = $1;}
	  |                             {$$ = params____eps();}
	  ;

param_list : param_list COMMA param     {$$ = param_list____param_list_COMMA_param($1, $3);}
	   | param                      {$$ = param_list____param($1);}
	   ;

param      : type_spec IDENT            {$$ = param____type_spec_IDENT($1, $2);}
	   ;

stmt_list : stmt_list stmt              {$$ = stmt_list____stmt_list_stmt(); }
	  |                             {$$ = stmt_list____eps();}
	  ;

stmt      : expr_stmt                   {$$ = stmt____expr_stmt();}
	  | compound_stmt
	  | if_stmt
	  | while_stmt
	  | return_stmt                 {$$ = stmt____return_stmt();}
	  | print_stmt
	  | SEMI
	  ;

expr_stmt : IDENT ASSIGN expr_stmt      {$$ = expr_stmt____IDENT_ASSIGN_expr_stmt($1, $3);}
	  | expr SEMI                   {$$ = expr_stmt____expr($1);}
	  | IDENT LSQUARE expr RSQUARE ASSIGN expr_stmt {$$ = expr_stmt____IDENT_arr_ASSIGN_expr_stmt($1, $3, $6);}
	  ;

while_stmt : WHILE LCIRCLE expr RCIRCLE {$$ = while_stmt____WHILE_LCIRCLE_expr_RCIRCLE($3);} stmt
	   ;

compound_stmt : LCURLY {compound_stmt___LBRACE();}
	        local_decls stmt_list
	        RCURLY {compound_stmt___RBRACE();}

if_stmt : IF LCIRCLE expr RCIRCLE {$$ = if_stmt____LCIRCLE_expr($3);} LCURLY stmt_list RCURLY else_stmt
	;

else_stmt : ELSE LCURLY stmt_list RCURLY
          |
          ;

print_stmt : PRINT expr SEMI {$$ = $1;}
	   ;

return_stmt : RETURN expr SEMI {$$ = return_stmt____RETURN_expr_SEMI($2);}
	    ;

local_decls : local_decls local_decl
	    |                  {$$ = local_decls____eps();}
	    ;

local_decl  : type_spec IDENT SEMI {$$ = local_decl____type_spec_IDENT_SEMI($1, $2);}
	    ;

arg_list    : arg_list COMMA expr {$$ = arg_list____arg_list_COMMA_expr($1, $3);}
	    | expr                {$$ = arg_list____expr($1);}
	    ;

args	    : arg_list {$$ = $1;}
	    | 	       {$$ = args____eps();}
	    ;

expr        : expr ADD expr                        {$$ = expr____expr_ADD_expr($1, $3);}
            | expr SUB expr                        {$$ = expr____expr_SUB_expr($1, $3);}
            | expr MUL expr                        {$$ = expr____expr_MUL_expr($1, $3);}
            | expr DIV expr                        {$$ = expr____expr_DIV_expr($1, $3);}
            | expr MOD expr                        {$$ = expr____expr_MOD_expr($1, $3);}
	    | expr OR expr                         {$$ = expr____expr_OR_expr($1, $3);}
	    | expr AND expr                        {$$ = expr____expr_AND_expr($1, $3);}
	    | NOT expr                             {$$ = expr____NOT_expr($2);}
	    | expr EQ expr                         {$$ = expr____expr_EQ_expr($1, $3);}
	    | expr NE expr                         {$$ = expr____expr_NE_expr($1, $3);}
	    | expr LE expr                         {$$ = expr____expr_LE_expr($1, $3);}
	    | expr LT expr                         {$$ = expr____expr_LT_expr($1, $3);}
	    | expr GE expr                         {$$ = expr____expr_GE_expr($1, $3);}
	    | expr GT expr                         {$$ = expr____expr_GT_expr($1, $3);}
	    | LCIRCLE expr RCIRCLE                 {$$ = $2;}
	    | IDENT                                {$$ = expr____IDENT($1);}
	    | IDENT LCIRCLE args RCIRCLE           {$$ = expr____IDENT_LCIRCLE_args_RCIRCLE($1, $3);}
	    | IDENT LSQUARE expr RSQUARE           {$$ = expr____IDENT_LSQUARE_expr_RSQUARE($1, $3);}
	    | BOOL_LIT                             {$$ = expr____BOOL_LIT($1);}
	    | INT_LIT                              {$$ = expr____INT_LIT($1);}
	    ;
%%


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


