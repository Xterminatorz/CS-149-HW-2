/*
 * CS 149 Group 2
 * Homework 2
 */
import java.util.LinkedList;

/**
 *
 * @author Johnny
 */
public class FirstComeFirstServe implements ProccessScheduler {

    LinkedList queue = new LinkedList();

    @Override
    public void addProcess(SimulatedProcess proc) {
        queue.add(proc);
    }

    @Override
    public void start() {
        
    }

}
