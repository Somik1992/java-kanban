public class Epic extends Task {
    String name;
    int EpicTaskId;
    String description;
    String status;

    public Epic (String name, String description) {
        Manager manager = new Manager();
        this.EpicTaskId = manager.generateTaskId();
        this.name = name;
        this.description = description;
        this.status = "NEW";
    }

    public Epic () {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getEpicTaskId() {
        return EpicTaskId;
    }

    public void setEpicTaskId(int epicTaskId) {
        this.EpicTaskId = epicTaskId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
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
        return "Epic{" +
                "taskId=" + EpicTaskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

