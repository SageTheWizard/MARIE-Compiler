package MARIE;

public class InstructionTableRow {
    private int memAddr;
    private int value;
    private String label;
    private String instruction;

    public InstructionTableRow(int memAddr, int value, String label, String instruction) {
        this.memAddr = memAddr;
        this.value = value;
        this.label = label;
        this.instruction = instruction;
    }

    public int getMemAddr() {
        return memAddr;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public String getInstruction() {
        return instruction;
    }
}
