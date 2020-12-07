package MARIE.Compiler;

import java.util.ArrayList;

public class ICGNode {
    private StringBuffer code;
    private ICGNode parent;
    private ArrayList<ICGNode> children;
    private boolean isRoot;

    // Only call for root
    public ICGNode(){
        this.isRoot = true;
        this.code = new StringBuffer("");
        this.parent = null;
        this.children = new ArrayList<>();
    }
    // Call for others
    public ICGNode(ICGNode parent) {
        this.isRoot = false;
        this.code = new StringBuffer("");
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    // Is Leaf dunno if I'll use this but .. might be useful
    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    // Generate Code: Collect children's code in order
    public void generateCode() {
        for (ICGNode child : this.children) {
            this.code.append(child.getCode());
        }
    }

    // Set a child
    public void setChild(Object child) {
        this.children.add((ICGNode) child);
    }

    // Set bulk children
    public void setBulkChildren(ArrayList<ICGNode> children) {
        this.children.addAll(children);
    }

    // Get Code: Returns the code stored in the node
    public String getCode() {
        return this.code.toString();
    }

    // Load a literal
    public void loadLiteral(String lit) {
        if (lit.equals("true")) {
            this.code.append("load ").append(1).append("\n");
        }
        else if (lit.equals("false")) {
            this.code.append("load ").append(-1).append("\n");
        }
        else {
            this.code.append("load ").append(lit);
        }
    }

    // Load an if
    public void loadIf(int ifCtr) {
        this.generateCode();
        this.children.remove(0);
        this.code.append("skipcond 800").append("\n")
                .append("jump :endIfStmt").append(ifCtr).append("\n");
    }

    // Load end if
    public void loadEndIf(int ifCtr) {
        this.generateCode();
        this.code.append("jump :endElse").append(ifCtr).append("\n");
        this.code.append(":endIfStmt").append(ifCtr).append("\n");
        this.code.append(this.children.get(this.children.size() - 1).getCode());
        this.code.append(":endElse").append(ifCtr).append("\n");
    }

    //<editor-fold desc="****Integer Ops****">
    // Add Operator
    public void add() {
        this.code.append(this.children.get(0).getCode());
        this.code.append("store :TEMP\n")
                .append(this.children.get(1).getCode())
                .append("add :TEMP\n");
    }

    // Sub Operator
    public void sub() {
        this.code.append(this.children.get(1).getCode())
                .append("store :TEMP\n")
                .append(this.children.get(0).getCode())
                .append("subt :TEMP\n");
    }

    // Mul Operator
    public void mul(Instructions inst) {
        this.code.append(this.children.get(0).getCode())
                .append("store :TEMP\n")
                .append(this.children.get(1).getCode())
                .append("store :TEMP2\n")
                .append(inst.pseudoInstructions(Parser.MUL, "TEMP", "TEMP2"))
                .append("\n");
    }

    // Div Operator
    public void div(Instructions inst) {
        this.code.append(this.children.get(1).getCode())
                .append("store :TEMP\n")
                .append(this.children.get(0).getCode())
                .append("store :TEMP2\n")
                .append(inst.pseudoInstructions(Parser.DIV, "TEMP", "TEMP2"))
                .append("\n");
    }

    // Mod Operator
    public void mod(Instructions inst) {
        this.code.append(this.children.get(0).getCode())
                .append("store :TEMP\n")
                .append(this.children.get(1).getCode())
                .append("store :TEMP2\n")
                .append(inst.pseudoInstructions(Parser.MOD, "TEMP", "TEMP2"))
                .append("\n");
    }
    //</editor-fold>

    //<editor-fold desc="****Bool Ops****">
    // Or Operator
    public void or(int ctr) {
        this.code.append(this.children.get(0).getCode())
                .append("store TEMP\n")
                .append(this.children.get(1).getCode())
                .append("store TEMP2\n")
                .append("load TEMP\n add TEMP2\n")
                .append("skipcond 000\n")
                .append("jump :true").append(ctr).append('\n')
                .append("load -1\n")
                .append("jump :endBool").append(ctr).append('\n')
                .append("true").append(ctr).append('\n')
                .append("load 1").append('\n')
                .append(":endBool").append(ctr).append('\n');
    }

    // And Operator
    public void and(int ctr) {
        this.code.append(this.children.get(0).getCode())
                .append("store TEMP\n")
                .append(this.children.get(1).getCode())
                .append("store TEMP2\n")
                .append("load TEMP\n add TEMP2\n")
                .append("skipcond 800\n")
                .append("jump :true").append(ctr).append('\n')
                .append("load -1\n")
                .append("jump :endBool").append(ctr).append('\n')
                .append(":true").append(ctr).append('\n')
                .append("load 1").append('\n')
                .append(":endBool").append(ctr).append('\n');
    }

    // Not Operator
    public void not(Instructions inst) {
        this.code.append(this.children.get(0).getCode())
                .append("negate\n");
    }
    //</editor-fold>

    //<editor-fold desc="****Equality Ops****">
    // All Equality Ops in one function (since the code will be almost exactly the same)
    public void equalityOps(int op, int ctr) {
        this.code.append(this.children.get(0).getCode())
                .append("store TEMP\n")
                .append(this.children.get(1).getCode())
                .append("store TEMP2\n load TEMP\n sub TEMP2\n");
        switch(op) {
            case Parser.EQ: // 1 - 1 = 0 ---> True | 1 - 2 != 0 --> False
                this.code.append("skipcond 400\n")
                        .append("jump :false").append(ctr).append('\n')
                        .append("load 1\n")
                        .append("jump :endBool").append(ctr).append('\n')
                        .append(":false").append(ctr).append('\n')
                        .append("load -1\n");
                break;
            case Parser.NE: // 1 - 1 = 0 ---> False | 1 - 2 != 0 ---> True
                this.code.append("skipcond 400\n")
                        .append("jump :true").append(ctr).append('\n')
                        .append("load -1\n")
                        .append("jump :endBool").append(ctr).append('\n')
                        .append("true").append(ctr).append('\n')
                        .append("load 1\n");
                break;
            case Parser.LE: // 1 - 2 > 0 ----> False | 2 - 1 > 0 ----> True
                this.code.append("skipcond 800\n")
                        .append("jump :true").append(ctr).append('\n')
                        .append("load -1\n")
                        .append("jump :endBool").append(ctr).append('\n')
                        .append(":true").append(ctr).append('\n')
                        .append("load 1\n");
                break;
            case Parser.LT:
                this.code.append("skipcond 000\n")
                        .append("jump :true").append(ctr).append('\n')
                        .append("load -1\n")
                        .append("jump :endBool").append(ctr).append('\n')
                        .append(":true").append(ctr).append('\n')
                        .append("load 1\n");
                break;
            case Parser.GE:
                this.code.append("skipcond 000\n")
                        .append("jump :false").append(ctr).append('\n')
                        .append("load 1\n")
                        .append("jump :endBool").append(ctr).append('\n')
                        .append(":false").append(ctr).append('\n')
                        .append("load -1\n");
                break;
            case Parser.GT:
                this.code.append("skipcond 800\n")
                        .append("jump :false").append(ctr).append('\n')
                        .append("load 1\n")
                        .append("jump :endBool").append(ctr).append('\n')
                        .append(":false").append(ctr).append('\n')
                        .append("load -1\n");
                break;
        }
    }
    //</editor-fold>

    // local decls: All are init'd to 1 (ie. ints = 1, bools = true by default)
    //              This will minic java's array default inits of arrays
    public void localDecl(String var, Instructions inst) {
        this.code.append("stack increment 1\n")
                .append("load 1")
                .append("stack push 0");
        inst.addLocalVar(var);
    }

    // load var
    public void loadId(String var) {
        this.code.append("load ").append(var).append("\n");
    }

    // Print current thing register
    public void printRegister() {this.code.append("output\n");}

    // Assignment Code
    public void assign(String id, Instructions inst) {
        for (ICGNode child : this.children) {
            this.code.append(child.getCode());
        }

        this.code.append("stack push ")
                .append(inst.getCurrentStackLocation() - inst.getStackLocationOfID(id)).append('\n');
    }

    // Set the label for the while loops
    public void beginWhile(int ctr) {
        // :TOP_WHILE0
        this.code.append(":TOP_WHILE").append(ctr).append('\n');
    }

    // Generate code for body and end of the while loop
    public void endWhile(int ctr) {
        // First Child will be the condition
        this.code.append(this.children.get(0).getCode());
        // AC should be holding the result of the condition
        this.code.append("skipcond 000\n")
                .append("jump :END_WHILE").append(ctr).append('\n');
        // There shouldn't be more than 1 child but just in case
        for (int i = 1; i < this.children.size(); i++) {
            this.code.append(this.children.get(i).getCode());
        }
        this.code.append("jump :TOP_WHILE").append(ctr)
                .append(":END_WHILE").append(ctr).append('\n');
    }

    // Code For Functions
    public void callFunction(String id, ArrayList<ICGNode> args, Instructions inst) {
        int argCounter = 0;
        for (ICGNode arg : args) {
            this.code.append(arg.getCode())
                    .append("stack increment 1\n")
                    .append("stack push 0\n");
            inst.addLocalVar(inst.getFunctionParamIDs(id, argCounter));
            argCounter++;
        }
        this.code.append("jumpnpush :").append(id).append('\n');
    }

    public void funcDecl(String id) {
        this.code.append(":").append(id).append('\n');
    }

    public void endFunction() {
        for (ICGNode child : this.children) {
            this.code.append(child.getCode());
        }
    }

    public void returnFunction() {
        this.code.append("jump return\n");
    }

    public void storeIndex(Instructions inst, String arrId) {
        inst.addLocalVar("INDEX_" + arrId);
        this.code.append("stack increment 1\nstack push 0\n");
    }

    public void assignIndex(Instructions inst, String arrId) {
        int indexStackLocation =
                inst.getCurrentStackLocation() - inst.getStackLocationOfID("INDEX_" + arrId);
        int arrayStart = inst.getStackLocationOfID(arrId);

    }

    public void setGlobalVars(Instructions inst) {
        this.code.append(inst.getGlobalVarCode());
    }

}
