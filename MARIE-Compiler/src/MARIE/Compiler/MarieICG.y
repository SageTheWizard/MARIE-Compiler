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
//%type <int> type_spec_global
%type <obj> var_decl fun_decl type_spec local_decls local_decl
%type <obj> params param_list param args arg_list
%type <obj> while_stmt compound_stmt if_stmt else_stmt print_stmt stmt_list stmt return_stmt expr_stmt
%type <obj> expr

%%

program   : decl_list
		{tree.setBulkChildren((ArrayList<ICGNode>) $1);
		 tree.orgAndMain();
		 tree.generateCode();
		 tree.setGlobalVars(inst);
		}
	  ;

decl_list : decl_list decl              {((ArrayList<ICGNode>) $1).add((ICGNode) $2); $$ = $1;}
	  |                             {ArrayList<ICGNode> declsList = new ArrayList<ICGNode>(); $$ = declsList;}
	  ;

decl	  : var_decl                    { $$ = $1;}
	  | fun_decl                    { $$ = $1;}
	  ;

var_decl  : type_spec IDENT {$2 = lexer.lastIdent;} SEMI
	    {inst.addGlobalVar((String) $2, (Integer) $1); $$ = new ICGNode();}
          ;
/*
type_spec_global : BOOL                        {$$ = 1;}
	         | INT                         {$$ = 1;}
	         |
	         ;
*/
type_spec : BOOL {$$ = 1;}
          | INT  {$$ = 1;}
          | type_spec LSQUARE {int size = Integer.parseInt(lexer.yytext()); $$ = size;} INT_LIT RSQUARE
          ;

fun_decl  : type_spec IDENT {$2 = lexer.lastIdent;} LCIRCLE params RCIRCLE
		{
			ICGNode funcDeclNode = new ICGNode();
			inst.setCurrentFunction((String) $2);
			inst.putFunctionParamIDs((String) $2, (ArrayList<String>) $5);
			funcDeclNode.funcDecl((String) $2);
			$2 = funcDeclNode;
		}
		LCURLY
		local_decls stmt_list RCURLY
		{
			ICGNode funcDeclNode = (ICGNode) $2;
			funcDeclNode.setBulkChildren((ArrayList<ICGNode>) $9);
			funcDeclNode.setBulkChildren((ArrayList<ICGNode>) $10);
			funcDeclNode.endFunction();
			$$ = funcDeclNode;
		}
	  ;

params    : param_list                  {$$ = $1;}
	  |                             {$$ = new ArrayList<String>();}
	  ;

param_list : param_list COMMA param
		{ArrayList<String> pList = ((ArrayList<String>) $1); pList.add((String) $3); $$ = pList;}
	   | param
	   	{ArrayList<String> params = new ArrayList<>(); params.add((String) $1); $$ = params;}
	   ;

param      : type_spec IDENT {String id = lexer.lastIdent; $$ = id;}
	   ;

stmt_list : stmt_list stmt
		{
			ArrayList<ICGNode> stmts = (ArrayList<ICGNode>) $1;
			stmts.add((ICGNode) $2);
			$$ = stmts;
		}
	  |                             {$$ = new ArrayList<ICGNode>(); }
	  ;

stmt      : expr_stmt
		{ICGNode exprNode = new ICGNode(); exprNode.setChild($1); exprNode.generateCode(); $$ = exprNode;}
	  | compound_stmt
	  	{$$ = $1;}
	  | if_stmt
	  	{ICGNode ifParent = new ICGNode(); ifParent.setChild($1); ifParent.generateCode(); $$ = ifParent;}
	  | while_stmt
	  	{ICGNode whileParent = new ICGNode(); whileParent.setChild($1); whileParent.generateCode(); $$ = whileParent;}
	  | return_stmt
	  	{ICGNode returnParent = new ICGNode(); returnParent.setChild($1); returnParent.generateCode(); $$ = returnParent; }
	  | print_stmt
	  	{ICGNode printParent = new ICGNode(); printParent.setChild($1); printParent.generateCode(); $$ = printParent; }
	  | SEMI
	  ;

