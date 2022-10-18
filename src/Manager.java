public class Manager {
    private static int count = 0;

    public int generateTaskId() {
        count += 1;
        return count;
    }
}
