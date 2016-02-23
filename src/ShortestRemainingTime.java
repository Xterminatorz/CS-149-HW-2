
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestRemainingTime implements Scheduler {

    private final List<SimulatedProcess> readyQueue = new ArrayList<>();
    private final Map<Float, SimulatedProcess> finished = new HashMap<>();
    private boolean shouldStop = false;
    private int prevQueueSize = 0;

    @Override
    public void addProcess(SimulatedProcess proc) {
        readyQueue.add(proc);
    }

    @Override
    public void executing(float time) {
        if (shouldStop || readyQueue.isEmpty())
            return;

        SimulatedProcess proc = readyQueue.get(0); //have to initialize proc to some value

        //finds shortest remaining time if new processes are added
        if (prevQueueSize != readyQueue.size()) {
            float shortestTime = 100000.0f; //initialize a value for shortest time
            for (SimulatedProcess process : readyQueue) {
                if (process.getTimeRemaining() < shortestTime) {
                    proc = process;

                    SimulatedProcess temp = process;
                    process = readyQueue.get(0);
                    readyQueue.set(0, temp);
                }
            }

            prevQueueSize = readyQueue.size();
        }

        proc.executing(time);
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

    @Override
    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }

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
