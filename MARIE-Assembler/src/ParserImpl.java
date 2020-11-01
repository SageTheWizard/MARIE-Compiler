import java.util.HashMap;

public class ParserImpl
{
    Lexer lexer;
    int offset;

    HashMap<String, Integer> symbolTable = new HashMap<String, Integer>();

    String output = "";

    Object startOrg(Object orgNum, Object program) throws MARIEAssemblerException {
        offset = (int) orgNum;
        output = (String) program;
        //check to make sure everything has an address and replace the labels with numbers
        for(String s : symbolTable.keySet()) {
            if(symbolTable.get(s) == -1) {
                //there is no address for this
                throw new MARIEAssemblerException("Label " + s + " does not point to an address.");
            }

            output = output.replace(s, "" + symbolTable.get((String) s));

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

    Object lineLabelLine_(Object label, Object line) throws MARIEAssemblerException {
        if(symbolTable.containsKey((String) label)) {
            if(symbolTable.get(label) == -1) {
                symbolTable.put((String) label, offset + lexer.lineno);
            }
            else {
                //duplicate label
                throw new MARIEAssemblerException("Duplicate label " + label + " found at line: " + offset + lexer.lineno);
            }
        }
        else {
            //symbol table doesn't have that label
            symbolTable.put((String) label, lexer.lineno);
        }
        return label.toString() + line.toString() + '\n';
    }

    Object lineLine_(Object instr) {

        return instr.toString() + '\n';
    }

    Object line_InstrOperand(Object instr, Object operand) {

        return instr.toString() + padLeft(operand.toString(), 3);
    }

    Object newLine(Object newLine) {
        output += '\n';
        return '\n';
    }

    Object operandLabel(Object label) {
        if(!symbolTable.containsKey((String) label)) {
            symbolTable.put((String) label, -1); //use -1 as a placeholder since we don't have an address yet
        }
        return label;
    }

    Object numHex_num(Object hex) throws MARIEAssemblerException {
        int hexNum = Integer.parseInt((String) hex, 16);
        if(hexNum >= MARIEValues.INT_MIN_VALUE && hexNum <= MARIEValues.INT_MAX_VALUE) {
            return Integer.toHexString(hexNum).toUpperCase();
        }
        else {
            throw new MARIEAssemblerException("Number is not in the range 0000 to FFFF");
        }
    }

    Object numDec_num(Object dec) throws MARIEAssemblerException {
        int decNum = Integer.parseInt((String) dec);
        if (decNum >= MARIEValues.INT_MIN_VALUE && decNum <= MARIEValues.INT_MAX_VALUE) {
            return Integer.toHexString(decNum).toUpperCase();
        }
        else {
            throw new MARIEAssemblerException("Number is not in the range 0000 to FFFF");
        }
    }

    Object numOct_num(Object oct) throws MARIEAssemblerException {
        int octNum = Integer.parseInt((String) oct, 8);
        if (octNum >= MARIEValues.INT_MIN_VALUE && octNum <= MARIEValues.INT_MAX_VALUE) {
            return Integer.toHexString(octNum).toUpperCase();
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
