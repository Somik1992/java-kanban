package task;


public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask () {
        super();
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", epicTaskId=" + epicId +
                '}';
    }

    public int getEpicTaskId() {
        return epicId;
    }

    public void setEpicTaskId(int epicTaskId) {
        this.epicId = epicTaskId;
    }
}
