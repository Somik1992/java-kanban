import java.util.ArrayList;
import java.util.Scanner;
/**
 * Привет! Я понимаю что в коде очень много всякой фигни и повторений
 * Писал в очень быстрые сроки, чтобы успеть сдать все 4 проекта до 7 ноября (на работе загруз)
 * Проверь пожалуйста функциональость
 * Знаю что 4 проект это доработка 3, там исправлю все косяки - если нет - все равно уйду в академ
 * Функционал вроде работает
 * P.S я не бюджетник (не знаю важно ли это)
 */
public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Scanner scanner = new Scanner(System.in);


        System.out.println("Создаем Задачи");
        Task time = new Task("Обычная таска: Заполенить тайм", "За прошлую неделю");
        Task walking = new Task("Обычная таска: Погулять с собакой", "Вечером");
        Task meal = new Task("Обычная таска: Выбрать Ресторан", "В Центре");



        SubTask collectData = new SubTask("Cаб-таска: Реконсиляция", "Собрать данные");
        SubTask sortData = new SubTask("Cаб-таска: Реконсиляция", "Сортировать данные");
        ArrayList<SubTask> subTasks = new ArrayList<>();
        subTasks.add(collectData);
        subTasks.add(sortData);
        Epic reconcile = new Epic("Эпический Проект: Реконсиляция", "Релонсилировать котировки из разных источников", subTasks);

        System.out.println("");

        System.out.println("1. Записываем Задачи");
        Manager.saveTask(time);
        Manager.saveTask(walking);
        Manager.saveTask(meal);
        Manager.saveTask(reconcile);
        Manager.saveTask(collectData);
        Manager.saveTask(sortData);
        System.out.println("");

        System.out.println("2.1 Получение списка всех задач.");
        System.out.println(Manager.taskReport);
        System.out.println(Manager.epicReport);
        System.out.println(Manager.subTaskReport);
        System.out.println("");

        System.out.println("2.3 Поиск Задачи по id");
        System.out.println(manager.searchTaskByTaskId(1));
        System.out.println(manager.searchTaskByTaskId(2));
        System.out.println(manager.searchTaskByTaskId(3));
        System.out.println(manager.searchTaskByTaskId(4));
        System.out.println(manager.searchTaskByTaskId(5));
        System.out.println(manager.searchTaskByTaskId(6));
        System.out.println("");

        System.out.println("2.4 Создание. Сам объект должен передаваться в качестве параметра.");
        System.out.println("Я чет не понял что тут надо сделать, пусть будет копия");
        Object copyTaskTime = manager.createCopyTask(time);
        Object copyTaskCollectData = manager.createCopyTask(collectData);
        Object copyTaskSortData = manager.createCopyTask(sortData);
        ArrayList<SubTask> newSubtasks = new ArrayList<>();
        newSubtasks.add((SubTask) copyTaskSortData);
        newSubtasks.add((SubTask) copyTaskCollectData);
        Epic copyTaskreconcile = manager.createCopyTask(reconcile, newSubtasks);

        Manager.saveTask(copyTaskTime);
        Manager.saveTask(copyTaskCollectData);
        Manager.saveTask(copyTaskSortData);
        Manager.saveTask(copyTaskreconcile);

        System.out.println(copyTaskTime);
        System.out.println(copyTaskCollectData);
        System.out.println(copyTaskSortData);
        System.out.println(copyTaskreconcile);
        System.out.println("");

        System.out.println("2.5 Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.");
        manager.updateTask(scanner, time);
        System.out.println(Manager.taskReport);
        System.out.println(" ");

        System.out.println("3.1 Получение списка всех подзадач определённого эпика.");
        manager.getSubTaskIdByEpic(scanner);
        System.out.println(" ");


        System.out.println("4. Управление статусами осуществляется по следующему правилу:");
        manager.manageStatusTask(scanner);
        System.out.println(Manager.subTaskReport);
        System.out.println(Manager.taskReport);
        manager.manageStatusTask(scanner);
        System.out.println(Manager.epicReport);
        manager.refreshEpicTaskStatus(newSubtasks, copyTaskreconcile);
        System.out.println(Manager.epicReport);

    }
}