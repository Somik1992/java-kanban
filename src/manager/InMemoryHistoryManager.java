package manager;

import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    static List<Task> history = new ArrayList<>();
    private final Map<Integer, Node> customLinkedList = new HashMap<>();
    private Node first;
    private Node last;

    @Override
    public void addTask(Task task) {
        if (customLinkedList.containsKey(task.getId())) {
            removeNode(customLinkedList.get(task.getId()));
            customLinkedList.remove(task.getId());
        }
        Node nodeAdded = linkLast(task);
        customLinkedList.put(task.getId(), nodeAdded);
    }

    public Node linkLast(Task task) {
        final Node secondLast = last;
        final Node newNode = new Node(secondLast, task, null);
        last = newNode;
        if (secondLast != null) {
            secondLast.next = newNode;
        } else {
            first = newNode;
        }
        return newNode;
    }

    public List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        Node node = first;
        while (node != null) {
            history.add(node.value);
            node = node.next;
        }
        return history;
    }

    @Override
    public List<Task> getHistory() {
        history = getTasks();
        return history;
    }

    public void removeNode(Node value) {

        if (value == first) {
            if (first.next != null)
                first = first.next;
            else
                first = null;
        }

        if (value == last) {
            if (last.previous != null)
                last = last.previous;
            else
                last = null;
        }

        if (value.previous != null) {
            value.previous.next = value.next;
            if (value.next != null) {
                value.next.previous = value.previous;
            }
        }
    }

    public void remove(int id) {
        if (customLinkedList.containsKey(id)) {
            removeNode(customLinkedList.get(id));
            customLinkedList.remove(id);
        }
    }

    private static class Node {
        private Node previous;
        private Task value;
        private Node next;

        public Node(Node previous, Task value, Node next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }

    }
}
