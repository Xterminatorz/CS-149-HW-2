
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Frank
 *
 */
public class RoundRobin implements Scheduler {

    private final LinkedList<SimulatedProcess> readyQueue = new LinkedList<>();
    private final Map<Float, SimulatedProcess> finished = new HashMap<>();
    private boolean shouldStop = false;

    @Override
    public void addProcess(SimulatedProcess proc) {
        readyQueue.add(proc);
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
        } else {
            readyQueue.offer(readyQueue.poll());
        }
    }

    @Override
    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }

    /**
     * Resets scheduler
     */
    @Override
    public void reset() {
        readyQueue.clear();
        finished.clear();
        shouldStop = false;
    }

    @Override
    public Collection<SimulatedProcess> getFinishedProcesses() {
        return finished.values();
    }

    @Override
    public boolean shouldStop() {
        return shouldStop;
    }

}
