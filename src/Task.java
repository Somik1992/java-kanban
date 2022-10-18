public class Task {
    private static int count = 0;
    public int taskId;
    public String name;
    public String description;
    public String status;

    Task (String name, String description) {
        this.taskId = generateTaskId();
        this.name = name;
        this.description = description;
        this.status = "NEW";
    }

    private int generateTaskId () {
        count += 1;
        return this.taskId += count;
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
