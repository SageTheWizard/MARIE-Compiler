package MARIE;

public class ClockStep {
    private String programCtr;
    private String instructionReg;
    private String ioReg;
    private String accumulator;
    private String stackPointer;

    private String memoryBufferReg;
    private String memoryAddrReg;

    private int previousStep;

    private String prevStepString;

    public ClockStep(int programCtr, int instructionReg, int ioReg, int accumulator, int stackPointer, int memoryBufferReg, int memoryAddrReg, int previousStep) {
        this.programCtr = Integer.toHexString(programCtr);
        this.instructionReg = Integer.toHexString(instructionReg);
        this.ioReg = Integer.toHexString(ioReg);
        this.accumulator = Integer.toHexString(accumulator);
        this.stackPointer = Integer.toHexString(stackPointer);
        this.memoryBufferReg = Integer.toHexString(memoryBufferReg);
        this.memoryAddrReg = Integer.toHexString(memoryAddrReg);
        this.previousStep = previousStep;
        this.prevStepString = "" + previousStep;
    }


    public void setProgramCtr(int programCtr) {
        this.programCtr = Integer.toHexString(programCtr);
    }


    public void setInstructionReg(int instructionReg) {
        this.instructionReg = Integer.toHexString(instructionReg);
    }


    public void setIoReg(int ioReg) {
        this.ioReg = Integer.toHexString(ioReg);
    }


    public void setAccumulator(int accumulator) {
        this.accumulator = Integer.toHexString(accumulator);
    }


    public void setStackPointer(int stackPointer) {
        this.stackPointer = Integer.toHexString(stackPointer);
    }


    public void setMemoryBufferReg(int memoryBufferReg) {
        this.memoryBufferReg = Integer.toHexString(memoryBufferReg);
    }


    public void setMemoryAddrReg(int memoryAddrReg) {
        this.memoryAddrReg = Integer.toHexString(memoryAddrReg);
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
