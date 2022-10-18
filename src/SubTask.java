public class SubTask extends Task {
    private static int count = 0;
    public int subTaskId;
    public String name;
    public String description;
    public String status;

    SubTask(String name, String description) {
        super(name, description);
    }

    private int generateSubTaskId () {
        count += 1;
        return subTaskId += count;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "subTaskId=" + subTaskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    @Override
    public String getName() {
        return name;
    }
}
