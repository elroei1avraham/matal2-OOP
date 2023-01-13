package Ex2_2;

import java.util.concurrent.Callable;

/**
 * This Class representing a generic task with a Type that returns a result and may throw an exception.
 * Each task has a priority used for scheduling inferred from the integer value of the task􀍛s Type.
 */
public class Task<T>  implements Callable<T>, Comparable<Task<T>> {


    private Callable task;
    private TaskType taskType;

    /**
     * Constructor for task or prioritized task instances which are potentially executed by another thread.
     * @param other Callable instance
     */
    public Task(Callable other){
        task = other;
        if(other instanceof Task){
            taskType = ((Task<?>) other).getTaskType().getType();
        }
        else{
            taskType = TaskType.OTHER;
        }
    }

    /**
     * Constructor for prioritized task instance which is potentially executed by another thread.
     * @param other Callable instance.
     * @param otherTaskeType The task type and priority by using TaskType enum.
     */
    public Task(Callable other, TaskType otherTaskeType){
        taskType = otherTaskeType;
        task = other;
    }

    /**
     * Factory method for creating prioritized task.
     * @param o Callable instance
     * @param t The task type and priority by using TaskType enum.
     * @return
     */
    public static Callable createTask( Callable o, TaskType t){     //factory method
        return new Task(o,t);
    }

    /**
     * Getter for TaskType data member
     * @return taskType
     */
    public TaskType getTaskType() {
        return taskType;
    }


    /**
     *  Performs the task and returns the result.
     * @return task's result.
     * @throws Exception if exception has been thrown while preforming the task.
     */
    @Override
    public T call() throws Exception {
        if(this.task==null){
            throw new RuntimeException("Task is null");
        }
        return (T) task.call();
    }

    /**
     * compareTo Method to determine natural order relation.
     * @param o the object to be compared.
     * @return natural order.
     */
    @Override
    public int compareTo(Task<T> o) {
        return Integer.compare(taskType.getPriorityValue(),o.getTaskType().getPriorityValue());
    }
}





