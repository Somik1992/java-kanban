package manager;

import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    static List<Integer> history = new ArrayList<>();

    @Override
    public void addTask(Task task) {
        if (history.size() == 10) {
            history.remove(1);
        }
        history.add(task.getId());
    }

    @Override
    public List<Integer> getHistory() {
        return history;
    }
}
