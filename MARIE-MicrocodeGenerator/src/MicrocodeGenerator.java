public class MicrocodeGenerator {
    final static int MAX_INSTR_SIZE = 8;
    final static int MAX_CLOCK_STEPS = 16;
    // INSTRUCTION DECLARATIONS GO HERE

    /*
     * CONTROL OUTPUT GOES HERE
     * control output should take the form:
     * static final int pin = 1 << PIN_POS
     * where PIN_POS is the pin that the ROM would output the data on
     */

    public static final int HLT = 1 << 23; //Halt
    public static final int MI = 1 << 22; //Memory Address In
    public static final int RI = 1 << 21; //RAM in
    public static final int RO = 1 << 20; //RAM out
    public static final int JMP = 1 << 19; //Jump
    public static final int CE = 1 << 18; //Counter Enable
    public static final int CO = 1 << 17; //Counter Out
    public static final int ACI = 1 << 16; //Accumulator in
    public static final int ACO = 1 << 15; //Accumulator out
    public static final int LTZ = 1 << 14; //Accumulator < 0
    public static final int EQZ = 1 << 13; //Accumulator = 0
    public static final int GTZ = 1 << 12; //Accumulator > 0
    public static final int SU = 1 << 11; //Subtract
    public static final int ALUO = 1 << 10; //ALU out
    public static final int MBRO = 1 << 9; //Memory Buffer Register Out
    public static final int MBRI = 1 << 8; //Memory Buffer Register In
    public static final int OUTPUT = 1 << 7; //Output
    public static final int IO = 1 << 6; //Instruction Register Out [11-0]
    public static final int II = 1 << 5; //Instruction Register In
    public static final int MCR = 1 << 4; //Microcode Counter Reset
    public static final int ONE = 1 << 3; //puts 1 on bus
    public static final int INPUT = 1 << 2; //input
    public static final int SPI = 1 << 1; //Stack pointer in
    public static final int SPO = 1 << 0; //Stack pointer out

    private static int[][] microCode = null;

    public static void generateMicrocode() {
        microCode = new int[MAX_INSTR_SIZE][MAX_CLOCK_STEPS];
        //Single operand instructions should be run in for loops to cover all possible operands
    }

    public static int[][] getMicroCode() {
        if(microCode == null) {
            generateMicrocode();
        }
        return microCode;
    }
}


