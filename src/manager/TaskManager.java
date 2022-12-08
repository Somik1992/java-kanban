package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.Collection;


public interface TaskManager {

    Collection<Task> getTasks();

    Collection<Epic> getEpics();

    Collection<Subtask> getSubtasks();

    Collection<Subtask> getEpicSubtasks(int epicId);


    Collection<Task> getHistory();

    Task getTask(int taskId);

    Epic getEpic(int epicId);

    Subtask getSubtask(int subtaskId);

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
