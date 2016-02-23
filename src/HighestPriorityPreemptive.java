import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HighestPriorityPreemptive implements Scheduler {

	private final List<List<SimulatedProcess>> readyQueue;
	private final List<SimulatedProcess> priorityQueue1;
	private final List<SimulatedProcess> priorityQueue2;
	private final List<SimulatedProcess> priorityQueue3;
	private final List<SimulatedProcess> priorityQueue4;
	private final Map<Float, SimulatedProcess> finished;
	private final Map<Float, SimulatedProcess> pQueue1Finished;
	private final Map<Float, SimulatedProcess> pQueue2Finished;
	private final Map<Float, SimulatedProcess> pQueue3Finished;
	private final Map<Float, SimulatedProcess> pQueue4Finished;
    private boolean shouldStop;
    
	public HighestPriorityPreemptive(){
		readyQueue = new ArrayList();	
		priorityQueue1 = new ArrayList();
		priorityQueue2 = new ArrayList();
		priorityQueue3 = new ArrayList();
		priorityQueue4 = new ArrayList();
		readyQueue.add(priorityQueue1);
		readyQueue.add(priorityQueue2);
		readyQueue.add(priorityQueue3);
		readyQueue.add(priorityQueue4);
		
		finished = new HashMap<>();
		pQueue1Finished = new HashMap<>();
		pQueue2Finished = new HashMap<>();
		pQueue3Finished = new HashMap<>();
		pQueue4Finished = new HashMap<>();
		
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
	                readyQueue.get(i).get(j).waiting();
	            }
	            if (process.isFinished()) {
	                finished.put(time, process);
	                if(i == 0)
	                	pQueue1Finished.put(time, process);
	                else if(i== 1)
	                	pQueue2Finished.put(time, process);
	                else if(i== 2)
	                	pQueue3Finished.put(time, process);
	                else {
	                	pQueue4Finished.put(time, process);
					}
	                readyQueue.get(i).remove(process);
	                if (time >= CPUScheduler.QUANTA_TO_RUN - 1.0) {
	                    shouldStop = true;
	                }
	            } else {
	            	
	                readyQueue.get(i).add(readyQueue.get(i).remove(0));
	              
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
