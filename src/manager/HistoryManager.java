package manager;

import task.Task;

import java.util.List;

public interface HistoryManager {

    List<Task> addTask(Task task);

    List<Task> getHistory();
}
