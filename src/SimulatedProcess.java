/*
 * CS 149 Group 2
 * Homework 2
 */
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johnny
 */
public class SimulatedProcess implements Runnable {

    private final String name;
    private final float arrivalTime, totalRunTime;
    private final int priority;

    public SimulatedProcess(String name, float arrivalTime, float totalRunTime, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.totalRunTime = totalRunTime;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public float getArrivalTime() {
        return arrivalTime;
    }

    public float getTotalRunTime() {
        return totalRunTime;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return name + ": AT=" + arrivalTime + " TR=" + totalRunTime + " priority=" + priority;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) totalRunTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimulatedProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
