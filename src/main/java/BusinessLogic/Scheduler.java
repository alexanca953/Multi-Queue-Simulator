package BusinessLogic;
import Model.Server;
import Model.Task;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    Strategy strategy;
    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        servers = new ArrayList<>();
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
         for(int i = 0; i < maxNoServers; i++)
         {
             Server server = new Server();
             servers.add(server);
             Thread thread = new Thread(server);
             thread.start();
         }
    }
     public void changeStrategy(SelectionPolicy policy)
     {
if(policy==SelectionPolicy.SHORTEST_QUEUE){
    strategy=new ShortestQueueStrategy();
}
if(policy==SelectionPolicy.SHORTEST_TIME)
{
    strategy=new TimeStrategy();
}
     }

    public void dispatchTask(Task task) {
     strategy.addTask(servers,task);
    }
    public List<Server> getServers() {
        return servers;
    }
}