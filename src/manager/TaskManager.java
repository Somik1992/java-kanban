package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public interface TaskManager {

    List<Task> tasks = new ArrayList<>();
    List<Subtask> subtasks = new ArrayList<>();
    List<Epic> epics = new ArrayList<>();

    Collection<Task> getTasks();

    Collection<Epic> getEpics();

    Collection<Subtask> getSubtasks();

    Collection<Subtask> getEpicSubtasks(int epicId);

    Task getTask(int taskId, boolean saveHistory);

    Epic getEpic(int epicId, boolean saveHistory);

    Subtask getSubtask(int subtaskId, boolean saveHistory);

    /**
     * Менеджер сам устанавливает id, т. е. в этот метод задача приходит без id
     */
    void addTask(Task task);

    /**
     * Менеджер сам устанавливает id, т. е. в этот метод эпик приходит без id
     */
    void addEpic(Epic epic);

    /**
     * Менеджер сам устанавливает id, т. е. в этот метод подзадача приходит без id.
     * Менеджер также добавляет подзадачу в эпик (подзадача передается с заполненным epicId).
     */
    void addSubtask(Subtask subtask);

    void updateTask(Task task);

    /**
     * updateEpic должен обновлять только название и описание эпика
     */
    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void deleteTask(int taskId);

    void deleteEpic(int epicId);

    void deleteSubtask(int subtaskId);

    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();
}
