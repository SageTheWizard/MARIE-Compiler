import java.util.HashMap;

public class ParserImpl
{
    HashMap<String, Integer> symbolTable = new HashMap<String, Integer>();

    String output = "";

    Object startOrg(Object orgNum, Object program) {

        return null; //TODO fix
    }

    Object start(Object program) {

        return startOrg(100, program); //by default we start ORG at 100
    }

    Object prgmLinePrgm(Object line, Object program) {

        return null; //TODO fix
    }

    Object prgmLineEnd(Object line) {

        return null; //TODO fix
    }

    Object lineLabelLine_(Object label, Object line) {
        if(symbolTable.containsKey((String) label)) {
            if(symbolTable.get(label) == -1) {
                symbolTable.put(label, )
            }
        }
        return null; //TODO fix
    }

    Object lineLine_(Object instr) {

        return null; //TODO fix
    }

    Object line_InstrOperand(Object instr, Object operand) {

        return null; //TODO fix
    }

    Object operandLabel(Object label) {
        if(!symbolTable.containsKey((String) label)) {
            symbolTable.put((String) label, -1); //use -1 as a placeholder since we don't have an address yet
        }
        return label; //TODO fix
    }

    Object numHex_num(Object hex) {
        int hexNum = Integer.parseInt((String) hex, 16);
        if(hexNum >= MARIEValues.INT_MIN_VALUE && hexNum <= MARIEValues.INT_MAX_VALUE) {
            return hexNum;
        }
        else {
            throw new ArithmeticException("Number is not in the range 0000 to FFFF");
        }
    }

    Object numDec_num(Object dec) {
        int decNum = Integer.parseInt((String) dec);
        if (decNum >= MARIEValues.INT_MIN_VALUE && decNum <= MARIEValues.INT_MAX_VALUE) {
            return decNum;
        }
        else {
            throw new ArithmeticException("Number is not in the range 0000 to FFFF");
        }
    }

    Object numOct_num(Object oct) {
        int octNum = Integer.parseInt((String) oct, 8);
        if (octNum >= MARIEValues.INT_MIN_VALUE && octNum <= MARIEValues.INT_MAX_VALUE) {
            return octNum;
        }
        else {
            throw new ArithmeticException("Number is not in the range 0000 to FFFF");
        }
    }
}
