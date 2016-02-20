/*
 * CS 149 Group 2
 * Homework 2
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Johnny
 */
public class CPUScheduler {

    private final Scheduler alg;
    private final Timer timer;
    private final List<SimulatedProcess> processes = new ArrayList<>();
    private float currentTime = 0;

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
     * @param alg The algorithm to run
     */
    public CPUScheduler(Scheduler alg) {
        this.alg = alg;
        timer = new Timer();
    }

    /**
     * Generates new processes to put on scheduler
     */
    public void generateProcesses() {
        Random rnd = new Random(0);
        for (int i = 0; i < 50; i++) {
            SimulatedProcess proc = new SimulatedProcess(rnd.nextFloat() * 99, rnd.nextFloat() * 9.9f + 0.1f, rnd.nextInt(4) + 1);
            processes.add(proc);
            System.out.println(proc.toString());
        }
<<<<<<< HEAD
		processes.sort((p1, p2) -> Float.compare(p1.getArrivalTime(), p2.getArrivalTime()));
        
=======
        // Sort by arrival time
        processes.sort((p1, p2) -> Float.compare(p1.getArrivalTime(), p2.getArrivalTime()));
>>>>>>> refs/remotes/origin/master
    }

    /**
     * Starts the scheduler
     */
    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Adds process to scheduler if current time is >= arrival time of process
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
                alg.executing(currentTime); // Run algorithm
                currentTime += TIME_UNIT_QUANTA; // Increase CPU time
                if (processes.isEmpty() && (alg.isEmpty() || alg.shouldStop())) {
                    timer.cancel();
                    calcStats(alg.getFinishedProcesses());
                }
            }
        }, 0, 1);
    }

    private void calcStats(Collection<SimulatedProcess> procs) {
        double turnAround = 0;
        double waiting = 0;
        double response = 0;
        for (SimulatedProcess proc : procs) {
            turnAround += proc.getTurnaroundTime();
            waiting += proc.getWaitingTime();
            response += proc.getResponseTime();
        }
        double avgTA = turnAround / procs.size();
        double avgWait = waiting / procs.size();
        double avgResp = response / procs.size();
        double throughput = procs.size() / QUANTA_TO_RUN;
        System.out.println();
        System.out.println("Average Turnaround: " + avgTA + " Average Wait: " + avgWait + " Average Response Time: " + avgResp + " Throughput: " + throughput);
    }

}
