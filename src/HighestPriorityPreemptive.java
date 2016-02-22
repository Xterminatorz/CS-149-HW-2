import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HighestPriorityPreemptive implements Scheduler {

	private final ArrayList<PriorityQueue<SimulatedProcess>> readyQueue;
	private final PriorityQueue<SimulatedProcess> priorityQueue1;
	private final PriorityQueue<SimulatedProcess> priorityQueue2;
	private final PriorityQueue<SimulatedProcess> priorityQueue3;
	private final PriorityQueue<SimulatedProcess> priorityQueue4;
	private final Map<Float, SimulatedProcess> finished;
    private boolean shouldStop;
    
	public HighestPriorityPreemptive(){
		readyQueue = new ArrayList<>();	
		priorityQueue1 = new PriorityQueue();
		priorityQueue2 = new PriorityQueue();
		priorityQueue3 = new PriorityQueue();
		priorityQueue4 = new PriorityQueue();
		readyQueue.add(priorityQueue1);
		readyQueue.add(priorityQueue2);
		readyQueue.add(priorityQueue3);
		readyQueue.add(priorityQueue4);
		
		finished = new HashMap<>();
		shouldStop = false;
	}
	
	@Override
	public void addProcess(SimulatedProcess proc) {
		// TODO Auto-generated method stub
		readyQueue.get(proc.getPriority()-1).add(proc);
		
	}

	@Override
	public void executing(float time) {
		// TODO Auto-generated method stub
		if(shouldStop || isEmpty())
			return;
		for(int i = 0; i < 4; i++)
		{
			if(readyQueue.get(i).isEmpty())
				continue;
			for(SimulatedProcess process: readyQueue.get(i)){
				process.executing(time);
	        	System.out.print(process.getName());
	        	for (int j = 1; j < readyQueue.get(i).size(); j++) {
	                readyQueue.get(i).peek().waiting();
	            }
	            if (process.isFinished()) {
	                finished.put(time, process);
	                readyQueue.get(i).remove(process);
	                if (time >= CPUScheduler.QUANTA_TO_RUN - 1.0) {
	                    shouldStop = true;
	                }
	            } else {
	                readyQueue.get(i).offer(readyQueue.get(i).poll());
	            }
			}
		}
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return priorityQueue1.isEmpty() && priorityQueue2.isEmpty()
	            && priorityQueue3.isEmpty() && priorityQueue4.isEmpty();
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		for(List<SimulatedProcess> clearList: readyQueue)
			clearList.clear();
		readyQueue.clear();
		shouldStop = false;
	}

	@Override
	public Collection<SimulatedProcess> getFinishedProcesses() {
		// TODO Auto-generated method stub
		return finished.values();
	}

	@Override
	public boolean shouldStop() {
		// TODO Auto-generated method stub
		return shouldStop;
	}

}
