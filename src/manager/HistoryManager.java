
package manager;

import task.Task;

import java.util.List;
import java.util.Map;

public interface HistoryManager {

    void addTask(Task task);

    List<Task> getHistory();

    void remove(int id);
}