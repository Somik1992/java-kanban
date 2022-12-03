package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private static int count = 0;
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final ArrayList<Subtask> subtasks = new ArrayList<>();
    private final ArrayList<Epic> epics = new ArrayList<>();
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();


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
                if (subTask.getStatus() == (Status.NEW)) {
                    countNew++;
                }
                if (subTask.getStatus() == (Status.DONE)) {
                    countDone++;

                }
                size++;
            }

        }
        if ((subTasks.isEmpty()) || (countNew == size)) {
            epic.setStatus(Status.NEW);
        } else if (countDone == size) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public Collection<Task> getTasks() {
        return tasks;
    }

    @Override
    public Collection<Epic> getEpics() {
        return epics;
    }

    @Override
    public Collection<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public Collection<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> newSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            if (subtask.getEpicId() == epicId) {
                newSubtasks.add(subtask);
            }
        }
        return newSubtasks;
    }

    @Override
    public Task getTask(int taskId, boolean saveHistory) {
        Task taskToReturn = null;
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                taskToReturn = task;
            }
        }
        if (saveHistory) {
            inMemoryHistoryManager.addTask(taskToReturn);
        }
        return taskToReturn;
    }

    @Override
    public Epic getEpic(int epicId, boolean saveHistory) {
        Epic epicToReturn = null;
        for (Epic epic : epics) {
            if (epic.getId() == epicId) {
                epicToReturn = epic;
            }
        }
        if (saveHistory) {
            inMemoryHistoryManager.addTask(epicToReturn);
        }
        return epicToReturn;
    }

    @Override
    public Subtask getSubtask(int subtaskId, boolean saveHistory) {
        Subtask subtaskToReturn = null;
        for (Subtask subtask : subtasks) {
            if (subtask.getId() == subtaskId) {
                subtaskToReturn = subtask;
            }
        }
        if (saveHistory) {
            inMemoryHistoryManager.addTask(subtaskToReturn);
        }
        return subtaskToReturn;
    }

    /**
     * Менеджер сам устанавливает id, т. е. в этот метод задача приходит без id
     */
    @Override
    public void addTask(Task task) {
        task.setId(generateTaskId());
        tasks.add(task);
    }

    /**
     * Менеджер сам устанавливает id, т. е. в этот метод эпик приходит без id
     */
    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateTaskId());
        epic.setStatus(Status.NEW);
        epics.add(epic);
    }

    /**
     * Менеджер сам устанавливает id, т. е. в этот метод подзадача приходит без id.
     * Менеджер также добавляет подзадачу в эпик (подзадача передается с заполненным epicId).
     */
    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(generateTaskId());
        subtasks.add(subtask);
        for (Epic epic : epics) {
            if (epic.getId() == subtask.getEpicId()) {
                epic.getSubtaskIds().add(subtask.getId());
            }
        }
        Epic epic = getEpic(subtask.getEpicId(), false);
        ArrayList<Subtask> subtaskInternal = new ArrayList<>();
        for (int epicSubtask : epic.getSubtaskIds()) {
            subtaskInternal.add(getSubtask(epicSubtask, false));
        }
        refreshEpicTaskStatus(subtaskInternal, epic);
    }

    /**
     * updateEpic должен обновлять только название и описание эпика
     */
    @Override
    public void updateEpic(Epic epic) {
        for (Epic newEpic : epics) {
            if (newEpic.getId() == epic.getId()) {
                newEpic.setName(epic.getName());
                newEpic.setDescription(epic.getDescription());
            }
        }
    }

    @Override
    public void updateTask(Task task) {
        tasks.set(tasks.indexOf(task), task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.set(subtasks.indexOf(subtask), subtask);
        int epicId = subtask.getEpicId();
        Epic epic = getEpic(epicId, false);
        ArrayList<Subtask> subtaskInternal = new ArrayList<>();
        for (int epicSubtask : epic.getSubtaskIds()) {
            subtaskInternal.add(getSubtask(epicSubtask, false));
        }
        refreshEpicTaskStatus(subtaskInternal, epic);
    }

    @Override
    public void deleteTask(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                tasks.remove(task);
                break;
            }
        }
    }

    @Override
    public void deleteEpic(int epicId) {
        for (Epic epic : epics) {
            if (epic.getId() == epicId) {
                epics.remove(epic);
                break;
            }
        }
        subtasks.removeIf(subtask -> subtask.getEpicId() == epicId);
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        Epic epic = getEpic(getSubtask(subtaskId, false).getEpicId(), false);
        for (Subtask subtask : subtasks) {
            if (subtask.getId() == subtaskId) {
                subtasks.remove(subtask);
                epic.getSubtaskIds().remove(Integer.valueOf(subtask.getId()));
                break;
            }
        }

        ArrayList<Subtask> subtaskInternal = new ArrayList<>();
        for (int epicSubtask : epic.getSubtaskIds()) {
            if (getSubtask(epicSubtask, false) != null) {
                subtaskInternal.add(getSubtask(epicSubtask, false));
            }
        }
        refreshEpicTaskStatus(subtaskInternal, epic);
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic : epics) {
            epic.getSubtaskIds().removeAll(epic.getSubtaskIds());
            epic.setStatus(Status.NEW);
        }
    }
}
