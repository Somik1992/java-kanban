import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;


/**
 refreshEpicTaskStatus - метод для обновления статуса эпика который вложен в updateSubtask();
 Событие для обновления статуса эпика - любое обновлдение сабтаски
 */
public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefauts();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        System.out.println("Создаем Задачи");
        Task time = new Task("Обычная таска: Заполенить тайм", "За прошлую неделю");
        Task walking = new Task("Обычная таска: Погулять с собакой", "Вечером");
        Task meal = new Task("Обычная таска: Выбрать Ресторан", "В Центре");

        Subtask collectData = new Subtask("Cаб-таска: Реконсиляция", "Собрать данные", 4);
        Subtask sortData = new Subtask("Cаб-таска: Реконсиляция", "Сортировать данные", 4);
        Epic reconcile = new Epic("Эпический Проект: Реконсиляция", "Релонсилировать котировки из разных источников");


        Epic reReconcile = new Epic("Эпический Проект: Ре-Реконсиляция", "Ре-Релонсилировать котировки из разных источников");
        Subtask reCollectData = new Subtask("Cаб-таска: Реконсиляция", "Пере-Собрать данные", 7);
        Subtask reSortData = new Subtask("Cаб-таска: Реконсиляция", "Пере-Сортировать данные", 7);
        System.out.println(" ");

        System.out.println("2.4 Создание. Сам объект должен передаваться в качестве параметра.");
        taskManager.addTask(time);
        taskManager.addTask(walking);
        taskManager.addTask(meal);
        taskManager.addEpic(reconcile);
        taskManager.addSubtask(sortData);
        taskManager.addSubtask(collectData);

        taskManager.addEpic(reReconcile);
        taskManager.addSubtask(reSortData);
        taskManager.addSubtask(reCollectData);

        System.out.println("Задачи созданы");
        System.out.println(" ");

        System.out.println("2.1 Получение списка всех задач.");
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getSubtasks());
        System.out.println(taskManager.getEpics());
        System.out.println(" ");

        System.out.println("2.3 Поиск Задачи по id");
        System.out.println(taskManager.getTask(1));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(3));
        System.out.println(taskManager.getSubtask(6));
        System.out.println(taskManager.getSubtask(5));
        System.out.println(taskManager.getEpic(4));
        System.out.println(" ");

        System.out.println("2.5 Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.");
        walking.setDescription("Вечером (обновленная)");
        walking.setName("Обычная таска: Погулять с собакой (Обновленная)");
        taskManager.updateTask(walking);

        collectData.setStatus(Status.IN_PROGRESS);
        collectData.setName("Новая саб таска коллект дата");
        taskManager.updateSubtask(collectData);

        reconcile.setName("Обновленный эпик реконсиляции");
        reconcile.setDescription("Обновленныое описание эпика реконсиляции");
        taskManager.updateEpic(reconcile);

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getSubtasks());
        System.out.println(taskManager.getEpics());
        System.out.println(" ");

        System.out.println("3.1 Получение списка всех подзадач определённого эпика.");
        System.out.println(taskManager.getEpicSubtasks(4));
        System.out.println(" ");


        System.out.println("4. Управление статусами осуществляется по следующему правилу:");
        sortData.setStatus(Status.DONE);
        taskManager.updateSubtask(sortData);
        System.out.println(taskManager.getEpics());
        collectData.setStatus(Status.DONE);
        taskManager.updateSubtask(collectData);
        System.out.println(taskManager.getSubtasks());
        System.out.println(taskManager.getEpics());
        System.out.println(" ");



        System.out.println("2.6 Удаление по идентификатору");
        taskManager.deleteTask(1);
        taskManager.deleteSubtask(6);
        taskManager.deleteSubtask(5);
        taskManager.deleteEpic(4);
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getSubtasks());
        System.out.println(taskManager.getEpics());
        System.out.println(" ");

        System.out.println("2.2 Удаление всех задач");
        taskManager.deleteTasks();
        taskManager.deleteSubtasks();
        taskManager.deleteEpics();
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getSubtasks());
        System.out.println(taskManager.getEpics());
        System.out.println(" ");

        System.out.printf(String.valueOf(inMemoryHistoryManager.getHistory()));

    }
}