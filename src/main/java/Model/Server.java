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

                    Task task = tasks.peek();
                    if (task != null) {
                        try {
                            Thread.sleep(1000);
                            waitingPeriod.decrementAndGet();
                            task.setServiceTime(task.getServiceTime() - 1);

                        } catch (InterruptedException e) {
                            task.setServiceTime(task.getServiceTime() + 1);
                            Thread.currentThread().interrupt();
                            return;
                        }


                        if (task.getServiceTime() <= 0) {
                            tasks.poll();

                        }

                    }
//                else {
//                    Thread.sleep(1000); //
//                }

            } catch (Exception e2) {
                e2.printStackTrace();
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