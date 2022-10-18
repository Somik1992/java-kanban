public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Task task = new Task("Первая таска", "Проверка");
        Task task1 = new Task("Вторая таска", "Проверка");
        Task task2 = new Task("Третья таска", "Проверка");
        SubTask subTask = new SubTask("Первая таска", "Проверка");
        System.out.println(task);
        System.out.println(task1);
        System.out.println(task2);
        System.out.println(subTask);
    }
}
