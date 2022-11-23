package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaskManager {
    private static int count = 0;
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final ArrayList<Subtask> subtasks = new ArrayList<>();
    private final ArrayList<Epic> epics = new ArrayList<>();


    private int generateTaskId() {
        count += 1;
        return count;
    }

    private void refreshEpicTaskStatus(Collection<Subtask> subTasks, Epic epic) {
        int countNew = 0;
        int countDone = 0;
        int epicTaskId = epic.getId();
        int size = 0;

        for (Subtask subTask : subTasks) {
            if (subTask.getEpicId() == epicTaskId) {
                if (subTask.getStatus().equalsIgnoreCase("NEW")) {
                    countNew++;
                }
                if (subTask.getStatus().equalsIgnoreCase("DONE")) {
                    countDone++;

                }
                size++;
            }

        }
        if ((subTasks.isEmpty()) || (countNew == size)) {
            epic.setStatus("NEW");
        } else if (countDone == size) {
            epic.setStatus("DONE");
        } else {
            epic.setStatus("IN_PROGRESS");
        }
    }

    public Collection<Task> getTasks() {
        return tasks;
    }
    public Collection<Epic> getEpics() {
        return epics;
    }
    public Collection<Subtask> getSubtasks() {
        return subtasks;
    }

    public Collection<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> newSubtasks = new ArrayList<>();
        for (Subtask subtask: subtasks) {
            if (subtask.getEpicId() == epicId) {
                newSubtasks.add(subtask);
            }
        }
        return newSubtasks;
    }

    public Task getTask(int taskId) {
        Task taskToReturn = null;
        for (Task task: tasks) {
            if (task.getId() == taskId) {
                taskToReturn = task;
            }
        }
        return taskToReturn;
    }
    public Epic getEpic(int epicId) {
        Epic epicToReturn = null;
        for (Epic epic: epics) {
            if (epic.getId() == epicId) {
                epicToReturn = epic;
            }
        }
        return epicToReturn;
    }
    public Subtask getSubtask(int subtaskId) {
        Subtask subtaskToReturn = null;
        for (Subtask subtask: subtasks) {
            if (subtask.getId() == subtaskId) {
                subtaskToReturn = subtask;
            }
        }
        return subtaskToReturn;
    }
    /**
     * Менеджер сам устанавливает id, т. е. в этот метод задача приходит без id
     */
    public void addTask(Task task) {
        task.setId(generateTaskId());
        tasks.add(task);
    }
    /**
     * Менеджер сам устанавливает id, т. е. в этот метод эпик приходит без id
     */
    public void addEpic(Epic epic) {
        epic.setId(generateTaskId());
        epic.setStatus("NEW");
        epics.add(epic);
    }
    /**
     * Менеджер сам устанавливает id, т. е. в этот метод подзадача приходит без id.
     * Менеджер также добавляет подзадачу в эпик (подзадача передается с заполненным epicId).
     */
    public void addSubtask(Subtask subtask) {
        subtask.setId(generateTaskId());
        subtasks.add(subtask);
        for (Epic epic: epics) {
            if (epic.getId() == subtask.getEpicId()) {
                epic.getSubtaskIds().add(subtask.getId());
            }
        }
        Epic epic = getEpic(subtask.getEpicId());
        ArrayList<Subtask> subtaskInternal = new ArrayList<>();
        for (int epicSubtask: epic.getSubtaskIds()) {
            subtaskInternal.add(getSubtask(epicSubtask));
        }
        refreshEpicTaskStatus(subtaskInternal, epic);
    }
    /**
     * updateEpic должен обновлять только название и описание эпика
     */
    public void updateEpic(Epic epic) {
        for (Epic newEpic: epics) {
            if (newEpic.getId() == epic.getId()) {
                newEpic.setName(epic.getName());
                newEpic.setDescription(epic.getDescription());
            }
        }
    }

    public void updateTask(Task task) {
        tasks.set(tasks.indexOf(task), task);
    }
    public void updateSubtask(Subtask subtask) {
        subtasks.set(subtasks.indexOf(subtask), subtask);
        int epicId = subtask.getEpicId();
        Epic epic = getEpic(epicId);
        ArrayList<Subtask> subtaskInternal = new ArrayList<>();
        for (int epicSubtask : epic.getSubtaskIds()) {
            subtaskInternal.add(getSubtask(epicSubtask));
        }
        refreshEpicTaskStatus(subtaskInternal, epic);
    }

    public void deleteTask(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                tasks.remove(task);
                break;
            }
        }
    }
    public void deleteEpic(int epicId) {
        for (Epic epic : epics) {
            if (epic.getId() == epicId) {
                epics.remove(epic);
                break;
            }
        }
        subtasks.removeIf(subtask -> subtask.getEpicId() == epicId);
    }
    public void deleteSubtask(int subtaskId) {
        Epic epic = getEpic(getSubtask(subtaskId).getEpicId());
        for (Subtask subtask : subtasks) {
            if (subtask.getId() == subtaskId) {
                subtasks.remove(subtask);
                epic.getSubtaskIds().remove(Integer.valueOf(subtask.getId()));
                break;
            }
        }

        ArrayList<Subtask> subtaskInternal = new ArrayList<>();
        for (int epicSubtask: epic.getSubtaskIds()) {
            if (getSubtask(epicSubtask) != null) {
                subtaskInternal.add(getSubtask(epicSubtask));
            }
        }
        refreshEpicTaskStatus(subtaskInternal, epic);
    }
    public void deleteTasks() {
        tasks.clear();
    }
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }
    public void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic : epics) {
            epic.getSubtaskIds().removeAll(epic.getSubtaskIds());
            epic.setStatus("NEW");
        }
    }
}
