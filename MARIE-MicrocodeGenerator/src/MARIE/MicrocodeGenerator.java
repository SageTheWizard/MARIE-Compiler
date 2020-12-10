package MARIE;

public class MicrocodeGenerator {
    final static int MAX_INSTR_SIZE = 0x100;
    final static int MAX_CLOCK_STEPS = 16;
    // INSTRUCTION DECLARATIONS GO HERE
    // Operand Instructions
    public static final int JUMP = 0;
    public static final int LOAD = 1;
    public static final int STORE = 2;
    public static final int ADD = 3;
    public static final int SUBT = 4;
    public static final int CLEAR = 5;
    public static final int JNP = 6;
    public static final int STKINC = 7;
    public static final int STKDEC = 8;
    public static final int STKPSH = 9;
    public static final int STKPEK = 0xA;
    public static final int ADDI = 0xB;
    public static final int JUMPI = 0xC;
    public static final int STOREI = 0xD;
    public static final int LOADI = 0xE;
    public static final int INPUT = 0xF0;
    public static final int OUTPUT = 0xF1;
    public static final int NEGATE = 0xF2;
    public static final int HALT = 0xF3;
    public static final int SLT = 0xF4;
    public static final int SEQ = 0xF5;
    public static final int SGT = 0xF6;
    public static final int JRT = 0xF7;


    /*
     * CONTROL OUTPUT GOES HERE
     * control output should take the form:
     * static final int pin = 1 << PIN_POS
     * where PIN_POS is the pin that the ROM would output the data on
     */

    public static final int IOIN = 1 << 28;
    public static final int IOOUT = 1 << 27;
    public static final int SPDEC = 1 << 26; //stack pointer decrement
    public static final int SPINC = 1 << 25; //stack pointer increment
    public static final int NEG = 1 << 24; //negate
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
    public static final int OUT = 1 << 7; //Output
    public static final int IO = 1 << 6; //Instruction Register Out [11-0]
    public static final int II = 1 << 5; //Instruction Register In
    public static final int MCR = 1 << 4; //Microcode Counter Reset
    public static final int ONE = 1 << 3; //puts 1 on bus
    public static final int IN = 1 << 2; //input
    public static final int SPI = 1 << 1; //Stack pointer in
    public static final int SPO = 1 << 0; //Stack pointer out

    private static int[][] microCode = null;

