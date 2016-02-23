/*
 * CS 149 Group 2
 * Homework 2
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Johnny
 */
public class CPUScheduler {

    private final Scheduler alg;
    private final List<SimulatedProcess> processes = new ArrayList<>(); // full list of processes
    private float currentTime = 0;
    private final List<Statistics> stats = new ArrayList<>();
    private final Random R = new Random(0);

    /**
     * A time unit
     */
    public static final double TIME_UNIT_QUANTA = 1.0;

    /**
     * Amount of quanta to run
     */
    public static final double QUANTA_TO_RUN = 100.0;

    /**
     * Sets up a new CPU scheduler with the given algorithm
     *
     * @param alg The algorithm to run
     */
    public CPUScheduler(Scheduler alg) {
        this.alg = alg;
    }

    /**
     * Resets CPU state
     */
    public void reset() {
        processes.clear();
        alg.reset();
        currentTime = 0;
    }

    /**
     * Generates new processes to put on scheduler
     */
    public void generateProcesses() {
        for (int i = 0; i < 50; i++) {
            SimulatedProcess proc = new SimulatedProcess(R.nextFloat() * 99, R.nextFloat() * 9.9f + 0.1f, R.nextInt(4) + 1);
            processes.add(proc);
        }
        // Sort by arrival time
        processes.sort((p1, p2) -> Float.compare(p1.getArrivalTime(), p2.getArrivalTime()));
    }

    /**
     * Starts the scheduler
     */
    public void start() {
        boolean stop = false;
        while (!stop) {
            // Adds all processes to scheduler if current time is >= arrival time of process
            Iterator<SimulatedProcess> it = processes.iterator();
            while (it.hasNext()) {
                SimulatedProcess proc = it.next();
                if (currentTime >= proc.getArrivalTime()) {
                    alg.addProcess(proc);
                    it.remove();
                } else {
                    break;
                }
            }
            alg.executing(currentTime); // Run algorithm, one process
            currentTime += TIME_UNIT_QUANTA; // Increase CPU time
            if (processes.isEmpty() && (alg.isEmpty() || alg.shouldStop())) {
                stop = true;
                calcStats(alg.getFinishedProcesses());
            }
        }
    }

    private void calcStats(Collection<SimulatedProcess> procs) {
        double turnAround = 0;
        double waiting = 0;
        double response = 0;
        System.out.println();
        for (SimulatedProcess proc : procs) {
            System.out.println(proc);
            turnAround += proc.getTurnaroundTime();
            waiting += proc.getWaitingTime();
            response += proc.getResponseTime();
        }
        double avgTA = turnAround / procs.size();
        double avgWait = waiting / procs.size();
        double avgResp = response / procs.size();
        double throughput = procs.size() / currentTime;
        System.out.println("Average Turnaround: " + avgTA + " Average Wait: " + avgWait + " Average Response Time: " + avgResp + " Throughput: " + throughput);
        stats.add(new Statistics(avgTA, avgWait, avgResp, throughput));
        if (alg instanceof HPFNonpreemptive || alg instanceof HighestPriorityPreemptive) {
            Map<Integer, List<SimulatedProcess>> priorityQueues = alg.getFinishedPriorityQueues();
            priorityQueues.keySet().stream().forEach((p) -> {
                List<SimulatedProcess> l = priorityQueues.get(p);
                double pAvgTA = 0;
                double pAvgWait = 0;
                double pAvgResp = 0;
                for (SimulatedProcess proc : l) {
                    pAvgTA += proc.getTurnaroundTime();
                    pAvgWait += proc.getWaitingTime();
                    pAvgResp += proc.getResponseTime();
                }
                System.out.println("Priority Queue " + p + ": Average Turnaround: " + (l.isEmpty() ? 0 : pAvgTA / l.size()) + " Average Wait: " + (l.isEmpty() ? 0 : pAvgWait / l.size()) + " Average Response Time: " + (l.isEmpty() ? 0 : pAvgResp / l.size()) + " Throughput: " + l.size() / QUANTA_TO_RUN);
            });
        }
    }

    public void calcAvgStats() {
        double turnAround = 0;
        double waiting = 0;
        double response = 0;
        double throughput = 0;
        for (Statistics stat : stats) {
            turnAround += stat.turnAround;
            waiting += stat.waiting;
            response += stat.response;
            throughput += stat.throughput;
        }
        double avgTA = turnAround / stats.size();
        double avgWait = waiting / stats.size();
        double avgResp = response / stats.size();
        double avgThroughput = throughput / stats.size();
        System.out.println("ALG: Average Turnaround: " + avgTA + " Average Wait: " + avgWait + " Average Response Time: " + avgResp + " Throughput: " + avgThroughput);
    }

    class Statistics {

        double turnAround = 0;
        double waiting = 0;
        double response = 0;
        double throughput = 0;

        public Statistics(double ta, double w, double r, double t) {
            this.turnAround = ta;
            this.waiting = w;
            this.response = r;
            this.throughput = t;
        }

    }

}
