package task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds = new ArrayList<>();
    private ArrayList<Subtask> subTasks;

    public Epic (String name, String description, ArrayList<Subtask> subTasks) {
        super(name, description);
        this.subTasks = subTasks;

    }

    public ArrayList<Integer> getSubTaskIdInternal(ArrayList<Subtask> subTasks) {
        ArrayList<Integer> subTaskId = new ArrayList<>();
        for (Subtask subTask: subTasks) {
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

    public int getTaskId() {
        return taskId;
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


    public void setSubtaskIds(ArrayList<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", subtaskIds=" + subtaskIds +
                '}';
    }

    public ArrayList<Subtask> getSubTasks() {
        return subTasks;
    }

}

