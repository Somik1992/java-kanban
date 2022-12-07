package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private static int count = 0;
    static HashMap<Integer, Task> tasks = new HashMap<>();
    static HashMap<Integer, Subtask> subtasks = new HashMap<>();
    static HashMap<Integer, Epic> epics = new HashMap<>();
    private final InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();


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
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public Collection<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> newSubtasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        for (Integer id: epic.getSubtaskIds()) {
            newSubtasks.add(getSubtaskInternal(id));
        }
        return newSubtasks;
    }

    @Override
    public Task getTask(int taskId) {
        Task task = getTaskInternal(taskId);
        inMemoryHistoryManager.addTask(task);
        return task;
    }

    public Task getTaskInternal(int taskId) {
        return tasks.get(taskId);
    }

    @Override
    public Epic getEpic(int epicId) {
        Epic epic = getEpicInternal(epicId);
        inMemoryHistoryManager.addTask(epic);
        return epic;
    }

    public Epic getEpicInternal(int epicId) {
        return epics.get(epicId);
    }

    @Override
    public Subtask getSubtask(int subtaskId) {
        Subtask subtask = getSubtaskInternal(subtaskId);
        inMemoryHistoryManager.addTask(subtask);
        return subtask;
    }

    public Subtask getSubtaskInternal(int subtaskId) {
        return subtasks.get(subtaskId);
    }

    /**
     * Менеджер сам устанавливает id, т. е. в этот метод задача приходит без id
     */
    @Override
    public void addTask(Task task) {
        task.setId(generateTaskId());
        tasks.put(task.getId(), task);
    }

    /**
     * Менеджер сам устанавливает id, т. е. в этот метод эпик приходит без id
     */
    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateTaskId());
        epic.setStatus(Status.NEW);
        epics.put(epic.getId(), epic);
    }

    /**
     * Менеджер сам устанавливает id, т. е. в этот метод подзадача приходит без id.
     * Менеджер также добавляет подзадачу в эпик (подзадача передается с заполненным epicId).
     */
    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(generateTaskId());
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).getSubtaskIds().add(subtask.getId());
        Epic epic = getEpicInternal(subtask.getEpicId());
        ArrayList<Subtask> subtaskInternal = new ArrayList<>();
        for (int epicSubtask : epic.getSubtaskIds()) {
            subtaskInternal.add(getSubtaskInternal(epicSubtask));
        }
        refreshEpicTaskStatus(subtaskInternal, epic);
    }

    /**
     * updateEpic должен обновлять только название и описание эпика
     */
    @Override
    public void updateEpic(Epic epic) {
        Epic newEpic = epics.get(epic.getId());
        newEpic.setName(epic.getName());
        newEpic.setDescription(epic.getDescription());
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        int epicId = subtask.getEpicId();
        Epic epic = getEpicInternal(epicId);

        ArrayList<Subtask> subtaskInternal = new ArrayList<>();
        for (int epicSubtask : epic.getSubtaskIds()) {
            subtaskInternal.add(getSubtaskInternal(epicSubtask));
        }
        refreshEpicTaskStatus(subtaskInternal, epic);
    }

    @Override
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void deleteEpic(int epicId) {
        epics.remove(epicId);
        subtasks.values().removeIf(subtask -> subtask.getEpicId() == epicId);
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        Epic epic = getEpicInternal(getSubtaskInternal(subtaskId).getEpicId());
        epic.getSubtaskIds().clear();

        ArrayList<Subtask> subtaskInternal = new ArrayList<>();
        for (int epicSubtask : epic.getSubtaskIds()) {
            if (getSubtaskInternal(epicSubtask) != null) {
                subtaskInternal.add(getSubtaskInternal(epicSubtask));
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
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().removeAll(epic.getSubtaskIds());
            epic.setStatus(Status.NEW);
        }
    }
}
