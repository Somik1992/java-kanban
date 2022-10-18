public class Main {

    public static void main(String[] args) {


        Task time = new Task("Обычная таска: Заполенить тайм",
                "За прошлую неделю");

        Task walking = new Task("Обычная таска: Погулять с собакой",
                "Вечером");

        Task meal = new Task("Обычная таска: Выбрать Ресторан",
                "В Центре");

        Epic reconcile = new Epic("Эпический Проект: Реконсиляция",
                "Релонсилировать котировки из разных источников");

        SubTask collectData = new SubTask("Cаб-таска: Реконсиляция",
                "Собрать данные", reconcile);

        SubTask sortData = new SubTask("Cаб-таска: Реконсиляция",
                "Сортировать данные", reconcile);

        System.out.println(time);
        System.out.println(walking);
        System.out.println(meal);
        System.out.println(reconcile);
        System.out.println(collectData);
        System.out.println(sortData);

    }
}
