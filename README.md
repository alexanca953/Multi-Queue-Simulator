# Multi-Queue Simulator

A Java-based simulation application designed to model and visualize a multi-server queuing system, where clients (tasks) are dispatched to multiple queues (servers) based on configurable strategies. The system utilizes a clear architectural separation and Java's concurrency utilities.

## Project Structure and Architecture

The application employs a layered architecture with a clear separation of concerns:

| Package | Description | Key Components |
| :--- | :--- | :--- |
| **Model** | Contains core entities and server logic. | `Task`, `Server` |
| **BusinessLogic** | Implements the simulation, scheduling logic, and the Strategy Pattern. | `SimulationManager`, `Scheduler`, `Strategy` |
| **GUI** | Provides the graphical user interface for configuration and visualization. | `SimulationFrame` |

### Concurrency and Threading
* **Server Threads:** Each queue (`Server`) runs on its **own dedicated thread** to allow for simultaneous task processing, simulating a real-world multi-server environment.
* **BlockingQueue:** The tasks within each server are managed by a thread-safe `LinkedBlockingQueue`, which simplifies concurrent access and queue management.
* **AtomicInteger:** The `waitingPeriod` of each server is tracked using an `AtomicInteger`, ensuring atomic and thread-safe updates to the total waiting time.

### Design Patterns
* **Strategy Pattern:** Used by the `Scheduler` to dynamically select the method for dispatching incoming clients (`Task` objects) to the servers. The available policies are **Shortest Queue** and **Shortest Time**.

## Key Features

### Core Simulation
* **Task Generation:** Generates clients (`Task` objects) with random arrival and service times within ranges defined by the user.
* **Scheduling Strategies:** Clients are dispatched based on two selectable policies: Shortest Queue or Shortest Time.
* **Event Logging:** Simulation status (waiting clients and queue contents) is logged to the console and written to output files (`fisierN.txt`) at every time step.

### User Interface & Visualization
* **Configuration:** A Swing GUI (`SimulationFrame`) allows the user to set key simulation parameters (number of clients, number of queues, time limits, and time ranges).
* **Real-time Animation:** A dedicated animation window dynamically visualizes the queues and the tasks waiting within them:
    * Shows the list of waiting clients (not yet dispatched).
    * Renders each server/queue and the clients currently waiting, displaying their ID, arrival time, and remaining service time.

## Getting Started

### Running the Application
1.  **Execute the main method:** Run the `main` method in the `SimulationManager.java` class.
2.  **Configure:** Enter the required parameters in the configuration window.
3.  **Start:** Click the "Start Simulation" button to begin the simulation and launch the animation window.
