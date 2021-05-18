package MARIE;

public class MARIEValues {
    public static final int INT_MIN_VALUE = -32768;
    public static final int INT_MAX_VALUE = 32767;
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
    public static final int INPUT = 0xF;
    public static final int OUTPUT = 0xF1;
    public static final int NEGATE = 0xF2;
    public static final int HALT = 0xF3;
    public static final int SKPLT = 0xF4;
    public static final int SKPEQ = 0xF5;
    public static final int SKPGT = 0xF6;
    public static final int JMPRT = 0xF7;
    public static final String EXTENSION = "mre";
    public static final String EXECUTABLE_EXTENSION = "mex";
}
