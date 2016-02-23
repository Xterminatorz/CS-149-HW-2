
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HPFNonpreemptive implements Scheduler {

    private final LinkedList<SimulatedProcess> priorityQueue1 = new LinkedList<>();
    private final LinkedList<SimulatedProcess> priorityQueue2 = new LinkedList<>();
    private final LinkedList<SimulatedProcess> priorityQueue3 = new LinkedList<>();
    private final LinkedList<SimulatedProcess> priorityQueue4 = new LinkedList<>();
    private final Map<Integer, List<SimulatedProcess>> finished = new HashMap<>();
    private boolean shouldStop = false;

    public HPFNonpreemptive() {
        finished.put(1, new ArrayList<>());
        finished.put(2, new ArrayList<>());
        finished.put(3, new ArrayList<>());
        finished.put(4, new ArrayList<>());
    }

    /**
     * Add process to corresponding queue based on priority property
     */
    @Override
    public void addProcess(SimulatedProcess proc) {
        getPriorityQueueFromProcess(proc).add(proc);
    }

    private LinkedList<SimulatedProcess> getPriorityQueueFromProcess(SimulatedProcess proc) {
        switch (proc.getPriority()) {
            case 1:
                return priorityQueue1;
            case 2:
                return priorityQueue2;
            case 3:
                return priorityQueue3;
            case 4:
                return priorityQueue4;
        }
        return null;
    }

    private SimulatedProcess getJob() {
        if (!priorityQueue1.isEmpty()) {
            return priorityQueue1.peek();
        } else if (!priorityQueue2.isEmpty()) {
            return priorityQueue2.peek();
        } else if (!priorityQueue3.isEmpty()) {
            return priorityQueue3.peek();
        }
        return priorityQueue4.peek();
    }

    /**
     * Runs queues in order of priority
     */
    @Override
    public void executing(float time) {
        if (shouldStop || isEmpty())
            return;
        SimulatedProcess proc = getJob();
        proc.executing(time);
        System.out.print(proc.getName());
        // Loop through each queue that is not the currently running process
        priorityQueue1.stream().filter(p -> p != proc).forEach(p -> p.waiting());
        priorityQueue2.stream().filter(p -> p != proc).forEach(p -> p.waiting());
        priorityQueue3.stream().filter(p -> p != proc).forEach(p -> p.waiting());
        priorityQueue4.stream().filter(p -> p != proc).forEach(p -> p.waiting());
        if (proc.isFinished()) {
            List<SimulatedProcess> finish = finished.get(proc.getPriority());
            finish.add(proc); // Adds to finished list
            getPriorityQueueFromProcess(proc).remove(proc); // Remove from ready queue
            if (time >= CPUScheduler.QUANTA_TO_RUN - 1.0) {
                shouldStop = true;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return priorityQueue1.isEmpty() && priorityQueue2.isEmpty()
                && priorityQueue3.isEmpty() && priorityQueue4.isEmpty();
    }

    @Override
    public void reset() {
        priorityQueue1.clear();
        priorityQueue2.clear();
        priorityQueue3.clear();
        priorityQueue4.clear();
        finished.get(1).clear();
        finished.get(2).clear();
        finished.get(3).clear();
        finished.get(4).clear();
        shouldStop = false;
    }

    @Override
    public Collection<SimulatedProcess> getFinishedProcesses() {
        Stream<SimulatedProcess> joined = finished.values().stream().flatMap(List::stream);
 // Joined stream of all queues
        List<SimulatedProcess> list = joined.collect(Collectors.toList());
        return Collections.unmodifiableCollection(list);
    }

    public Map getPriorityQueues() {
        return finished;
    }

    @Override
    public boolean shouldStop() {
        return shouldStop;
    }

}
