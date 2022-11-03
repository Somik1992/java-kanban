import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    private static int count = 0;
    static HashMap<Integer, Task> taskReport = new HashMap<>();
    static HashMap<Integer, SubTask> subTaskReport = new HashMap<>();
    static HashMap<Integer, Epic> epicReport = new HashMap<>();


    public int generateTaskId() {
        count += 1;
        return count;
    }

    public static void saveTask(Object object) {
        switch (object.getClass().getName()) {
            case "Task" -> {
                taskReport.put(((Task) object).getTaskId(), (Task) object);
            }
            case "SubTask" -> {
                subTaskReport.put(((SubTask) object).getTaskId(), (SubTask) object);
            }
            case "Epic" -> {
                epicReport.put(((Epic) object).getEpicTaskId(), (Epic) object);
            }
        }
        System.out.println("Задача Сохранена");
    }

    public Object searchTaskByTaskId (int number) {
        Object object = null;
        if (taskReport.get(number) != null) {
            object = taskReport.get(number);
        } else if (epicReport.get(number) != null) {
            object = epicReport.get(number);
        } else if (subTaskReport.get(number) != null) {
            object = subTaskReport.get(number);
        }
        return object;
    }

    public Object createCopyTask(Object object) {
        switch (object.getClass().getName()) {
            case "Task": {
                return new Task(((Task) object).getName(), ((Task) object).getDescription());
            }
            case "SubTask": {
                return new SubTask(((SubTask) object).getName(), ((SubTask) object).getDescription());
            }
            default:
                return null;
        }
    }

    public Epic createCopyTask(Object object, ArrayList<SubTask> subTasks) {
        if ("Epic".equals(object.getClass().getName())) {
            return new Epic(((Epic) object).getName(), ((Epic) object).getDescription(), subTasks);
        }
        return null;
    }


    public void removeAllTask () {
        Manager.taskReport.keySet().removeAll(Manager.taskReport.keySet());
        System.out.println("Все задачи удалены");
    }

    public void removeTask(Scanner scanner) {
        System.out.println("Введите номер задачи");
        int number = scanner.nextInt();
        if (Manager.taskReport.get(number) != null) {
            Manager.taskReport.keySet().remove(number);
            System.out.println("Задача удалена");
        } else {
            System.out.println("Такой задачи нет");
        }

    }

    public void updateTask(Scanner scanner, Object object) {
        System.out.println("Что вы хотите обновить?");
        System.out.println("1 - Имя Задачи");
        System.out.println("2 - Описание Задачи");
        int number = scanner.nextInt();
        if (number == 1) {
            if (object.getClass().getName().equals("Task")) {
                scanner.useDelimiter("\n");
                System.out.println("Введите новое название задачи");
                String name = scanner.next();
                Manager.taskReport.get(((Task) object).getTaskId()).setName(name);
            } else if  (object.getClass().getName().equals("SubTask")) {
                scanner.useDelimiter("\n");
                System.out.println("Введите новое название задачи");
                String name = scanner.next();
                Manager.subTaskReport.get(((SubTask) object).getTaskId()).setName(name);
            } else {
                scanner.useDelimiter("\n");
                System.out.println("Введите новое название задачи");
                String name = scanner.next();
                Manager.epicReport.get(((Epic) object).getEpicTaskId()).setName(name);
            }

        } else if (number == 2) {
            if (object.getClass().getName().equals("Task")) {
                scanner.useDelimiter("\n");
                System.out.println("Введите новое название задачи");
                String name = scanner.next();
                Manager.taskReport.get(((Task) object).getTaskId()).setDescription(name);
            } else if  (object.getClass().getName().equals("SubTask")) {
                scanner.useDelimiter("\n");
                System.out.println("Введите новое название задачи");
                String name = scanner.next();
                Manager.subTaskReport.get(((SubTask) object).getTaskId()).setDescription(name);
            } else {
                scanner.useDelimiter("\n");
                System.out.println("Введите новое название задачи");
                String name = scanner.next();
                Manager.epicReport.get(((Epic) object).getEpicTaskId()).setDescription(name);
            }
        } else {
            System.out.println("Такой функции нет");
        }
    }

    public void getSubTaskIdByEpic (Scanner scanner) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        System.out.println("Введите номер Эпика");
        int number = scanner.nextInt();
        System.out.println("Номера саб-тасок данного эпика " +  Manager.epicReport.get(number).getSubTaskId());
    }

    public void manageStatusTask (Scanner scanner) {
        System.out.println("Управлять статусами можно только саб тасок или простых тасок");
        System.out.println("Доступные статусы: DONE, IN_PROGRESS, NEW");
        System.out.println("Введите номер задачи");
        int number = scanner.nextInt();
        Object object = searchTaskByTaskId(number);
        System.out.println("Введите новый статус задачи");
        String status = scanner.next();
        switch (object.getClass().getName()) {
            case "Task" -> {
                ((Task) object).setStatus(status);
                break;
            }
            case "Epic" -> {
                System.out.println("Статус Эпика станет DONE - когда все саб таски будут DONE");
                break;
            }
            case "SubTask" -> {
                ((SubTask) object).setStatus(status);
                break;
            }
            default -> {
            }
        }
    }

    public void refreshEpicTaskStatus(ArrayList<SubTask> subTasks, Epic epic) {
        String statusEpicTask;
        int countNew = 0;
        int countDone = 0;
        int epicTaskId = epic.EpicTaskId;


        for (SubTask subTask : subTasks) {
            if (subTask.getStatus().equalsIgnoreCase("NEW")) {
                countNew++;
            }
            if (subTask.getStatus().equalsIgnoreCase("DONE")) {
                countDone++;
            }
        }
        if ((subTasks.isEmpty()) || (countNew == subTasks.size())) {
            epicReport.get(epicTaskId).setStatus("NEW");
        } else if (countDone == subTasks.size()) {
            epicReport.get(epicTaskId).setStatus("DONE");
        } else {
            epicReport.get(epicTaskId).setStatus("IN_PROGRESS");
        }
    }
}
