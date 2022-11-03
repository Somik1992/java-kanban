public class Task {

    public int taskId;
    public String name;
    public String description;
    public String status;

    public Task (String name, String description) {
        Manager manager = new Manager();
        this.name = name;
        this.taskId = manager.generateTaskId();
        this.description = description;
        this.status = "NEW";
    }

    public Task() {
    }

    public int getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
