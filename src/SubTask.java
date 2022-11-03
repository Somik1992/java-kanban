public class SubTask extends Epic {
    public int taskId;
    public String name;
    public String description;
    public String status;

    SubTask(String name, String description) {
        Manager manager = new Manager();
        this.name = name;
        this.description = description;
        this.taskId = manager.generateTaskId();
        this.status = "NEW";
    }

    public int getTaskId() {
        return taskId;
    }

    @Override
    public String getName() {
        return name;
    }



    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
