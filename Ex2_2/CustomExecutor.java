package Ex2_2;

import java.util.concurrent.*;

import static Ex2_2.TaskType.*;

public class CustomExecutor extends ThreadPoolExecutor   {
    private static int numOfCores = Runtime.getRuntime().availableProcessors();
    private static int corePoolSize = numOfCores/2;
    private static int maximumPoolSize = numOfCores-1;
    private static int SumOfComputational = 0;
    private static int SumOfIo = 0;
    private static int SumOfOther = 0;

    public static int getSumOfComputational() {
        return SumOfComputational;
    }

    public static int getSumOfIo() {
        return SumOfIo;
    }

    public static int getSumOfOther() {
        return SumOfOther;
    }


    private static volatile int currMaxPriority;
    private static PriorityBlockingQueue priorityBlockingQueue;

    /**
     * Constructor
     */
    public CustomExecutor(){
        super(corePoolSize,maximumPoolSize, 300,TimeUnit.MILLISECONDS,
                priorityBlockingQueue = new PriorityBlockingQueue<>());

        currMaxPriority = Integer.MAX_VALUE;
    }

    /**
     * the 3 methods below are providing the User the option to submit
     * A Task instance, Callable instance or Callable instance  with TaskeType to CustomExecutor
     *
     * @param task
     * creating FutureTask object which is TaskAdapter and executing the task by threadPoolExecutor.
     * @return taskAdapter
     * @param <T>
     */
    public <T> Future <T> submit (Task task){
        TaskAdapter<T> taskAdapter = new TaskAdapter<> ((Task) task);
        if(task.getTaskType()==COMPUTATIONAL){
            SumOfComputational++;
        } else if(task.getTaskType()==IO){
            SumOfIo++;
        } else if(task.getTaskType()==OTHER){
            SumOfOther++;
        }


        System.out.println("before execute currMaxPriority: "+getCurrentMax());
        System.out.println("SumOfComputational: "+SumOfComputational);
        System.out.println("SumOfIo: "+SumOfIo);
        System.out.println("SumOfOther: "+SumOfOther);
        System.out.println("peek = "+getQueue().peek());
        execute(taskAdapter);

        System.out.println("SumOfComputational: "+SumOfComputational);
        System.out.println("SumOfIo: "+SumOfIo);
        System.out.println("SumOfOther: "+SumOfOther);

        return taskAdapter;
    }

    public Future submit(Callable callable, TaskType taskType){ //input: Callable, submit Task to Adapter
        Task task = new Task<>(callable,taskType);
        return  submit(task);
    }

    public Future submit(Callable callable){
        Task task = new Task<>(callable);
        return submit(task);
    }

    /**
     * @param r
     * @param t
     * subtract the max priority after the thread execute.
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        int MaxPriority = getCurrentMax();
        takeDownFromSum(MaxPriority);
        System.out.println("after execute currMaxPriority: "+getCurrentMax());
        System.out.println("SumOfComputational: "+SumOfComputational);
        System.out.println("SumOfIo: "+SumOfIo);
        System.out.println("SumOfOther: "+SumOfOther);
        System.out.println("peek = "+getQueue().peek());
        System.out.println("queue size: "+getQueue().size());
    }

    /**
     * @param taskType
     * subtract the taskType priority from the sum of taskType.
     */
    public void takeDownFromSum(int taskType) {
        if (taskType ==  COMPUTATIONAL.getPriorityValue()) {
            SumOfComputational--;
        } else if (taskType ==  IO.getPriorityValue()) {
            SumOfIo--;
        }
        if (taskType ==  OTHER.getPriorityValue()) {
            SumOfOther--;
        }
    }

    /**
     * get the taskType priority from the sum of taskType.
     */
    public int getCurrentMax(){
        if (SumOfComputational != 0){
            return 1;
        } else if (SumOfIo != 0){
            return 2;
        } else if (SumOfOther != 0){
            return 3;
        }
        return Integer.MAX_VALUE;
    }

    /**
     * After finishing of all tasks submitted to the executor, or if an exception is thrown,
     * terminate the executor.
     */
    public void gracefullyTerminate(){
        shutdown();
    }
}












