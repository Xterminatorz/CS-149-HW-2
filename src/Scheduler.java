/*
 * CS 149 Group 2
 * Homework 2
 */
import java.util.Collection;

/**
 *
 * @author Johnny
 */
public interface Scheduler {

    void addProcess(SimulatedProcess proc);

    void executing(float time);

    boolean isEmpty();

    Collection<SimulatedProcess> getFinishedProcesses();

    boolean shouldStop();

}
