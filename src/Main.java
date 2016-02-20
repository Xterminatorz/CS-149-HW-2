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
        CPUScheduler sched = new CPUScheduler(new RoundRobin());
        sched.generateProcesses();
        sched.start();
    }

}
