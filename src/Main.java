/*
 * CS 149 Group 2
 * Homework 2
 */

/**
 *
 * @author Johnny
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CPUScheduler sched = new CPUScheduler(new FirstComeFirstServe());
        for (int i = 0; i < 5; i++) {
            sched.generateProcesses();
            sched.start();
            sched.reset();
        }
        sched.calcAvgStats();

        sched = new CPUScheduler(new RoundRobin());
        for (int i = 0; i < 5; i++) {
            sched.generateProcesses();
            sched.start();
            sched.reset();
        }
        sched.calcAvgStats();
    }

}
