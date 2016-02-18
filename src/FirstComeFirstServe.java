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


    @Override
    public void addProcess(SimulatedProcess proc) {
        readyQueue.add(proc);
    }

    @Override
    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }

    @Override
    public void executing(float time) {
        if (shouldStop || readyQueue.isEmpty())
            return;
        SimulatedProcess proc = readyQueue.get(0);
        proc.executing(time);
        System.out.print(proc.getName());
        for (int i = 1; i < readyQueue.size(); i++) {
            readyQueue.get(i).waiting();
        }
        if (proc.isFinished()) {
            finished.put(time, proc);
            readyQueue.remove(0);
            if (time >= CPUScheduler.QUANTA_TO_RUN - 1.0) {
                shouldStop = true;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return shouldStop;
    }

    @Override
    public Collection<SimulatedProcess> getFinishedProcesses() {
        return finished.values();
    }

}
