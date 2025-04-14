package Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private  BlockingQueue<Task> tasks;
    private  AtomicInteger waitingPeriod;
    public Server()
    {
        tasks = new LinkedBlockingQueue<Task>();
        waitingPeriod = new AtomicInteger(0);
    }
    public void addTask(Task newTask)
    {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }
    @Override
    public void run() {
        while (true) {
            try {
                synchronized(this) {
                Task task = tasks.peek();
                if (task != null)
                { Thread.sleep(1000);
                    task.setServiceTime(task.getServiceTime() - 1);
                    waitingPeriod.decrementAndGet();
                    if (task.getServiceTime() <= 0)
                    {
                        tasks.poll();
                    }

                }}
//                else {
//                    Thread.sleep(1000); //
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public Task[] getTasks()
    {
        return tasks.toArray(new Task[tasks.size()]);
    }
    public int getWaitingPeriod()
    {
        return waitingPeriod.get();
    }
    public String toString()
    {
        return tasks.toString();
    }
}