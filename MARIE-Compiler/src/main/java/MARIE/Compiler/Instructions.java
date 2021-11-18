package MARIE.Compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class Instructions {
    private HashMap<String, Integer> identStackLocation;
    private HashMap<String, Integer> globalMMLocation;
    private HashMap<String, ArrayList<String>> functionVarNames;
    private HashMap<String, String> literalIDs;
    private HashMap<String, Integer> functionLocalCount;
    private int mulLoopCtr;
    private int divLoopCtr;
    private int ifCtr;
    private int boolCtr;
    private int whileCtr;
    private int mainMemLocation;
    private int stackLocation;
    private StringBuffer idents;
    private String currentFunction;

    public Instructions() {
        this.mulLoopCtr = 0;
        this.divLoopCtr = 0;
        this.ifCtr = 0;
        this.boolCtr = 0;
        this.whileCtr = 0;
        this.mainMemLocation = 100;
        this.stackLocation = 1;
        this.idents = new StringBuffer();
        this.identStackLocation = new HashMap<>();
        this.globalMMLocation = new HashMap<>();
        this.literalIDs = new HashMap<>();
        this.functionVarNames = new HashMap<>();
        this.functionLocalCount = new HashMap<>();

        this.literalIDs.put("1", "L_1");
        this.literalIDs.put("-1", "L_N1");
    }
    public String pseudoInstructions(short parserOp, int op1, int op2){
        StringBuffer codeGenerated = new StringBuffer();
        switch(parserOp){
            case ParserICGN.MUL:
                op1 -=  (stackLocation - 1);
                op2 -=  (stackLocation - 1);
                codeGenerated
                        .append("stkpek ").append(op2).append("\n")
                        .append("subt :L_N1\nstkpsh ").append(op2).append("\n")
                        .append("skpgt\n")
                        .append("jump :endMulLoop").append(this.mulLoopCtr).append("\n")
                        .append("stkpek ").append(op1).append("\n")
                        .append("store :TEMP\n")
                        .append(":topMulLoop").append(this.mulLoopCtr).append(" ")
                        .append("stkpek ").append(op1).append('\n')
                        .append("add ").append(" :TEMP").append("\n")
                        .append("stkpsh ").append(op1).append("\n")
                        .append("stkpek ").append(op2).append("\n")
                        .append("subt :L_1\n")
                        .append("stkpsh ").append(op2).append("\n")
                        .append("skpeq \n")
                        .append("jump :topMulLoop").append(this.mulLoopCtr).append("\n")
                        .append(":endMulLoop").append(this.mulLoopCtr).append(" ")
                        .append("stkpek ").append(op1).append('\n');
                this.mulLoopCtr++;
                break;
            case ParserICGN.DIV:
                op1 -=  (stackLocation - 1);
                op2 -=  (stackLocation - 1);
                codeGenerated
                        .append("stkpek ").append(op1).append("\n")
                        .append("skplt\n")
                        .append("jump :endDivLoop").append(this.divLoopCtr).append("\n")
                        .append("load :L_0\nstore :TEMP\nstkpek ").append(op2).append("\n")
                        .append("store :TEMP2\n").append("stkpek ").append(op1).append("\n")
                        .append(":topDivLoop").append(this.divLoopCtr).append(" ")
                        .append("negate\n")
                        .append("skpgt\n")
                        .append("jump :endDivLoop").append(this.divLoopCtr).append("\n")
                        .append("negate\n")
                        .append("stkpsh ").append(op1).append("\n")
                        .append("load :TEMP\n")
                        .append("add :L_1\n")
                        .append("store :TEMP\n")
                        .append("stkpek ").append(op1).append("\n")
                        .append("subt ").append(":TEMP2").append("\n")
                        .append("stkpsh ").append(op1).append("\n")
                        .append("jump :topDivLoop").append(this.divLoopCtr).append("\n")
                        .append(":endDivLoop").append(this.divLoopCtr).append(" ")
                        .append("load :TEMP\n")
                        .append("skpeq\n")
                        .append("subt :L_N1\n");
                this.divLoopCtr++;
                break;

            case ParserICGN.MOD:
                op1 -=  (stackLocation - 1);
                op2 -=  (stackLocation - 1);
                codeGenerated
                        .append("stkpek ").append(op1).append("\n")
                        .append("jump :endDivLoop").append(this.divLoopCtr).append("\n")
                        .append("load :L_0\nstore :TEMP\nstkpek ").append(op2).append("\n")
                        .append("store :TEMP2\n").append("stkpek ").append(op1).append("\n")
                        .append(":topDivLoop").append(this.divLoopCtr).append(" ")
                        .append("negate\n")
                        .append("skpgt\n")
                        .append("jump :endDivLoop").append(this.divLoopCtr).append("\n")
                        .append("negate\n")
                        .append("stkpsh ").append(op1).append("\n")
                        .append("load :TEMP\n")
                        .append("add :L_1\n")
                        .append("store :TEMP\n")
                        .append("stkpek ").append(op1).append("\n")
                        .append("subt ").append(":TEMP2").append("\n")
                        .append("stkpsh ").append(op1).append("\n")
                        .append("jump :topDivLoop").append(this.divLoopCtr).append("\n")
                        .append(":endDivLoop").append(this.divLoopCtr).append(" ")
                        .append("stkpek ").append(op2).append("\n")
                        .append("store :TEMP2")
                        .append("load :TEMP\n")
                        .append("negate\n")
                        .append("add ").append("TEMP2").append("\n");
                this.divLoopCtr++;

        }
        return codeGenerated.toString();
    }

    public String addLiteral(String lit) {
        String literalVar = "L_";
        char firstChar = lit.charAt(0);
        if (firstChar == '-') {
            literalVar += ("N" + lit);
        }
        else if (firstChar == 't') {
            literalVar += "TRUE";
        }
        else if (firstChar == 'f') {
            literalVar += "FALSE";
        }
        else {
            literalVar += lit;
        }

        if (this.literalIDs.containsKey(lit)) {
            return literalVar;
        }
        this.literalIDs.put(lit, literalVar);
        return literalVar;

    }

    public void addGlobalVar(String id, int size) {
        if (size == 1) {
            this.idents.append(":").append(id).append(" DEC 1\n");
            this.mainMemLocation++;
        }
        else {
            this.mainMemLocation++;
            this.globalMMLocation.put(id, this.mainMemLocation);
            this.idents.append(":").append(id).append(" ").append("DEC 1")
                    .append("\n");
            this.mainMemLocation += size;
        }
    }

    public void addLocalVar(String id) {
        if (!this.identStackLocation.containsKey(id)) {
            this.identStackLocation.put(id, stackLocation);
        }

        this.stackLocation++;
    }

    public int getCurrentStackLocation() {
        return this.stackLocation;
    }
    public int getStackLocationOfID(String id) {
        return this.identStackLocation.getOrDefault(id, -1);
    }
    public int getIfCtr() {
        this.ifCtr++;
        return this.ifCtr;
    }

    public int getIfCtrNoInc() {
        return this.ifCtr;
    }

    public int getBoolCtr() {
        this.boolCtr++;
        return this.boolCtr;
    }

    public int getBoolCtrNoInc() {return this.boolCtr;}

    public int getWhileCtr() {
        this.whileCtr++;
        return this.whileCtr;
    }

    public int getWhileCtrNoInc() {return this.whileCtr;}

    public void putFunctionParamIDs(String funcID, ArrayList<String> params) {
        this.functionVarNames.put(funcID, params);
    }

    public void setCurrentFunction(String currentFunction) {
        this.currentFunction = currentFunction;
        this.functionLocalCount.put(currentFunction, 0);
    }

    public void incLocalVarCtr() {
        this.functionLocalCount.put(this.currentFunction, this.functionLocalCount.get(this.currentFunction) + 1);
    }
    public int getLocalVarCtr() {
        return this.functionLocalCount.get(this.currentFunction);
    }

    public String getFunctionParamIDs(String funcID, int paramNum) {
        ArrayList<String> functionVars = this.functionVarNames.get(funcID);
        if (functionVars == null) {
            return "ERROR_FINDING_FUNCTION_NAME___" + funcID;
        }
        else {
            return functionVars.get(paramNum);
        }
    }

    public String getGlobalVarCode() {
        StringBuffer globalCode = new StringBuffer();
        for (String key : this.literalIDs.keySet()) {
            String init;
            if (key.equals("true")) {
                init = "1";
            }
            else if (key.equals("false")) {
                init = "-1";
            }
            else {
                init = key;
            }
            globalCode.append(":").append(this.literalIDs.get(key)).append(" DEC ").append(init).append("\n");
        }
        return this.idents.toString() + globalCode.toString() + ":TEMP DEC 1\n:TEMP2 DEC 1\n:MUL_TEMP DEC 1\n:MUL_TEMP2 DEC 1\n" +
                ":DIV_TEMP DEC 1\n:DIV_TEMP2 DEC 1\n:MOD_TEMP DEC 1\n:MOD_TEMP2 DEC 1\n";
    }
    public void incStackLocation() {
        this.stackLocation++;
    }

}
