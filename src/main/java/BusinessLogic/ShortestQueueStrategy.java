package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy  {
    public void addTask(List<Server> servers, Task task)
    {
        int poz=0;
        int min=servers.get(0).getTasks().length;
        int i=0;
        for(Server server : servers)
        {
            if(server.getTasks().length<min)
            {
                min=server.getTasks().length;
                poz=i;
            }i++;
        }
        servers.get(poz).addTask(task);
    }
}