expr_stmt : IDENT {$1 = lexer.lastIdent;} ASSIGN expr_stmt
	    {ICGNode assignNode = new ICGNode(); assignNode.setChild($4); assignNode.assign((String) $1, inst); $$ = assignNode;}
	  | expr SEMI
	    {ICGNode exprNode = new ICGNode(); exprNode.setChild($1); exprNode.generateCode(); $$ = exprNode;}
	  /*
	  | IDENT {String id = lexer.lastIdent;}  LSQUARE expr RSQUARE ASSIGN expr_stmt
	  	{
	  		ICGNode arrayAssign = new ICGNode();
	  		arrayAssign.setChild($4);
	  		arrayAssign.generateCode();
	  		arrayAssign.storeIndex(inst, id);
	  		arrayAssign.setChild($7);
	  		arrayAssign.assignIndex(inst, id);
	  	}
	  */
	  ;

while_stmt : WHILE {ICGNode whileNode = new ICGNode(); whileNode.beginWhile(inst.getWhileCtr()); $1 = whileNode;}
             LCIRCLE expr {ICGNode whileNode = (ICGNode) $1; whileNode.setChild($4); $1 = whileNode;} RCIRCLE stmt
             {ICGNode whileNode = (ICGNode) $1; whileNode.setChild($7); whileNode.endWhile(inst.getWhileCtrNoInc()); $$ = whileNode;}
	   ;

compound_stmt : LCURLY
	        local_decls stmt_list
	        RCURLY
	        {
	        	ICGNode compNode = new ICGNode();
	        	compNode.setBulkChildren((ArrayList<ICGNode>) $2);
	        	compNode.setBulkChildren((ArrayList<ICGNode>) $3);
	        	compNode.generateCode();
	        	$$ = compNode;

	        }

if_stmt : IF LCIRCLE expr {ICGNode ifNode = new ICGNode(); ifNode.setChild($3); ifNode.loadIf(inst.getIfCtr()); $1 = ifNode; } RCIRCLE
	  LCURLY stmt_list RCURLY else_stmt
	  {
	  	ICGNode ifNode = (ICGNode) $1;
	  	ifNode.setBulkChildren((ArrayList<ICGNode>) $7);
	  	ifNode.loadEndIf(inst.getIfCtrNoInc());
	  	ifNode.loadElse((ICGNode) $9, inst.getIfCtrNoInc());
	  	$$ = ifNode;
	  }
	;

else_stmt : ELSE LCURLY stmt_list RCURLY {ICGNode elseNode = new ICGNode();  elseNode.setBulkChildren((ArrayList<ICGNode>) $3); elseNode.generateCode(); $$ = elseNode;}
          | {$$ = new ICGNode();}
          ;

print_stmt : PRINT expr {ICGNode toPrint =  (ICGNode) $1; toPrint.generateCode(); toPrint.printAC(); $$ = toPrint; } SEMI
	   ;

return_stmt : RETURN expr SEMI
		{
			ICGNode returnNode = new ICGNode();
			returnNode.setChild($2);
			returnNode.generateCode();
			returnNode.returnFunction(inst);
			$$ = returnNode;
		}
	    ;

local_decls : local_decls local_decl {((ArrayList<ICGNode>) $1).add((ICGNode) $2); inst.incLocalVarCtr(); $$ = $1;}
	    | {$$ = new ArrayList<ICGNode>();}
	    ;

local_decl  : type_spec IDENT {$2 = lexer.lastIdent;} SEMI
              {ICGNode decl = new ICGNode(); decl.localDecl((String) $2, inst); $$ = decl;}
	    ;

arg_list    : arg_list COMMA expr
		{
			ICGNode finalArg = new ICGNode();
			ArrayList<ICGNode> nodes = new ArrayList<>();
			nodes.add(finalArg);
			$$ = nodes;
		}
	    | expr
	    	{
	    		ICGNode argNode = new ICGNode();
	    		argNode.setChild($1);
	    		ArrayList<ICGNode> argNodes = new ArrayList<>();
	    		argNodes.add(argNode);
	    		$$ = argNodes;
	    	}
	    ;

args	    : arg_list {$$ = $1;}
	    | 	       {$$ = new ArrayList<ICGNode>();}
	    ;

