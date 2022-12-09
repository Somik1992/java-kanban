package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int count = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();


    private int generateTaskId() {
        count += 1;
        return count;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void refreshEpicTaskStatus(Epic epic) {

        ArrayList<Subtask> subtaskInternal = new ArrayList<>();
        for (int epicSubtask : epic.getSubtaskIds()) {
            subtaskInternal.add(getSubtaskInternal(epicSubtask));
        }


        int countNew = 0;
        int countDone = 0;
        int epicTaskId = epic.getId();
        int size = 0;

        for (Subtask subTask : subtaskInternal) {
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
        if ((subtaskInternal.isEmpty()) || (countNew == size)) {
            epic.setStatus(Status.NEW);
        } else if (countDone == size) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public Collection<Task> getTasks() {
        return tasks.values();
    }

    @Override
    public Collection<Epic> getEpics() {
        return epics.values();
    }

    @Override
    public Collection<Subtask> getSubtasks() {
        return subtasks.values();
    }

    @Override
    public Collection<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> newSubtasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        for (Integer id : epic.getSubtaskIds()) {
            newSubtasks.add(getSubtaskInternal(id));
        }
        return newSubtasks;
    }

    @Override
    public Task getTask(int taskId) {
        Task task = getTaskInternal(taskId);
        historyManager.addTask(task);
        return task;
    }

    public Task getTaskInternal(int taskId) {
        return tasks.get(taskId);
    }

    @Override
    public Epic getEpic(int epicId) {
        Epic epic = getEpicInternal(epicId);
        historyManager.addTask(epic);
        return epic;
    }

    public Epic getEpicInternal(int epicId) {
        return epics.get(epicId);
    }

    @Override
    public Subtask getSubtask(int subtaskId) {
        Subtask subtask = getSubtaskInternal(subtaskId);
        historyManager.addTask(subtask);
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
        refreshEpicTaskStatus(epic);
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
        refreshEpicTaskStatus(epic);
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
        epic.getSubtaskIds().removeIf(s -> s.equals(subtaskId));
        subtasks.remove(subtaskId);
        refreshEpicTaskStatus(epic);
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
