import java.util.*;
import java.util.HashMap;

public class ParserImpl
{
    Env env = new Env(null);

    public static Boolean _debug = false;
    void Debug(String message)
    {
        if(_debug)
            System.out.println(message);
    }

    // this stores list of all functions, which will be used to print parse tree and run the parse tree
    ArrayList<ParseTree.StmtFunc> funcs = null;

    Object program____decllist(Object p1) throws Exception
    {
        ArrayList<ParseTree.StmtFunc> decllist = (ArrayList<ParseTree.StmtFunc>)p1;
        funcs = decllist;
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object decllist____decllist_decl(Object p1, Object p2) throws Exception
    {
        ArrayList<ParseTree.StmtFunc> decllist = (ArrayList<ParseTree.StmtFunc>)p1;
        ParseTree.StmtFunc            decl     = (ParseTree.StmtFunc           )p2;
        decllist.add((ParseTree.StmtFunc)decl);
        return decllist;
    }
    Object decllist____eps() throws Exception
    {
        return new ArrayList<ParseTree.StmtFunc>();
    }
    Object decl____fundecl(Object p1) throws Exception
    {
        return p1;
    }

    Object typespec____BOOL () throws Exception
    {
        return "bool";
    }
    Object typespec____NUM() throws Exception
    {
        return "num";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object fundecl____typespec_IDENT_LPAREN_params_RPAREN_LBRACE_7_localdecls_stmtlist_RBRACE_123456(Object p1, Object p2, Object p4) throws Exception
    {
        return null;
    }
    Object fundecl____typespec_IDENT_LPAREN_params_RPAREN_LBRACE_7_localdecls_stmtlist_RBRACE_890(Object p1, Object p2, Object p4, Object p8, Object p9, Object p10) throws Exception
    {
        var stmt = new ParseTree.StmtFunc();
        stmt.name  = (String) p2;
        stmt.rettype = (String) p1;
        stmt.stmts = (ArrayList<ParseTree.Stmt>) p9;
        return stmt;
    }

    Object params____eps() throws Exception
    {
        return new ArrayList<Object>();
    }

    Object stmtlist____stmtlist_stmt(Object p1, Object p2) throws Exception
    {
        ArrayList<ParseTree.Stmt> stmtlist = (ArrayList<ParseTree.Stmt>)p1;
        ParseTree.Stmt            stmt     = (ParseTree.Stmt           )p2;
        stmtlist.add(stmt);
        return stmtlist;
    }
    Object stmtlist____eps() throws Exception
    {
        return new ArrayList<ParseTree.Stmt>();
    }

    Object stmt____exprstmt_SEMI        (Object p1           ) throws Exception { return p1; }
    Object stmt____returnstmt_SEMI      (Object p1           ) throws Exception { return p1; }
    Object stmt____SEMI                 (                    ) throws Exception { return null; }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object exprstmt____IDENT_ASSIGN_expr(Object p1, Object p2, Object p3) throws Exception
    {
        return new ParseTree.StmtAssign();
    }
    Object localdecls____eps() throws Exception
    {
        return new ArrayList<Object>();
    }
    Object returnstmt____RETURN_expr(Object p2) throws Exception
    {
        var stmt = new ParseTree.StmtReturn();
        stmt.expr = (ParseTree.Expr) p2;
        return stmt;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object args____eps() throws Exception
    {
        return new ArrayList<ParseTree.Expr>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object expr____expr_RELOPEQ_expr(Object p1, Object p2, Object p3) throws Exception
    {
        return new ParseTree.ExprEq(null, null);
    }
    Object expr____expr_OPAND_expr(Object p1, Object p2, Object p3) throws Exception
    {
        return new ParseTree.ExprAnd(null, null);
    }
    Object expr____expr_OPADD_expr(Object p1, Object p2, Object p3) throws Exception
    {
        return new ParseTree.ExprAdd(null, null);
    }
    Object expr____LPAREN_expr_RPAREN(Object p1, Object p2, Object p3) throws Exception
    {
        return new ParseTree.ExprParen(null);
    }
    Object expr____IDENT(Object p1) throws Exception
    {
        return new ParseTree.ExprVar(0);
    }
    Object expr____IDENT_LPAREN_args_RPAREN(Object p1, Object p3) throws Exception
    {
        return new ParseTree.ExprCall(null, null);
    }
    Object expr____BOOLLIT(Object p1) throws Exception
    {
        return new ParseTree.ExprBool(true);
    }
    Object expr____NUMLIT(Object p1) throws Exception
    {
        return new ParseTree.ExprNum(0);
    }
}
