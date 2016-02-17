/*
 * CS 149 Group 2
 * Homework 2
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Johnny
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int pCount = 0;
        Random rnd = new Random(0);
        List<SimulatedProcess> processes = new ArrayList<>();

        for (int i = 0; i < 99; i++) {
            processes.add(new SimulatedProcess("P" + pCount++, rnd.nextFloat() * 99, rnd.nextFloat() * 9.9f + 0.1f, rnd.nextInt(4) + 1));
        }

        ProccessScheduler ps = new FirstComeFirstServe();
        processes.sort((p1, p2) -> Float.compare(p1.getArrivalTime(), p2.getArrivalTime()));
        processes.stream().forEach((p) -> {
            ps.addProcess(p);
        });

    }

}
