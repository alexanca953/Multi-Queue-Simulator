package BusinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationManager implements Runnable {
    public int timeLimit=60;
    public int maxProcessingTime=30;
    public int minProcessingTime=2;
    public int maxArrivalTime=30;
    public int minArrivalTime=2;
    public int numberOfServers =2;
    public int numberOfClients=4;
    Scheduler scheduler;
    SimulationFrame frame;
    List<Task> tasks;
    SelectionPolicy selectionPolicy=SelectionPolicy.SHORTEST_TIME;
    public SimulationManager()
    {
 scheduler=new Scheduler(numberOfServers,numberOfClients);
 scheduler.changeStrategy(selectionPolicy);
frame=new SimulationFrame();
tasks=new ArrayList<Task>();
tasks=generateRandomTasks();

    }
    public ArrayList<Task> generateRandomTasks() {
       ArrayList<Task> tasks=new ArrayList<>();
       int arr;
       int procTime;
        Random rand=new Random();
        for(int i=0;i<numberOfClients;i++)
        {

            arr=rand.ints(minArrivalTime, maxArrivalTime + 1).findFirst().getAsInt();
         ///  arr= rand.nextInt(timeLimit+1);
          /// procTime= rand.ints(minProcessingTime,maxProcessingTime+1);
        procTime=rand.ints(minProcessingTime, maxProcessingTime + 1).findFirst().getAsInt();
            tasks.add(new Task(i+1,arr,procTime));
        }

        Collections.sort(tasks);
       /// System.out.println(tasks);
        return tasks;
    }
    public void run()
    {
        int currentTime = 0;
        int verif=0;
        while (currentTime <=timeLimit) {
            Iterator<Task> iterator = tasks.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.getArrivalTime() == currentTime)
                {
                    scheduler.dispatchTask(task);
                    iterator.remove();
                }
            }
            System.out.print("Waiting clients:");
            for(Task task: tasks) {
                System.out.print(task.toString() + " ");
            }
            System.out.println();
            System.out.println("Queues at time"+currentTime+":");
             verif=1;
            for(int i = 1; i<=this.numberOfServers; i++)
            {
                System.out.println( "Queeue"+i+":"+this.scheduler.servers.get(i-1).toString());
                if(this.scheduler.servers.get(i-1).getWaitingPeriod()!=0)
                {
                    verif=0;
                }
            }

            ///punem in fisier
        String fisier="fisier";
        if(numberOfServers ==2&&numberOfClients==4)
        {
            fisier="fisier1.txt";
        }
            else if(numberOfServers ==5&&numberOfClients==50) {
            fisier = "fisier2.txt";
        }
           else if(numberOfServers ==20&&numberOfClients==10000)
            {
                fisier="fisier3.txt";
            }
           if(currentTime==0)
           {
               try (FileWriter fw = new FileWriter(fisier, false)) {  // `false` = suprascrie fișierul
                   fw.write("");  // Scrie un șir gol (șterge tot)
               } catch (IOException e) {
                   System.err.println("Eroare la ștergerea fișierului: " + e.getMessage());
               }
           }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fisier,true))) {
       writer.write("Waiting clients:");
        for(Task task: tasks) {
            writer.write(task.toString() + " ");
        }
        writer.newLine();
        writer.write("Queues at time "+currentTime+": ");
        writer.newLine();
        for(int i = 1; i<=this.numberOfServers; i++)
        {
            writer.write( "Queue"+i+":"+this.scheduler.servers.get(i-1).toString());
            writer.newLine();
        }

        writer.newLine();
        writer.newLine();
    } catch (IOException e) {
        System.err.println("Eroare: " + e.getMessage());
    }

            int finalCurrentTime = currentTime;
            SwingUtilities.invokeLater(() -> {
               frame.animationQueues((ArrayList<Server>) this.scheduler.servers,numberOfServers, (ArrayList<Task>) tasks, finalCurrentTime);
         });
            boolean queuesEmpty = true;
            for (Server server : scheduler.servers) {
                if (  server.getWaitingPeriod() > 0){
                    queuesEmpty = false;
                    break;
                }
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//           if(verif==1&& tasks.isEmpty())
//               break;



            // Condiția finală de oprire
            if (tasks.isEmpty() && queuesEmpty) {
                System.out.println("SIMULATION ENDED: No waiting clients and all queues empty at time " + currentTime);

                break;
            }
            currentTime++;
        }
    }

    public static void main(String[] args) {
        SimulationManager simulationManager = new SimulationManager();

        simulationManager.frame.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Preluăm toate valorile din frame
                    simulationManager.numberOfClients = simulationManager.frame.getNumberOfClients();
                    simulationManager.numberOfServers = simulationManager.frame.getNumberOfQueues();
                    simulationManager.timeLimit = simulationManager.frame.getSimulationTime();
                    simulationManager.minArrivalTime = simulationManager.frame.getArrivalMin();
                    simulationManager.maxArrivalTime = simulationManager.frame.getArrivalMax();
                    simulationManager.minProcessingTime = simulationManager.frame.getServiceMin();
                    simulationManager.maxProcessingTime = simulationManager.frame.getServiceMax();

                    simulationManager.selectionPolicy = simulationManager.frame.getSelectedStrategy().equals("TIME") ? SelectionPolicy.SHORTEST_TIME : SelectionPolicy.SHORTEST_QUEUE;

                    simulationManager.scheduler = new Scheduler(simulationManager.numberOfServers, simulationManager.numberOfClients);
                    simulationManager.scheduler.changeStrategy(simulationManager.selectionPolicy);
                    simulationManager.tasks = simulationManager.generateRandomTasks();

                    Thread thread = new Thread(simulationManager);
                    thread.start();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(simulationManager.frame.getFrame(),
                            "Please enter valid numbers in all fields",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                catch(IllegalArgumentException ex)
                {
                    JOptionPane.showMessageDialog(simulationManager.frame.getFrame(),
                            "Please enter valid numbers in all fields",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}