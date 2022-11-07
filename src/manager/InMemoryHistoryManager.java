package manager;

import task.Task;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    List<Task> history;

    @Override
    public List<Task> addTask(Task task) {
        return null;
    }

    @Override
    public List<Task> getHistory() {
        return null;
    }
}
