import java.util.ArrayList;

public class Epic extends Task {
    String name;
    int EpicTaskId;
    String description;
    String status;
    ArrayList<Integer> subTaskId = new ArrayList<>();
    ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic (String name, String description, ArrayList<SubTask> subTasks) {
        Manager manager = new Manager();
        this.EpicTaskId = manager.generateTaskId();
        this.name = name;
        this.description = description;
        this.status = "NEW";
        this.subTaskId = getSubTaskIdInternal(subTasks);
        this.subTasks = subTasks;
    }

    public Epic () {
    }

    public ArrayList<Integer> getSubTaskIdInternal(ArrayList<SubTask> subTasks) {
        ArrayList<Integer> subTaskId = new ArrayList<>();
        for (SubTask subTask: subTasks) {
            subTaskId.add(subTask.getTaskId());
        }
        return subTaskId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getEpicTaskId() {
        return EpicTaskId;
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
                "EpicTaskId=" + EpicTaskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", subTaskId=" + subTaskId +
                '}';
    }

    public ArrayList<Integer> getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(ArrayList<Integer> subTaskId) {
        this.subTaskId = subTaskId;
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }
}

