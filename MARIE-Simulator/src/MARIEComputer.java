import java.io.File;

abstract class MARIEComputer {

    final int MAX_MEMORY_SIZE = 4096;

    private int bus = 0;
    private int microcodeCounter = 0;

    private int programCtr = 0;
    private int instructionReg = 0;
    private int ioReg = 0;
    private int accumulator = 0;
    private int stackPointer = 0;

    private int[] mainMemory = new int[MAX_MEMORY_SIZE];
    private int memoryBufferReg = 0;
    private int memoryAddrReg = 0;

    abstract void input(int input);

    abstract int output();

    private void clockTick() {

    }

    public void loadFile(File file) {

    }



}