    public static void generateMicrocode() {
        microCode = new int[MAX_INSTR_SIZE][MAX_CLOCK_STEPS];
        //fetch cycle first
        for(int i = 0; i <= 0xFF; i++) {
            microCode[i][0] = MI + CO; // MAR <- PC
            microCode[i][1] = II + RO; // IR <- M[MAR]
            microCode[i][2] = CE; //PC <- PC + 1
        }

        //Single operand instructions should be run in for loops to cover all possible operands
        for(int i = JUMP * 0x0f; i <= JUMP * 0x0f +  0x0F; i++) {
            microCode[i][3] = IO + JMP;
            microCode[i][4] = MCR;
        }

        for(int i = LOAD * 0x0f; i <= LOAD * 0x0f + 0x0F; i++) {
            microCode[i][3] = MI + IO;
            microCode[i][4] = MBRI + RO;
            microCode[i][5] = ACI + MBRO;
            microCode[i][6] = MCR;
        }

        for(int i = STORE * 0x0f; i <= STORE * 0x0f + 0x0F; i++) {
            microCode[i][3] = MI + IO;
            microCode[i][4] = MBRI + ACO;
            microCode[i][5] = RI + MBRO;
            microCode[i][6] = MCR;
        }

        for(int i = ADD * 0x0f; i <= ADD * 0x0f + 0x0F; i++) {
            microCode[i][3] = MI + IO;
            microCode[i][4] = MBRI + RO;
            microCode[i][5] = ALUO + ACI;
            microCode[i][6] = MCR;
        }

        for(int i = SUBT * 0x0f; i <= SUBT * 0x0f + 0x0F; i++) {
            microCode[i][3] = MI + IO;
            microCode[i][4] = MBRI + RO;
            microCode[i][5] = ALUO + ACI + SU;
            microCode[i][6] = MCR;
        }

        for(int i = CLEAR * 0x0f; i <= CLEAR * 0x0f + 0x0F; i++) {
            microCode[i][3] = ACI;
            microCode[i][4] = MCR;
        }

        for(int i = JNP * 0x0f; i <= JNP * 0x0f + 0x0F; i++) {
            microCode[i][3] = ONE + SPINC;
            microCode[i][4] = SPO + MI;
            microCode[i][5] = CO + RI;
            microCode[i][6] = IO + JMP;
            microCode[i][7] = MCR;
        }

        for(int i = STKINC * 0x0f; i <= STKINC * 0x0f + 0x0F; i++) {
            microCode[i][3] = SPINC;
            microCode[i][4] = MCR;
        }

        for(int i = STKDEC * 0x0f; i <= STKDEC * 0x0f + 0x0F; i++) {
            microCode[i][3] = SPDEC;
            microCode[i][4] = MCR;
        }

        for(int i = STKPSH * 0x0f; i <= STKPSH * 0x0f + 0x0F; i++) {
            microCode[i][3] = SPO + MI;
            microCode[i][4] = ACO + RI;
            microCode[i][5] = MCR;
        }

        for(int i = STKPEK * 0x0f; i <= STKPEK * 0x0f + 0x0F; i++) {
            microCode[i][3] = SPO + MI;
            microCode[i][4] = ACI + RO;
            microCode[i][5] = MCR;
        }

        for(int i = ADDI * 0x0f; i <= ADDI * 0x0f + 0x0F; i++) {
            microCode[i][3] = IO + MI;
            microCode[i][4] = MBRI + RO;
            microCode[i][5] = MBRO + MI;
            microCode[i][6] = RO + MBRI;
            microCode[i][7] = ACI + ALUO;
            microCode[i][8] = MCR;
        }

        for(int i = JUMPI * 0x0f; i <= JUMPI * 0x0f + 0x0F; i++) {
            microCode[i][3] = IO + MI;
            microCode[i][4] = RO + MBRI;
            microCode[i][5] = MBRO + JMP;
            microCode[i][6] = MCR;
        }

        for(int i = STOREI * 0x0f; i <= STOREI * 0x0f + 0x0F; i++) {
            microCode[i][3] = IO + MI;
            microCode[i][4] = RO + MBRI;
            microCode[i][5] = MBRO + MI;
            microCode[i][6] = ACO + MBRI;
            microCode[i][7] = RI + MBRO;
            microCode[i][8] = MCR;
        }

        for(int i = LOADI * 0x0f; i <= LOADI * 0x0f + 0x0F; i++) {
            microCode[i][3] = IO + MI;
            microCode[i][4] = RO + MBRI;
            microCode[i][5] = MBRO + MI;
            microCode[i][6] = RO + MBRI;
            microCode[i][7] = MBRO + ACI;
            microCode[i][8] = MCR;
        }

        //now that we are in the extended opcodes we don't need for loops anymore

        microCode[INPUT][3] = IN;
        microCode[INPUT][4] = IOOUT + ACI;
        microCode[INPUT][5] = MCR;

        microCode[OUTPUT][3] = ACO + IOIN;
        microCode[OUTPUT][4] = OUT;
        microCode[OUTPUT][5] = MCR;

        microCode[NEGATE][3] = NEG;
        microCode[NEGATE][4] = MCR;

        microCode[HALT][3] = HLT;

        microCode[SLT][3] = LTZ;
        microCode[SLT][4] = MCR;

        microCode[SEQ][3] = EQZ;
        microCode[SEQ][4] = MCR;

        microCode[SGT][3] = GTZ;
        microCode[SGT][4] = MCR;

        microCode[JRT][3] = SPO + MI;
        microCode[JRT][4] = RO + MBRI;
        microCode[JRT][5] = RI + ACO;
        microCode[JRT][6] = ONE + ACI;
        microCode[JRT][7] = JMP + ALUO;
        microCode[JRT][8] = RO + ACI;
        microCode[JRT][9] = SPDEC + ONE;
        microCode[JRT][10] = MCR;
    }

    public static int[][] getMicroCode() {
        if(microCode == null) {
            generateMicrocode();
        }
        return microCode;
    }
}


