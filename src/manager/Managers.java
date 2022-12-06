package manager;

public class Managers {

    public static TaskManager getDefaut() {
        return new InMemoryTaskManager();
    }

    public InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
