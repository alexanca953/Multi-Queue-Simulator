package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class TimeStrategy implements Strategy {
    public
    void addTask(List<Server> servers, Task task)
    {
        int poz=0;
        int min=servers.get(0).getWaitingPeriod();
        int i=0;
        for(Server server : servers)
        {
            if(server.getWaitingPeriod()<min)
            {
                min=server.getWaitingPeriod();
                poz=i;
            }i++;
        }
        servers.get(poz).addTask(task);
    }
}