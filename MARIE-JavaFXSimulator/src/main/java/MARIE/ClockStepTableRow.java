package MARIE;

public class ClockStepTableRow {
    private String programCtr;
    private String instructionReg;
    private String ioReg;
    private String accumulator;
    private String stackPointer;

    private String memoryBufferReg;
    private String memoryAddrReg;

    private int previousStep;

    private String prevStepString;

    public ClockStepTableRow(int programCtr, int instructionReg, int ioReg, int accumulator, int stackPointer, int memoryBufferReg, int memoryAddrReg, int previousStep) {
        this.programCtr = Integer.toHexString(programCtr).toUpperCase();
        this.instructionReg = Integer.toHexString(instructionReg).toUpperCase();
        this.ioReg = Integer.toHexString(ioReg).toUpperCase();
        this.accumulator = Integer.toHexString(accumulator).toUpperCase();
        this.stackPointer = Integer.toHexString(stackPointer).toUpperCase();
        this.memoryBufferReg = Integer.toHexString(memoryBufferReg).toUpperCase();
        this.memoryAddrReg = Integer.toHexString(memoryAddrReg).toUpperCase();
        this.previousStep = previousStep;
        this.prevStepString = "" + previousStep;
    }


    public void setProgramCtr(int programCtr) {
        this.programCtr = Integer.toHexString(programCtr).toUpperCase();
    }


    public void setInstructionReg(int instructionReg) {
        this.instructionReg = Integer.toHexString(instructionReg).toUpperCase();
    }


    public void setIoReg(int ioReg) {
        this.ioReg = Integer.toHexString(ioReg).toUpperCase();
    }


    public void setAccumulator(int accumulator) {
        this.accumulator = Integer.toHexString(accumulator).toUpperCase();
    }


    public void setStackPointer(int stackPointer) {
        this.stackPointer = Integer.toHexString(stackPointer).toUpperCase();
    }


    public void setMemoryBufferReg(int memoryBufferReg) {
        this.memoryBufferReg = Integer.toHexString(memoryBufferReg).toUpperCase();
    }


    public void setMemoryAddrReg(int memoryAddrReg) {
        this.memoryAddrReg = Integer.toHexString(memoryAddrReg).toUpperCase();
    }


    public void setPreviousStep(int previousStep) {
        this.previousStep = previousStep;
        this.prevStepString = "" + this.previousStep;
    }

    public String getProgramCtr() {
        return programCtr;
    }

    public String getInstructionReg() {
        return instructionReg;
    }

    public String getIoReg() {
        return ioReg;
    }

    public String getAccumulator() {
        return accumulator;
    }

    public String getStackPointer() {
        return stackPointer;
    }

    public String getMemoryBufferReg() {
        return memoryBufferReg;
    }

    public String getMemoryAddrReg() {
        return memoryAddrReg;
    }

    public int getPreviousStep() {
        return previousStep;
    }

    public String getPrevStepString() {
        return prevStepString;
    }
}
