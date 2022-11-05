package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int count = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();


    public int generateTaskId() {
        count += 1;
        return count;
    }

    public void addTask(Task task) {
        int id = generateTaskId();
        tasks.put(id, task);
        task.setTaskId(id);
    }

    public void addSubtask(Subtask subtask) {
        int id = generateTaskId();
        subtasks.put(id, subtask);
        subtask.setTaskId(id);
    }

    public void addEpic(Epic epic) {
        int taskId = generateTaskId();
        epics.put(taskId, epic);
        epic.setTaskId(taskId);
        for (Subtask subtask: epics.get(taskId).getSubTasks()) {
            subtask.setEpicTaskId(taskId);
        }
        epic.setSubtaskIds(epic.getSubTaskIdInternal(epic.getSubTasks()));
    }


    public Object searchTaskByTaskId (int number) {
        Object object = null;
        if (tasks.get(number) != null) {
            object = tasks.get(number);
        } else if (epics.get(number) != null) {
            object = epics.get(number);
        } else if (subtasks.get(number) != null) {
            object = subtasks.get(number);
        }
        return object;
    }


    public void updateTask(int idToUpdate, Task task) {
        Task newTask = new Task(task.getName(), task.getDescription());
        newTask.setTaskId(idToUpdate);
        tasks.put(idToUpdate, newTask);
    }

    public void updateSubtask(int idToUpdate, Subtask subtask) {
        Subtask newSubtask = new Subtask(subtask.getName(), subtask.getDescription());
        newSubtask.setTaskId(idToUpdate);
        newSubtask.setEpicTaskId(subtask.getEpicTaskId());
        subtasks.put(idToUpdate, newSubtask);
    }

    public void updateEpic(int idToUpdate, Epic epic) {
        String name = epic.getName();
        String description = epic.getDescription();
        epics.get(idToUpdate).setName(name);
        epics.get(idToUpdate).setDescription(description);
    }


    public void manageStatusTask (int number, String status) {
        Object object = searchTaskByTaskId(number);

        switch (object.getClass().getName()) {
            case "task.Task" -> ((Task) object).setStatus(status);
            case "task.Epic" -> System.out.println("Статус Эпика станет DONE - когда все саб таски будут DONE");
            case "task.Subtask" -> {
                ((Subtask) object).setStatus(status);
                refreshEpicTaskStatus(getSubTasks(), (Epic) searchTaskByTaskId(((Subtask) object).getEpicTaskId()));
            }
            default -> {
            }
        }
    }

    public void refreshEpicTaskStatus(HashMap<Integer, Subtask> subTasks, Epic epic) {
        int countNew = 0;
        int countDone = 0;
        int epicTaskId = epic.taskId;
        int size = 0;

        for (Subtask subTask : subTasks.values()) {
            if (subTask.getEpicTaskId() == epicTaskId) {
                if (subTask.getStatus().equalsIgnoreCase("NEW")) {
                    countNew++;
                }
                if (subTask.getStatus().equalsIgnoreCase("DONE")) {
                    countDone++;

                }
                size++;
            }

        }
        if ((subTasks.isEmpty()) || (countNew == size)) {
            epics.get(epicTaskId).setStatus("NEW");
        } else if (countDone == size) {
            epics.get(epicTaskId).setStatus("DONE");
        } else {
            epics.get(epicTaskId).setStatus("IN_PROGRESS");
        }
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getSubTasks() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        return getEpics().get(epicId).getSubTasks();
    }

    public void deleteTask(int taskId) {
        tasks.keySet().remove(taskId);
        System.out.println("Задачи удалена");
    }
    public void deleteEpic(int epicId) {
        epics.keySet().remove(epicId);
        System.out.println("Эпик удален");
    }
    public void deleteSubtask(int subtaskId) {
        subtasks.keySet().remove(subtaskId);
        System.out.println("Cаб-таска удалена");
    }
    public void deleteTasks() {
        tasks.keySet().removeAll(tasks.keySet());
        System.out.println("Все задачи удалены");
    }
    public void deleteEpics() {
        epics.keySet().removeAll(epics.keySet());
        System.out.println("Все эпики удалены");
    }
    public void deleteSubtasks() {
        subtasks.keySet().removeAll(subtasks.keySet());
        System.out.println("Все саб-таски удалены");
    }
}
