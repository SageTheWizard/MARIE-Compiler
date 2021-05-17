package MARIE;

public class StackTableRow {
    private int memAddr;
    private int value;

    private String stackOffsetStr;
    private String ValueStr;

    public StackTableRow(int memAddr, int[] mainMem, int stackPointer) {
        this.memAddr = memAddr;
        this.value = mainMem[memAddr];
        stackOffsetStr = "-" + Integer.toHexString(stackPointer - memAddr).toUpperCase();
        ValueStr = Integer.toHexString(value).toUpperCase();
    }

    public int getMemAddr() {
        return memAddr;
    }

    public void setMemAddr(int memAddr) {
        this.memAddr = memAddr;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStackOffsetStr() {
        return stackOffsetStr;
    }

    public void setStackOffsetStr(String stackOffsetStr) {
        this.stackOffsetStr = stackOffsetStr;
    }

    public String getValueStr() {
        return ValueStr;
    }

    public void setValueStr(String valueStr) {
        ValueStr = valueStr;
    }

    public void update(int stackPtr, int[] mainMem) {
        //update the value from main mem
        value = mainMem[memAddr];

        stackOffsetStr = "-" + Integer.toHexString(stackPtr - memAddr).toUpperCase();
        ValueStr = "" + Integer.toHexString(value).toUpperCase();
    }

    public boolean inStack(int stackPtr) {
        return stackPtr - memAddr >= 0;
    }
}