expr        : expr ADD expr {ICGNode addOp = new ICGNode(); addOp.setChild($1); addOp.setChild($3); addOp.add(); $$ = addOp;}
            | expr SUB expr {ICGNode subOp = new ICGNode(); subOp.setChild($1); subOp.setChild($3); subOp.sub(); $$ = subOp;}
            | expr MUL expr {ICGNode mulOp = new ICGNode(); mulOp.setChild($1); mulOp.setChild($3); mulOp.mul(inst); $$ = mulOp;}
            | expr DIV expr {ICGNode divOp = new ICGNode(); divOp.setChild($1); divOp.setChild($3); divOp.div(inst); $$ = divOp;}
            | expr MOD expr {ICGNode modOp = new ICGNode(); modOp.setChild($1); modOp.setChild($3); modOp.mod(inst); $$ = modOp;}
	    | expr OR expr  {ICGNode orOp = new ICGNode(); orOp.setChild($1); orOp.setChild($3); orOp.or(inst.getBoolCtr()); $$ = orOp;}
	    | expr AND expr {ICGNode andOp = new ICGNode(); andOp.setChild($1); andOp.setChild($3); andOp.and(inst.getBoolCtr()); $$ = andOp;}
	    | NOT expr      {ICGNode notOp = new ICGNode(); notOp.setChild($2); notOp.not(inst); $$ = notOp;}
	    | expr EQ expr  {ICGNode eqOp = new ICGNode(); eqOp.setChild($1); eqOp.setChild($3); eqOp.equalityOps(Parser.EQ, inst.getBoolCtr()); $$ = eqOp;}
	    | expr NE expr  {ICGNode neOp = new ICGNode(); neOp.setChild($1); neOp.setChild($3); neOp.equalityOps(Parser.NE, inst.getBoolCtr()); $$ = neOp;}
	    | expr LE expr  {ICGNode leOp = new ICGNode(); leOp.setChild($1); leOp.setChild($3); leOp.equalityOps(Parser.LE, inst.getBoolCtr()); $$ = leOp;}
	    | expr LT expr  {ICGNode ltOp = new ICGNode(); ltOp.setChild($1); ltOp.setChild($3); ltOp.equalityOps(Parser.LT, inst.getBoolCtr()); $$ = ltOp;}
	    | expr GE expr  {ICGNode geOp = new ICGNode(); geOp.setChild($1); geOp.setChild($3); geOp.equalityOps(Parser.GE, inst.getBoolCtr()); $$ = geOp;}
	    | expr GT expr  {ICGNode gtOp = new ICGNode(); gtOp.setChild($1); gtOp.setChild($3); gtOp.equalityOps(Parser.GT, inst.getBoolCtr()); $$ = gtOp;}
	    | LCIRCLE expr RCIRCLE {$$ = $1;}
	    | IDENT {$1 = lexer.lastIdent;} {ICGNode idNode = new ICGNode(); idNode.loadId(inst, (String) $1); $$ = idNode;}
	    | IDENT {$1 = lexer.lastIdent;} LCIRCLE args RCIRCLE
	    	{
	    		ICGNode functionCall = new ICGNode();
	    		ArrayList<ICGNode> args = (ArrayList<ICGNode>) $4;
	    		functionCall.callFunction((String) $1, args, inst);
	    		$$ = functionCall;
	    	}
	    | IDENT {$1 = lexer.lastIdent;} LSQUARE expr RSQUARE
	    | {ICGNode boolLit = new ICGNode(); String lit = inst.addLiteral(lexer.yytext()); boolLit.loadLiteral(":" + lit); $$ = boolLit;} BOOL_LIT
	    | {ICGNode intLit = new ICGNode(); String lit = inst.addLiteral(lexer.yytext()); intLit.loadLiteral(":" + lit); $$ = intLit;}  INT_LIT
	    ;
%%



public static MarieLexerICGN lexer;
public static ICGNode tree;
public static Instructions inst;

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

public ParserICGN(Reader r) {
	lexer = new MarieLexerICGN(r, this);
	tree = new ICGNode();
	inst = new Instructions();
}


