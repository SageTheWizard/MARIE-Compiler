package MARIE;

import java.util.concurrent.Semaphore;

public class SimulatorThread extends MARIEComputer implements Runnable {
    private SimController parent;

    public Semaphore runningSemaphore = new Semaphore(1, true);

    public boolean stop = false;
    public Semaphore stopMutex = new Semaphore(1, true);

    public boolean halted = false;
    public Semaphore haltedMutex = new Semaphore(1, true);

    public int inputBuffer = 0;
    public Semaphore inputBufferMutex = new Semaphore(0, true);
    public Semaphore threadInputSemaphore = new Semaphore(1, true);

    SimulatorThread(SimController parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        try {
            runningSemaphore.acquire();
            haltedMutex.acquire();
            stopMutex.acquire();
            while (!(halted = clockTick()) && !stop) {
                haltedMutex.release();
                stopMutex.release();

                //since the only other things that can update the table while running are the open file and step, we can treat isRunning as the mutex for the tables so we don't have to make another table mutex
                parent.regTableUpdate(false);
                parent.memTableUpdate();

                //grab the semaphore before the while loop check
                stopMutex.acquire();
                haltedMutex.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            runningSemaphore.release();
            haltedMutex.release();//release it once we're outside the while loop
            stopMutex.release();
        }

    }

    @Override
    int input() {
        inputBufferMutex.release();
        parent.input();
        try {
            threadInputSemaphore.acquire();
            inputBufferMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //TODO implement
        }
        return inputBuffer;
    }

    @Override
    void output(int output) {
        parent.output(output);
    }

}
