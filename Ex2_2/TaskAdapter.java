package Ex2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class TaskAdapter<T> extends FutureTask implements Callable<T>, Comparable<TaskAdapter<T>>{

   private Task task;

//
//    public TaskAdapter(Callable callable) {
//        super(callable);
//        task = new Task (callable);
//        //this.taskType = task.getTaskType().getType();
//        priority = task.getTaskType().getType().getPriorityValue();
//    }

    /**
     * Constructor of new TaskeAdapter object
     * Invokes Task Constructor
     * @param callable Callable instance
     * @param taskType Task type and priority set by using enum TaskType.
     */
    public TaskAdapter(Callable callable, TaskType taskType){
        super(callable);
        task = new Task (callable,taskType);
    }

    /**
     * Constructor of new TaskeAdapter object
     * Invokes Task Constructor
     * @param task receives Callable instances (Task implements Callable Interface)
     */
    public TaskAdapter(Task task){
        super(task);
        this.task = new Task(task,task.getTaskType().getType());
    }

    public TaskType getTaskType() {
        return task.getTaskType().getType();
    }

    @Override
    public T call() throws Exception {
        return (T) task.call();
    }

    @Override
    public int compareTo(TaskAdapter<T> o) {
        return (-1)*Integer.compare(this.task.getTaskType().getType().getPriorityValue(),o.getTaskType().getType().getPriorityValue());
    }
}