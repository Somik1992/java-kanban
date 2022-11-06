package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaskManager {
    private static int count = 0;
    private final List<Task> tasks = new ArrayList<>();
    private final List<Subtask> subtasks = new ArrayList<>();
    private final List<Epic> epics = new ArrayList<>();


    public int generateTaskId() {
        count += 1;
        return count;
    }


    public Object searchTaskByTaskId (int number) {
        Object object = null;
        if (tasks.get(number) != null) {
            object = tasks.get(number);
        } else if (epics.get(number) != null) {
            object = epics.get(number);
        } else if (subtasks.get(number) != null) {
            object = subtasks.get(number);
        }
        return object;
    }


    public void refreshEpicTaskStatus(Collection<Subtask> subTasks, Epic epic) {
        int countNew = 0;
        int countDone = 0;
        int epicTaskId = epic.getId();
        int size = 0;

        for (Subtask subTask : subTasks) {
            if (subTask.getEpicTaskId() == epicTaskId) {
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
            if (subtask.getEpicTaskId() == epicId) {
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
        epics.add(epic);
    }
    /**
//     * Менеджер сам устанавливает id, т. е. в этот метод подзадача приходит без id.
//     * Менеджер также добавляет подзадачу в эпик (подзадача передается с заполненным epicId).
//     */
    public void addSubtask(Subtask subtask) {
        subtask.setId(generateTaskId());
        subtasks.add(subtask);
        for (Epic epic: epics) {
            if (epic.getId() == subtask.getEpicTaskId()) {
                epic.getSubtaskIds().add(subtask.getId());
            }
        }
    }
    public void updateTask(Task task) {
        for (Task newTask: tasks) {
            if (newTask.getId() == task.getId()) {
                newTask = task;
            }
        }
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
    public void updateSubtask(Subtask subtask) {
        int epicId = subtask.getEpicTaskId();
        for (Subtask newSubtask: subtasks) {
            if (newSubtask.getId() == subtask.getId()) {
                newSubtask = subtask;
            }
        }
        Epic epic = getEpic(epicId);
        ArrayList<Subtask> subtaskInternal = new ArrayList<>();
        for (int epicSubtask: epic.getSubtaskIds()) {
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
    }
    public void deleteSubtask(int subtaskId) {
        for (Subtask subtask : subtasks) {
            if (subtask.getId() == subtaskId) {
                subtasks.remove(subtask);
                break;
            }
        }
    }
    public void deleteTasks() {
        tasks.clear();
    }
    public void deleteEpics() {
        epics.clear();
    }
    public void deleteSubtasks() {
        subtasks.clear();
    }
}
