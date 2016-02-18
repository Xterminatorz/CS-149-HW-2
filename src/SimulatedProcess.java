/*
 * CS 149 Group 2
 * Homework 2
 */

/**
 *
 * @author Johnny
 */
public class SimulatedProcess {

    private static long nextpID = 0;
    private final long pId;
    private final String name;
    private final float arrivalTime, totalRunTime;
    private final int priority;
    private double waiting, response = 0;
    private float startTime = -1, finishTime;
    private boolean isFinished;
    private float timeRemaining;

    public SimulatedProcess(float arrivalTime, float totalRunTime, int priority) {
        pId = ++nextpID;
        this.name = "P" + pId;
        this.arrivalTime = arrivalTime;
        this.totalRunTime = totalRunTime;
        this.priority = priority;
        this.timeRemaining = totalRunTime;
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

    public double getTurnaroundTime() {
        return waiting + totalRunTime;
    }

    public double getWaitingTime() {
        return waiting;
    }

    public double getResponseTime() {
        return response;
    }

    public void executing(float time) {
        if (startTime == -1) {
            startTime = time;
            response = startTime - arrivalTime;
        }
        timeRemaining -= CPUScheduler.TIME_UNIT_QUANTA;
        if (time - startTime >= totalRunTime) {
            isFinished = true;
            finishTime = startTime + totalRunTime;
        }
    }

    public void waiting() {
        waiting += CPUScheduler.TIME_UNIT_QUANTA;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public float getFinishTime() {
        return finishTime;
    }

    public float getTimeRemaining() {
        return timeRemaining;
    }

    @Override
    public String toString() {
        return "Name=" + name + "/Arrival Time=" + arrivalTime + "/Expected Runtime=" + totalRunTime + "/Priority=" + priority;
    }
}
