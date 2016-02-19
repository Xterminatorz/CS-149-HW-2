/*
 * CS 149 Group 2
 * Homework 2
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Johnny
 */
public class FirstComeFirstServe implements Scheduler {

    private final List<SimulatedProcess> readyQueue = new ArrayList();
    private final Map<Float, SimulatedProcess> finished = new HashMap<>();
    private boolean shouldStop = false;

    /**
     * Adds a process to the ready queue
     * @param proc Process to add
     */
    @Override
    public void addProcess(SimulatedProcess proc) {
        readyQueue.add(proc);
    }

    /**
     * Checks if the ready queue is empty
     * @return whether the queue empty
     */
    @Override
    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }

    /**
     * Executes first job and sends waiting to other
     * @param time Current time of CPU
     */
    @Override
    public void executing(float time) {
        if (shouldStop || readyQueue.isEmpty())
            return;
        SimulatedProcess proc = readyQueue.get(0); // Gets first process in queue
        proc.executing(time); // Execute process
        System.out.print(proc.getName());
        for (int i = 1; i < readyQueue.size(); i++) {
            readyQueue.get(i).waiting(); // Increment wait timer on process
        }
        if (proc.isFinished()) {
            finished.put(time, proc); // Adds to finished list
            readyQueue.remove(0); // Remove from ready queue
            if (time >= CPUScheduler.QUANTA_TO_RUN - 1.0) {
                shouldStop = true;
            }
        }
    }

    /**
     * Returns whether the scheduler should stop
     * @return boolean showing whether the scheduler should stop
     */
    @Override
    public boolean shouldStop() {
        return shouldStop;
    }

    /**
     * Gets list of finished process
     * @return collection of finished processes
     */
    @Override
    public Collection<SimulatedProcess> getFinishedProcesses() {
        return finished.values();
    }

}
