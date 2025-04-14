package Model;

public class Task implements Comparable{
    private int id;
    private int arrivalTime;
    private int serviceTime;
    public Task(int ID, int arrivalTime, int serviceTime) {
        this.id = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public int getServiceTime() {
        return serviceTime;
    }
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
    public String toString() {
        return "(" + id + "," + arrivalTime + "," + serviceTime + ")";
    }

    @Override
    public int compareTo(Object o) {
        Task other = (Task) o;
        return Integer.compare(this.arrivalTime, other.arrivalTime);
    }
}