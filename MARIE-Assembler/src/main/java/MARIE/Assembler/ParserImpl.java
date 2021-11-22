package MARIE.Assembler;

import java.util.HashMap;

public class ParserImpl
{
    Lexer lexer;
    int offset;

    HashMap<String, Integer> symbolTable = new HashMap<String, Integer>();

    protected String outputArr[];

    Object startOrg(Object orgNum, Object program) throws MARIEAssemblerException {
        offset = Integer.parseInt((String) orgNum, 16);
        outputArr = ("ORG " + Integer.toHexString(offset) + "\n" + program).split("\\n");
        //check to make sure everything has an address and replace the labels with numbers
        for(String s : symbolTable.keySet()) {
            if(symbolTable.get(s) == -1) {
                //there is no address for this
                throw new MARIEAssemblerException("Label " + s + " does not point to an address.");
            }

            for(int i = 0; i < outputArr.length; i++) {
                if (outputArr[i].indexOf(s) == 0) { //this means the label comes first and we should straight up delete it
                    outputArr[i] = padLeft(outputArr[i].substring(s.length()), 4);
                }
                else {
                    outputArr[i] = outputArr[i].replace(s, Integer.toHexString(symbolTable.get(s) + offset));
                }
            }

        }


        return null; //TODO fix
    }

    Object start(Object program) throws MARIEAssemblerException {

        return startOrg(100, program); //by default we start ORG at 100
    }

    Object prgmLinePrgm(Object line, Object program) {

        return line.toString() + program.toString(); //TODO fix
    }

    Object prgmLineEnd(Object line) {

        return line.toString();
    }

    Object lineLabelLine_(MARIELabel label, Object line) throws MARIEAssemblerException {
        if(symbolTable.containsKey(label.name)) {
            if(symbolTable.get(label.name) == -1) {
                symbolTable.put(label.name, label.lineno);
            }
            else {
                //duplicate label
                throw new MARIEAssemblerException("Duplicate label " + label.name + " found at line: " + label.lineno);
            }
        }
        else {
            //symbol table doesn't have that label
            symbolTable.put(label.name, label.lineno);
        }
        return label.name + line.toString() + '\n';
    }

    Object lineLine_(Object instr) {

        return instr.toString() + '\n';
    }

    Object line_InstrOperand(Object instr, Object operand) {
        if(operand instanceof MARIELabel) {
            return instr.toString() + padLeft(((MARIELabel) operand).name, 3);
        }
        return instr.toString() + padLeft(operand.toString(), 3);
    }

    Object newLine(Object newLine) {
        return "0000\n";
    }

    Object operandLabel(MARIELabel label) {
        if(!symbolTable.containsKey(label.name)) {
            symbolTable.put(label.name, -1); //use -1 as a placeholder since we don't have an address yet
        }
        return label;
    }

    Object numHex_num(Object hex) throws MARIEAssemblerException {
        int hexNum = Integer.parseInt((String) hex, 16);
        if(hexNum >= MARIEValues.INT_MIN_VALUE && hexNum <= MARIEValues.INT_MAX_VALUE) {
            return Integer.toHexString(hexNum & 0xFFFF).toUpperCase();
        }
        else {
            throw new MARIEAssemblerException("Number is not in the range 0000 to FFFF");
        }
    }

    Object numDec_num(Object dec) throws MARIEAssemblerException {
        int decNum = Integer.parseInt((String) dec);
        if (decNum >= MARIEValues.INT_MIN_VALUE && decNum <= MARIEValues.INT_MAX_VALUE) {
            return Integer.toHexString(decNum & 0xFFFF).toUpperCase();
        }
        else {
            throw new MARIEAssemblerException("Number is not in the range 0000 to FFFF");
        }
    }

    Object numOct_num(Object oct) throws MARIEAssemblerException {
        int octNum = Integer.parseInt((String) oct, 8);
        if (octNum >= MARIEValues.INT_MIN_VALUE && octNum <= MARIEValues.INT_MAX_VALUE) {
            return Integer.toHexString(octNum & 0xFFFF).toUpperCase();
        }
        else {
            throw new MARIEAssemblerException("Number is not in the range 0000 to FFFF");
        }
    }

    String padRight(String toPad) {

        StringBuilder toPadBuilder = new StringBuilder(toPad);
        for(int i = toPadBuilder.length(); i < 4; i++) {
                toPadBuilder.append('0');
            }
        return toPadBuilder.toString();
    }

    String padLeft(String toPad, int length) {
        StringBuilder toPadBuilder = new StringBuilder(toPad);
        for(int i = toPadBuilder.length(); i < length; i++) {
            toPadBuilder.insert(0, '0');
        }
        return toPadBuilder.toString();
    }
}
