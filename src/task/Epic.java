package task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic (String name, String description) {
        super(name, description);

    }

    public Epic () {
        super();
    }

    public ArrayList<Integer> getSubTaskIdInternal(ArrayList<Subtask> subTasks) {
        ArrayList<Integer> subTaskId = new ArrayList<>();
        for (Subtask subTask: subTasks) {
            subTaskId.add(subTask.getId());
        }
        return subTaskId;
    }

    public void setSubtaskIds(ArrayList<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", subtaskIds=" + subtaskIds +
                '}';
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }
}

