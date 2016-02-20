/*
 * CS 149 Group 2
 * Homework 2
 */

/**
 *
 * @author Johnny
 */
public class SimulatedProcess{

    private static long nextpID = 0;
    private final long pId;
    private final String name;
    private final float arrivalTime, totalRunTime;
    private final int priority;
    private double waiting, response = 0;
    private float startTime = -1, finishTime;
    private boolean isFinished;
    private float timeRemaining;

    /**
     *
     * @param arrivalTime Arrival time of process
     * @param totalRunTime How long the process should run
     * @param priority Priority of the process
     */
    public SimulatedProcess(float arrivalTime, float totalRunTime, int priority) {
        pId = ++nextpID;
        this.name = "P" + pId;
        this.arrivalTime = arrivalTime;
        this.totalRunTime = totalRunTime;
        this.priority = priority;
        this.timeRemaining = totalRunTime;
    }

    /**
     * Returns name of the process
     * @return name of process
     */
    public String getName() {
        return name;
    }

    /**
     * Returns when this process should be added to the scheduler
     * @return arrival time
     */
    public float getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Returns how long this process should run for
     * @return length of the process
     */
    public float getTotalRunTime() {
        return totalRunTime;
    }

    /**
     * Returns priority of this process
     * @return priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Returns total life time of this process
     * @return turnaround time
     */
    public double getTurnaroundTime() {
        return waiting + totalRunTime;
    }

    /**
     * Returns how long this process has waited after being added to the scheduler
     * @return waiting time
     */
    public double getWaitingTime() {
        return waiting;
    }

    /**
     * Returns how long this process has waited before it started execution
     * @return response time
     */
    public double getResponseTime() {
        return response;
    }

    /**
     * Simulates an execution of a process
     * Only decreases time remaining
     * @param time current CPU time
     */
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

    /**
     * Increase waiting time of process
     */
    public void waiting() {
        waiting += CPUScheduler.TIME_UNIT_QUANTA;
    }

    /**
     * Returns whether the process has finished execution
     * @return whether process has finished
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Returns the time when this process was completed
     * @return finish time
     */
    public float getFinishTime() {
        return finishTime;
    }

    /**
     * Returns how long this process must be executed before completion
     * @return time remaining
     */
    public float getTimeRemaining() {
        return timeRemaining;
    }

    @Override
    public String toString() {
        return "Name=" + name + "/Arrival Time=" + arrivalTime + "/Expected Runtime=" + totalRunTime + "/Priority=" + priority;
    }

}
