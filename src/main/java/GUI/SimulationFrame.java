package GUI;

import Model.Server;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimulationFrame
{
    private JFrame frame;
    private JFrame animationFrame;
    private JTextField clientsField = new JTextField("");
    private JTextField queuesField = new JTextField("");
    private JTextField simTimeField = new JTextField("");
    private JTextField arrivalMinField = new JTextField("");
    private JTextField arrivalMaxField = new JTextField("");
    private JTextField serviceMinField = new JTextField("");
    private JTextField serviceMaxField = new JTextField("");
    private JButton startButton = new JButton("Start Simulation");
    private JRadioButton timeStrategyRadio = new JRadioButton("Time Strategy", true);
    private JRadioButton shortestQueueRadio = new JRadioButton("Shortest Queue Strategy");
private JPanel animationPanel;
private JPanel waitingPanel;
    public SimulationFrame()
    {
        initialize();
    }

    private void initialize()
    {
        frame = new JFrame("Queue Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1400);
        frame.setLayout(new GridLayout(0, 2, 5, 5));

        frame.add(new JLabel("Number of Clients (N):"));
        frame.add(clientsField);

        frame.add(new JLabel("Number of Queues (Q):"));
        frame.add(queuesField);

        frame.add(new JLabel("Simulation Time (MAX):"));
        frame.add(simTimeField);

        frame.add(new JLabel("Arrival Time MIN:"));
        frame.add(arrivalMinField);

        frame.add(new JLabel("Arrival Time MAX:"));
        frame.add(arrivalMaxField);

        frame.add(new JLabel("Service Time MIN:"));
        frame.add(serviceMinField);

        frame.add(new JLabel("Service Time MAX:"));
        frame.add(serviceMaxField);

        /// Strategy selection
        JPanel strategyPanel = new JPanel(new GridLayout(1, 2));
        ButtonGroup strategyGroup = new ButtonGroup();
        strategyGroup.add(timeStrategyRadio);
        strategyGroup.add(shortestQueueRadio);
        strategyPanel.add(timeStrategyRadio);
        strategyPanel.add(shortestQueueRadio);

        frame.add(new JLabel("Strategy:"));
        frame.add(strategyPanel);

        /// Start button
        frame.add(new JLabel());
        frame.add(startButton);

        frame.pack();
        frame.setVisible(true);


    }

    public void animationQueues(ArrayList<Server> servers, int numberOfServers,ArrayList <Task> tasks,int currentTime)
    {
        if (animationFrame == null)
        {
            animationFrame = new JFrame("Queue Animation");
            animationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            animationFrame.setSize(1000, 500);
            animationPanel = new JPanel();
            animationPanel.setLayout(new GridLayout(1, numberOfServers, 10, 10));
            animationFrame.add(animationPanel);
            animationFrame.setLayout(new BorderLayout());
            animationFrame.add(animationPanel, BorderLayout.CENTER);
            waitingPanel = new JPanel();
            waitingPanel.setLayout(new BorderLayout());
            animationFrame.add(waitingPanel, BorderLayout.SOUTH);
            animationFrame.setVisible(true);
            ///pt waiting
      //  JPanel
          //  waitingPanel = new JPanel();
          //  waitingPanel.setLayout(new BorderLayout());
         //   animationFrame.add(waitingPanel);
        }
        waitingPanel.removeAll();
        animationPanel.removeAll();
        int qNr=0;
        for (Server server : servers) {
            JPanel queuePanel = new JPanel() {
                @Override
                public void paintComponent(Graphics g)
                {
                    super.paintComponent(g);
                    g.setColor(Color.BLUE);
                    int y = 10;
                    for (Task task : server.getTasks()) {
                        g.fillRect(15, y, 50, 50);
                        g.setColor(Color.WHITE);
                        g.drawString("C:" + task.getId()+" "+task.getArrivalTime()+" "+task.getServiceTime(), 15, y + 15);
                        g.setColor(Color.CYAN);
                        y += 30;
                    }
                }
            };
            queuePanel.setPreferredSize(new Dimension(100, 200));
            queuePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            queuePanel.setLayout(new BorderLayout());
            animationPanel.add(queuePanel);
            queuePanel.add(new TextField("QUEUE"+qNr+" T="+currentTime), BorderLayout.SOUTH);

            qNr++;
        }
        animationPanel.revalidate();
        animationPanel.repaint();
        JPanel waitingTasksPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.ORANGE);
                int x = 10;
                for (Task task : tasks) {
                    g.fillRect(x, 10, 50, 50);
                    g.setColor(Color.BLACK);
                    g.drawString("C:" + task.getId()+" "+task.getArrivalTime()+" "+task.getServiceTime()+" ", x + 5, 35);
                    g.setColor(Color.ORANGE);
                    x += 60;
                }
            }
        };
        waitingTasksPanel.setPreferredSize(new Dimension(600, 70));
        waitingTasksPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        waitingPanel.add(new JLabel("Waiting Clients:"), BorderLayout.NORTH);
        waitingPanel.add(waitingTasksPanel, BorderLayout.CENTER);

        waitingPanel.revalidate();
        waitingPanel.repaint();
    }
    public int getNumberOfClients() {
        return Integer.parseInt(clientsField.getText());
    }
    public int getNumberOfQueues() {
        return Integer.parseInt(queuesField.getText());
    }
    public int getSimulationTime() {
        return Integer.parseInt(simTimeField.getText());
    }
    public int getArrivalMin() {
        return Integer.parseInt(arrivalMinField.getText());
    }
    public int getArrivalMax() {
        return Integer.parseInt(arrivalMaxField.getText());
    }
    public int getServiceMin() {
        return Integer.parseInt(serviceMinField.getText());
    }
    public int getServiceMax() {
        return Integer.parseInt(serviceMaxField.getText());
    }
    public String getSelectedStrategy() {
        return timeStrategyRadio.isSelected() ? "TIME" : "SHORTEST_QUEUE";
    }
    public JButton getStartButton() {
        return startButton;
    }
    public static void main(String[] args) {
        new SimulationFrame();
    }
    public Component getFrame() {
        return frame;
    }
}