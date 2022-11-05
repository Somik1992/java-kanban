package task;


public class Subtask extends Task {
    private int epicTaskId;

    public Subtask(String name, String description) {
        super(name, description);
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
        return "Subtask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", epicTaskId=" + epicTaskId +
                '}';
    }

    public int getEpicTaskId() {
        return epicTaskId;
    }

    public void setEpicTaskId(int epicTaskId) {
        this.epicTaskId = epicTaskId;
    }
}
