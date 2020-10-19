package MARIE.Compiler;

import java.io.UncheckedIOException;
import java.util.ArrayList;

public class ParserImpl {
    Scope scope = new Scope(null);
    String currentFunction = "";
    String currentFunctionReturnType = "";
    boolean foundReturn = false;
    public static boolean foundMain = false;

    public Object program___decllist() throws SemanticError {
        if (!foundMain) {
            throw new SemanticError("Program must have a main function titled \"main\" " +
                    "which takes no arguments.");
        }
        else return null;
    }

    public Object decl_list___decl_list_decl() throws SemanticError {
        return null;
    }

    public Object decl_list____eps() throws SemanticError {
        return null;
    }

    public Object decl____var_decl() throws SemanticError {
        return null;
    }

    public Object decl____fun_decl() throws SemanticError {
        return null;
    }

    public Object var_decl____type_spec_IDENT_SEMI(Object type, Object ident) throws SemanticError {
        if (!scope.put((String) ident, (String) type)){
            throw new SemanticError("Variable " + (String) ident + " is already defined. (Line: " +
                    Parser.lexer.lineno + ")");
        }
        return null;
    }

    public Object type_spec____BOOL() {
        return "boolean";
    }

    public Object type_spec____INT() {
        return "integer";
    }

    public Object type_spec____type_spec_LSQUARE_RSQUARE(Object type) {
        return "array " + type;
    }

    public Object fun_decl____type_spec_IDENT_LCIRCLE_params_RCIRCLE(Object retType, Object name, Object params) throws SemanticError {
        String id = (String) name;
        String ret = (String) retType;
        if (!(params instanceof ArrayList)) {
            throw new SemanticError("Jacob Broke Something!");
        }
        ArrayList<Object> paramsTypes = (ArrayList<Object>) params;

        StringBuilder funcInfo = new StringBuilder();
        boolean validPut;

        funcInfo.append(ret).append("-");
        if (paramsTypes.size() == 0) {
            funcInfo.append("NONE");
            validPut = scope.put(id, funcInfo.toString());
        }
        else {
            for (int i = 0; i < paramsTypes.size(); i++) {
                funcInfo.append(((String) paramsTypes.get(i)).split("-")[0]);
                if (i != paramsTypes.size() - 1) {
                    funcInfo.append("-");
                }
            }
            validPut = scope.put(id, funcInfo.toString());
        }

        if (!validPut) {
            throw new SemanticError("Variable " + id + " is already defined. (Line: " +
                    Parser.lexer.lineno + ")");
        }
        if (id.equals("main")) {
            if (!ret.equals("integer")) {
                throw new SemanticError("Main function must return an integer. (Line: " +
                        Parser.lexer.lineno + ")");
            }
            foundMain = true;
        }

        currentFunction = id;
        currentFunctionReturnType = ret;

        return null;
    }

    public void fun_decl____LCURLY(Object params) throws SemanticError{
        if (!(params instanceof ArrayList)) {
            throw new SemanticError("Jacob Broke Something!");
        }
        ArrayList<Object> paramInfo = (ArrayList<Object>) params;

        scope = new Scope(scope);

        for (Object param : paramInfo) {
            String temp = (String) param;
            String[] tokens = temp.split("-");

            if (!scope.put(tokens[1], tokens[0])){
                throw new SemanticError("Variable " + tokens[1] + " is already defined. (Line: " +
                        Parser.lexer.lineno + ")");
            }
        }
        foundReturn = false;
    }

    public void fun_decl____RCURLY(Object funcName) throws SemanticError{
        String id = (String) funcName;

        if (!foundReturn) {
            throw new SemanticError("Function " + funcName + " must return a " + currentFunctionReturnType +
                    " (Line Number: " + Parser.lexer.lineno + ")");
        }
        scope = scope.prev;
        foundReturn = false;
        currentFunction = "";
        currentFunctionReturnType = "";
    }

    public Object params____eps() {
        return new ArrayList<Object>();
    }

    public Object param_list____param_list_COMMA_param(Object pList, Object p) {
        ArrayList<Object> paramList = (ArrayList<Object>) pList;
        paramList.add(p);
        return paramList;
    }

