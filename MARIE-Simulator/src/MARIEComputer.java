import java.io.File;

abstract class MARIEComputer {


    static final int MAX_MEMORY_SIZE = 4096;


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

    abstract int input();

    abstract void output(int output);

    /**
     * Simulates a clock tick and updates all necessary registers
     * @return true if the computer halts, false otherwise
     */
    private boolean clockTick() {
        int mcBitString = MicrocodeGenerator.getMicroCode()[0b1111111100000000 & ioReg][microcodeCounter];
        //do outputs first so we can populate the bus
        if((mcBitString & MicrocodeGenerator.RO) != 0) {
            bus = mainMemory[memoryAddrReg];
        }

        if((mcBitString & MicrocodeGenerator.CO) != 0) {
            bus = programCtr;
        }

        if((mcBitString & MicrocodeGenerator.ACO) != 0) {
            bus = accumulator;
        }

        //we check to see if the ALU is subtracting first
        if((mcBitString & MicrocodeGenerator.SU) != 0 && (mcBitString & MicrocodeGenerator.ALUO) != 0) {
            bus = accumulator - memoryBufferReg;
        }
        //otherwise add the two numbers
        else if((mcBitString & MicrocodeGenerator.ALUO) != 0) {
            bus = accumulator + memoryBufferReg;
        }

        if((mcBitString & MicrocodeGenerator.MBRO) != 0) {
            bus = memoryBufferReg;
        }

        if((mcBitString & MicrocodeGenerator.IO) != 0) {
            bus = instructionReg;
        }

        if((mcBitString & MicrocodeGenerator.ONE) != 0) {
            bus = 1;
        }

        if((mcBitString & MicrocodeGenerator.SPO) != 0) {
            bus = stackPointer;
        }

        //then we do io and skipcond operations
        if((mcBitString & MicrocodeGenerator.OUTPUT) != 0) {
            output(accumulator);
        }

        if((mcBitString & MicrocodeGenerator.INPUT) != 0) {
            accumulator = input();
        }

        if((mcBitString & MicrocodeGenerator.LTZ) != 0) {
            if(accumulator < 0) {
                programCtr++;
            }
        }

        if((mcBitString & MicrocodeGenerator.EQZ) != 0) {
            if(accumulator == 0) {
                programCtr++;
            }
        }

        if((mcBitString & MicrocodeGenerator.GTZ) != 0) {
            if(accumulator > 0) {
                programCtr++;
            }
        }

        //then we do inputs
        if((mcBitString & MicrocodeGenerator.MI) != 0) {
            memoryAddrReg = bus;
        }

        if((mcBitString & MicrocodeGenerator.RI) != 0) {
            mainMemory[memoryAddrReg] = bus;
        }

        if((mcBitString & MicrocodeGenerator.ACI) != 0) {
            accumulator = bus;
        }

        if((mcBitString & MicrocodeGenerator.MBRI) != 0) {
            memoryBufferReg = bus;
        }

        if((mcBitString & MicrocodeGenerator.II) != 0) {
            instructionReg = bus;
        }

        if((mcBitString & MicrocodeGenerator.SPI) != 0) {
            stackPointer = bus;
        }

        //then we check for jump
        if((mcBitString & MicrocodeGenerator.JMP) != 0) {
            programCtr = bus;
        }

        //then we increment program counter
        if((mcBitString & MicrocodeGenerator.CE) != 0) {
            programCtr++;
        }

        //then we increment the microcodeCounter
        microcodeCounter++;
        //reset it if needed
        if((mcBitString & MicrocodeGenerator.MCR) != 0) {
            microcodeCounter = 0;
        }

        //check to see if we halt or not
        return (mcBitString & MicrocodeGenerator.HLT) != 0;
    }

    public void loadFile(File file) {
        //TODO implement
    }



}
