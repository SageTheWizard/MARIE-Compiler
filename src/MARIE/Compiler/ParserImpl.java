package MARIE.Compiler;

public class ParserImpl {
    Scope scope = new Scope(null);
    String currentFunction = "";
    String currentFunctionReturnType = "";
    public static boolean foundMain = false;

    public Object program____decllist() throws SemanticError {
        if (!foundMain) {
            throw new SemanticError("Program must have a main function titled \"main\" " +
                    "which takes no arguments.");
        }
        else return null;
    }

    public Object decl_list____decl_list_decl() throws SemanticError {
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
                    MarieParser.lexer.lineno + ")");
        }
        return null;
    }

    public Object type_spec____BOOL() {
        return "boolean";
    }

    public Object type_spec____INT() {
        return "integer";
    }

    public Object type_spec____LSQUARE_RSQUARE(Object type) {
        return "array " + type;
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
