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
        System.out.println("First Come First Serve");
        CPUScheduler sched = new CPUScheduler(new FirstComeFirstServe());
        for (int i = 0; i < 5; i++) {
            sched.generateProcesses();
            sched.start(); //whole process execution process, calls the algorithm's execution to handle the processes
            sched.reset();
            System.out.println();
        }
        sched.calcAvgStats();

        System.out.println("\n\nRound Robin");
        sched = new CPUScheduler(new RoundRobin());
        for (int i = 0; i < 5; i++) {
            sched.generateProcesses();
            sched.start();
            sched.reset();
            System.out.println();
        }
        sched.calcAvgStats();

        System.out.println("\n\nShortest Remaining Time");
        sched = new CPUScheduler(new ShortestRemainingTime());
        for (int i = 0; i < 5; i++) {
            sched.generateProcesses();
            sched.start();
            sched.reset();
            System.out.println();
        }
        sched.calcAvgStats();

        System.out.println("\n\nHighest Priority First (Nonpreemptive)");
        sched = new CPUScheduler(new HPFNonpreemptive());
        for (int i = 0; i < 5; i++) {
            sched.generateProcesses();
            sched.start();
            sched.reset();
            System.out.println();
        }
        sched.calcAvgStats();

        System.out.println("\n\nHighest Priority First (Preemptive)");
        sched = new CPUScheduler(new HighestPriorityPreemptive());
        for (int i = 0; i < 5; i++) {
            sched.generateProcesses();
            sched.start();
            sched.reset();
            System.out.println();
        }
        sched.calcAvgStats();
    }

}
