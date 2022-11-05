import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;

/**
 Поправил все)
 Борис привет, если есть возможность пропусти проект чтобы я успел начать и сдать версию 4 спринта,
 две итерации я просто не успею до 7 ноября и начать 4 проект
 */
public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        System.out.println("Создаем Задачи");
        Task time = new Task("Обычная таска: Заполенить тайм", "За прошлую неделю");
        Task walking = new Task("Обычная таска: Погулять с собакой", "Вечером");
        Task meal = new Task("Обычная таска: Выбрать Ресторан", "В Центре");

        Subtask collectData = new Subtask("Cаб-таска: Реконсиляция", "Собрать данные");
        Subtask sortData = new Subtask("Cаб-таска: Реконсиляция", "Сортировать данные");
        ArrayList<Subtask> subTasks = new ArrayList<>();
        subTasks.add(collectData);
        subTasks.add(sortData);
        Epic reconcile = new Epic("Эпический Проект: Реконсиляция", "Релонсилировать котировки из разных источников", subTasks);

        Subtask reCollectData = new Subtask("Cаб-таска: Реконсиляция", "Пере-Собрать данные");
        Subtask reSortData = new Subtask("Cаб-таска: Реконсиляция", "Пере-Сортировать данные");
        ArrayList<Subtask> reSubTasks = new ArrayList<>();
        reSubTasks.add(reCollectData);
        reSubTasks.add(reSortData);
        Epic reReconcile = new Epic("Эпический Проект: Ре-Реконсиляция", "Ре-Релонсилировать котировки из разных источников", reSubTasks);
        System.out.println(" ");

        System.out.println("2.4 Создание. Сам объект должен передаваться в качестве параметра.");
        taskManager.addTask(time);
        taskManager.addTask(walking);
        taskManager.addTask(meal);
        taskManager.addSubtask(sortData);
        taskManager.addSubtask(collectData);
        taskManager.addEpic(reconcile);
        taskManager.addSubtask(reSortData);
        taskManager.addSubtask(reCollectData);
        taskManager.addEpic(reReconcile);
        System.out.println("Задачи созданы");
        System.out.println(" ");

        System.out.println("2.1 Получение списка всех задач.");
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(" ");

        System.out.println("2.3 Поиск Задачи по id");
        System.out.println(taskManager.searchTaskByTaskId(1));
        System.out.println(taskManager.searchTaskByTaskId(2));
        System.out.println(taskManager.searchTaskByTaskId(3));
        System.out.println(taskManager.searchTaskByTaskId(4));
        System.out.println(taskManager.searchTaskByTaskId(5));
        System.out.println(taskManager.searchTaskByTaskId(6));
        System.out.println(" ");

        System.out.println("2.5 Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.");
        taskManager.updateTask(1, walking);
        taskManager.updateSubtask(4, collectData);
        taskManager.updateEpic(6, reReconcile);
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(" ");

        System.out.println("3.1 Получение списка всех подзадач определённого эпика.");
        System.out.println(taskManager.getEpicSubtasks(6));
        System.out.println(" ");


        System.out.println("4. Управление статусами осуществляется по следующему правилу:");
        taskManager.manageStatusTask(1, "DONE");
        taskManager.manageStatusTask(2, "DONE");
        taskManager.manageStatusTask(3, "DONE");

        taskManager.manageStatusTask(4, "DONE");
        System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpics());
        taskManager.manageStatusTask(5, "DONE");

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(" ");

        System.out.println("2.6 Удаление по идентификатору");
        taskManager.deleteTask(1);
        taskManager.deleteSubtask(4);
        taskManager.deleteSubtask(5);
        taskManager.deleteEpic(6);
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(" ");

        System.out.println("2.2 Удаление всех задач");
        taskManager.deleteTasks();
        taskManager.deleteSubtasks();
        taskManager.deleteSubtasks();
        taskManager.deleteEpics();
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(" ");

    }
}