package MARIE.Compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class Instructions {
    private HashMap<String, Integer> identStackLocation;
    private HashMap<String, Integer> globalMMLocation;
    private HashMap<String, ArrayList<String>> functionVarNames;
    private int mulLoopCtr;
    private int divLoopCtr;
    private int ifCtr;
    private int boolCtr;
    private int whileCtr;
    private int mainMemLocation;
    private int stackLocation;
    private StringBuffer idents;

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
        this.functionVarNames = new HashMap<>();
    }
    public String pseudoInstructions(short parserOp, String operand1, String operand2){
        int op1;
        int op2;
        StringBuffer codeGenerated = new StringBuffer();
        op1 = this.identStackLocation.getOrDefault(operand1, -1);
        op2 = this.identStackLocation.getOrDefault(operand2, -1);

        switch(parserOp){
            case Parser.MUL:
                codeGenerated
                        .append("temp, dec 0\n")
                        .append("stack peek ").append(op2).append("\n")
                        .append("skipcond 800\n")
                        .append("jump endMulLoop").append(this.mulLoopCtr).append("\n")
                        .append("topMulLoop").append(this.mulLoopCtr).append("\n")
                        .append("load temp\n")
                        .append("add ").append(op1).append("\n")
                        .append("store temp\n")
                        .append("loadi ").append(op2).append("\n")
                        .append("subt 1\n")
                        .append("stack push ").append(op2).append("\n")
                        .append("skipcond 400\n")
                        .append("jump topMulLoop").append(this.mulLoopCtr).append("\n")
                        .append("endMulLoop").append(this.mulLoopCtr).append("\n")
                        .append("load temp");
                this.mulLoopCtr++;
                break;
            case Parser.DIV:
                codeGenerated
                        .append("temp, dec 0\n")
                        .append("stack peek ").append(op1).append("\n")
                        .append("skipcond 400\n")
                        .append("jump endDivLoop").append(this.divLoopCtr).append("\n")
                        .append("topDivLoop").append(this.divLoopCtr).append("\n")
                        .append("negate\n")
                        .append("skipcond 800").append(this.divLoopCtr).append("\n")
                        .append("jump endDivLoop").append(this.divLoopCtr).append("\n")
                        .append("negate\n")
                        .append("stack push ").append(op1).append("\n")
                        .append("load temp\n")
                        .append("add 1\n")
                        .append("store temp\n")
                        .append("loadi ").append(op1).append("\n")
                        .append("subt ").append(op2).append("\n")
                        .append("store ").append(op1).append("\n")
                        .append("jump topDivLoop").append(this.divLoopCtr).append("\n")
                        .append("endDivLoop").append(this.divLoopCtr).append("\n")
                        .append("load temp")
                        .append("subt 1");
                this.divLoopCtr++;
                break;

            case Parser.MOD:
                codeGenerated
                        .append("stack peek ").append(op1).append("\n")
                        .append("skipcond 400\n")
                        .append("jump :endDivLoop").append(this.divLoopCtr).append("\n")
                        .append(":topDivLoop").append(this.divLoopCtr).append("\n")
                        .append("negate\n")
                        .append("skipcond 800")
                        .append("jump :endDivLoop").append(this.divLoopCtr).append("\n")
                        .append("negate\n")
                        .append("stack push ").append(op1).append("\n")
                        .append("load :temp\n")
                        .append("add 1\n")
                        .append("store :temp\n")
                        .append("stack peek ").append(op1).append("\n")
                        .append("subt ").append(op2).append("\n")
                        .append("store ").append(op1).append("\n")
                        .append("jump topDivLoop").append(this.divLoopCtr).append("\n")
                        .append("endDivLoop").append(this.divLoopCtr).append("\n")
                        .append("negate\n")
                        .append("add ").append(op2).append("\n");
                this.divLoopCtr++;

        }
        return codeGenerated.toString();
    }

    public void addGlobalVar(String id, int size) {
        if (size > 1) {
            this.idents.append(":").append(id).append(" DEC 1\n");
            this.mainMemLocation++;
        }
        else {
            this.mainMemLocation++;
            this.globalMMLocation.put(id, this.mainMemLocation);
            this.idents.append(":").append(id).append(" ").append(this.mainMemLocation)
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
        return this.idents.toString() + ":TEMP DEC 1\n:TEMP2 DEC 1";
    }

}
