import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HpfNonpreemptive implements Scheduler
{
   
   private final List<SimulatedProcess> priorityQueue1 = new ArrayList<>();
   private final List<SimulatedProcess> priorityQueue2 = new ArrayList<>();
   private final List<SimulatedProcess> priorityQueue3 = new ArrayList<>();
   private final List<SimulatedProcess> priorityQueue4 = new ArrayList<>();
   private List<List<SimulatedProcess>> queueList = new ArrayList<>(); //0 1 2 3
   
   private final Map<Float, SimulatedProcess> finished = new HashMap<>();
   private boolean shouldStop = false;

   public HpfNonpreemptive()
   {
      queueList.add(priorityQueue1);//get 0 = priority 1
      queueList.add(priorityQueue2);//get 1 = priority 2
      queueList.add(priorityQueue3);//get 2 = priority 3
      queueList.add(priorityQueue4);//get 3 = priority 4
   }
   /**
    * Add process to corresponding queue based on priority property
    */
   public void addProcess(SimulatedProcess proc)
   {
      queueList.get(proc.getPriority() - 1).add(proc);
   }
   

   /**
    * Runs queues in order of priority
    */
   @Override
   public void executing(float time)
   {
      int highestPriorityQueueIndex = -1; //initialize
      if (shouldStop || isEmpty())
         return;
      
      SimulatedProcess proc = new SimulatedProcess(0, 0, -1); //initialize
      for (int i = 0; i < 4; i++)
      {
         if (!queueList.get(i).isEmpty())
         {
            proc = queueList.get(i).get(0);
            highestPriorityQueueIndex = i;
            break;
         }
      }
      proc.executing(time);
      System.out.print(proc.getName());
      for (int i = 0; i < 4; i++)
      {
         int j;
         if (i == highestPriorityQueueIndex)
         {
            j = 1;
         }
         else
         {
            j = 0;
         }
         for (; j < queueList.get(i).size(); j++)
         {
            queueList.get(i).get(j).waiting(); // Increment wait timer on process
         }

      }
      
      if (proc.isFinished())
      {
         finished.put(time, proc); // Adds to finished list
         queueList.get(highestPriorityQueueIndex).remove(0); // Remove from ready queue
         if (time >= CPUScheduler.QUANTA_TO_RUN - 1.0)
         {
            shouldStop = true;
         }
      }
   }

   @Override
   public boolean isEmpty()
   {
      return priorityQueue1.isEmpty() && priorityQueue2.isEmpty()
            && priorityQueue3.isEmpty() && priorityQueue4.isEmpty();
   }

   @Override
   public void reset()
   {
      for (int i = 0; i < 4; i++)
      {
         queueList.get(i).clear();
      }
      finished.clear();
      shouldStop = false;
   }

   @Override
   public Collection<SimulatedProcess> getFinishedProcesses()
   {
      return finished.values();
   }

   @Override
   public boolean shouldStop()
   {
      return shouldStop;
   }

}
