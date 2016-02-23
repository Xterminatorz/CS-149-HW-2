/*
 * CS 149 Group 2
 * Homework 2
 */
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Johnny
 */
public interface Scheduler {

    /**
     * Adds a process to the ready queue
     *
     * @param proc Process to add
     */
    void addProcess(SimulatedProcess proc);

    /**
     * Executes job in the algorithm
     *
     * @param time Current time of CPU
     */
    void executing(float time);

    /**
     * Checks if the ready queue is empty
     *
     * @return whether the queue empty
     */
    boolean isEmpty();

    /**
     * Resets scheduler
     */
    void reset();

    /**
     * Gets list of finished process
     *
     * @return collection of finished processes
     */
    Collection<SimulatedProcess> getFinishedProcesses();

    /**
     * Returns whether the scheduler should stop
     *
     * @return boolean showing whether the scheduler should stop
     */
    boolean shouldStop();

    /**
     * Gets mapping of finished process with respective priority queue
     *
     * @return collection of finished processes
     */
    Map<Integer, List<SimulatedProcess>> getFinishedPriorityQueues();

}