    public Object param_list____param(Object p) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(p);
        return paramList;
    }

    public Object param____type_spec_IDENT(Object type, Object name) {
        String t = (String) type;
        String n = (String) name;

        return t + "-" + n;
    }

    public Object stmt_list____stmt_list_stmt() {
        return null;
    }

    public Object stmt_list____eps() {
        return null;
    }

    public Object stmt____expr_stmt() {
        return null;
    }

    public Object stmt____return_stmt() {
        return null;
    }

    public Object expr_stmt____IDENT_ASSIGN_expr_stmt(Object name, Object assignType) throws SemanticError {
        String expectedType = scope.get((String) name);

        if (expectedType == null) {
            throw new SemanticError("Error: Variable " + name + " is not declared. " +
                    "(Line Number: " + Parser.lexer.lineno + ")");
        }

        if (!expectedType.equals((String) assignType)) {
            throw new SemanticError("Error: Variable " + name + " is of " + expectedType.toUpperCase() +
                    "Type. Given: " + ((String) assignType).toUpperCase() + ". Line Number: (" +
                    Parser.lexer.lineno + ")");
        }

        return null;
    }

    public Object expr_stmt____expr(Object type) {
        return type;
    }

    public Object expr_stmt____IDENT_arr_ASSIGN_expr_stmt(Object name, Object indx, Object assignType) throws SemanticError {
        String indxType = (String) indx;
        String arrName = (String) name;
        String assign = (String) assignType;

        if (scope.get(arrName) == null || scope.get(arrName).contains("array")){
            throw new SemanticError("Error: Variable " + arrName + " is not an array, therefore, " +
                    "cannot be indexed.  (Line Number: " + Parser.lexer.lineno + ").");
        }
        else if (!scope.get(arrName).split(" ")[1].equals(assign)) {
            throw new SemanticError("Error: Array " + arrName + " is of type " +
                    scope.get("array " + arrName) + " not of type " + assign + ". " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }
        else if (!indxType.equals("integer")) {
            throw new SemanticError("Error: Arrays cannot be indexed by type " + indxType + "." +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return assign;
    }

    public Object while_stmt____WHILE_LCIRCLE_expr_RCIRCLE(Object loopCondType) throws SemanticError {
        String loopCond = (String) loopCondType;

        if (!loopCond.equals("boolean")) {
            throw new SemanticError("Error: While loop condition must be boolean not " +
                    loopCond + " type.  (Line Number: " + Parser.lexer.lineno + ").");
        }

        return null;
    }

    public void compound_stmt___LBRACE() {
        scope = new Scope(scope);
    }

    public void compound_stmt___RBRACE() {
        scope = scope.prev;
    }

    public Object if_stmt____LCIRCLE_expr(Object ifCond) throws SemanticError {
        String cond = (String) ifCond;

        if (!cond.equals("boolean")) {
            throw new SemanticError("Error: If statement cannot be conditioned on a " + ifCond +
                    ".  Must by of type boolean. (Line Number: " + Parser.lexer.lineno + ").");
        }

        return null;
    }

    public Object return_stmt____RETURN_expr_SEMI(Object returnType) throws SemanticError {
        String retType = (String) returnType;
        foundReturn = true;

        if (!retType.equals(currentFunctionReturnType)) {
            throw new SemanticError("Error: Function " + currentFunction + " has return type of " +
                    currentFunctionReturnType + " not " + retType + ". (Line Number: " +
                    Parser.lexer.lineno + ")");
        }

        return null;
    }

    public Object local_decls____eps() {
        return null;
    }

    public Object local_decl____type_spec_IDENT_SEMI(Object varType, Object var) throws SemanticError {
        String id = (String) var;
        String type = (String) varType;
        boolean validPut = scope.put(id, type);

        if (!validPut) {
            throw new SemanticError("Variable " + id + " is already defined within the scope.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }
        return null;
    }

    public Object args____eps() {
        return new ArrayList<Object>();
    }

    public Object arg_list____arg_list_COMMA_expr(Object argList, Object arg) {
        ArrayList<Object> args = (ArrayList<Object>) argList;
        args.add(arg);
        return args;
    }

    public Object arg_list____expr(Object arg) {
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(arg);
        return args;
    }

    public Object expr____expr_ADD_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("integer") || !type2.equals("integer"))) {
            throw new SemanticError("Error: Expression " + type1 + " + " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "integer";
    }
    public Object expr____expr_SUB_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("integer") || !type2.equals("integer"))) {
            throw new SemanticError("Error: Expression " + type1 + " - " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "integer";
    }

    public Object expr____expr_MUL_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("integer") || !type2.equals("integer"))) {
            throw new SemanticError("Error: Expression " + type1 + " * " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "integer";
    }

    public Object expr____expr_DIV_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("integer") || !type2.equals("integer"))) {
            throw new SemanticError("Error: Expression " + type1 + " / " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "integer";
    }

    public Object expr____expr_MOD_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("integer") || !type2.equals("integer"))) {
            throw new SemanticError("Error: Expression " + type1 + " % " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "integer";
    }

    public Object expr____expr_OR_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("boolean") || !type2.equals("boolean"))) {
            throw new SemanticError("Error: Expression " + type1 + " || " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "boolean";
    }

    public Object expr____NOT_expr(Object op) throws SemanticError {
        String type = (String) op;

        if (!type.equals("boolean")) {
            throw new SemanticError("Error: Expression ! " + type + " is not allowed.  " +
                    "Operator must be of type boolean.  (Line Number: " +
                    Parser.lexer.lineno + ").");
        }

        return "boolean";
    }

    public Object expr____expr_EQ_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("boolean") || !type2.equals("boolean"))) {
            throw new SemanticError("Error: Expression " + type1 + " == " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "boolean";
    }

    public Object expr____expr_NE_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("boolean") || !type2.equals("boolean"))) {
            throw new SemanticError("Error: Expression " + type1 + " != " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "boolean";
    }

    public Object expr____expr_LE_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("integer") || !type2.equals("integer"))) {
            throw new SemanticError("Error: Expression " + type1 + " <= " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "boolean";
    }

    public Object expr____expr_LT_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("integer") || !type2.equals("integer"))) {
            throw new SemanticError("Error: Expression " + type1 + " < " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "boolean";
    }
    public Object expr____expr_GE_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("integer") || !type2.equals("integer"))) {
            throw new SemanticError("Error: Expression " + type1 + " >= " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "boolean";
    }

    public Object expr____expr_GT_expr(Object op1, Object op2) throws SemanticError {
        String type1 = (String) op1;
        String type2 = (String) op2;

        if (!type1.equals(type2) || (!type1.equals("integer") || !type2.equals("integer"))) {
            throw new SemanticError("Error: Expression " + type1 + " > " + type2 +
                    " is not allowed.  Operands must be of integer type.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return "boolean";
    }

    public Object expr____IDENT(Object name) throws SemanticError {
        String id = (String) name;
        String type = scope.get(id);

        if (type == null) {
            throw new SemanticError("Undeclared variable " + id + ". (Line Number: " +
                    Parser.lexer.lineno + ").");
        }
        else if (type.contains("-")) {
            throw new SemanticError("Error: " + id + " is a function and must be called. " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return type;
    }

    public Object expr____IDENT_LCIRCLE_args_RCIRCLE(Object name, Object argList) throws SemanticError {
        String id = (String) name;
        ArrayList<Object> args = (ArrayList<Object>) argList;
        String funcInfo = scope.get(id);

        if (funcInfo == null) {
            throw new SemanticError("Error: Function " + id + " is not defined. " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        String[] tokens = funcInfo.split("-");
        String returnType = tokens[0];

        for (int i = 1; i < tokens.length; i++) {
            if (!tokens[i].equals((String) args.get(i - 1))) {
                throw new SemanticError("Error: Argument #" + i + " of function " + id +
                        " must be a " + tokens[i] + " not a " + args.get(i - 1) + ". " +
                        "(Line Number: " + Parser.lexer.lineno + ")");
            }
        }
        return returnType;
    }

    public Object expr____IDENT_LSQUARE_expr_RSQUARE(Object name, Object indxType) throws SemanticError {
        String id = (String) name;
        String indx = (String) indxType;

        if (!indx.equals("integer")) {
            throw new SemanticError("Error: Arrays must be indexed with an Integer and not a " + indx +
                    ".  (Line Number: " + Parser.lexer.lineno + ").");
        }
        else if (scope.get(id) == null) {
            throw new SemanticError("Error: Variable " + id + " is not defined.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }
        else if (!scope.get(id).contains("array")) {
            throw new SemanticError("Error: Variable " + id + " is not an array and cannot be indexed.  " +
                    "(Line number: " + Parser.lexer.lineno + ").");
        }

        return scope.get(id).split(" ")[1];
    }

    public Object expr____NEW_type_spec_LSQUARE_expr_RSQUARE(Object arrType, Object length) throws SemanticError {
        String type = (String) arrType;
        String lenType = (String) length;

        if (!lenType.equals("integer")) {
            throw new SemanticError("Error: Cannot declare the length of an array with an " + lenType + ".  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }
        else if (!(type.equals("integer") || type.equals("boolean"))) {
            throw new SemanticError("Error: Array types can only be integer or boolean.  " +
                    "(Line Number: " + Parser.lexer.lineno + ").");
        }

        return type;
    }

    public Object expr____BOOL_LIT(Object idunnoimessedup) {
        return "boolean";
    }

    public Object expr____INT_LIT(Object idunnoimessedup) {
        return "integer";
    }
}

class SemanticError extends Exception {
    public SemanticError(){
        super();
    }
    public SemanticError(String err) {
        super(err);
    }
}
