public class SubTask extends Epic {
    public int taskId;
    public String name;
    public String description;
    public String status;

    SubTask(String name, String description, Epic epic) {
        Manager manager = new Manager();
        this.name = name;
        this.description = description;
        this.taskId = manager.generateTaskId();
        this.EpicTaskId = epic.getEpicTaskId();
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
    public String toString() {
        return "SubT{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", EpicTaskId=" + EpicTaskId +
                '}';
    }
}
