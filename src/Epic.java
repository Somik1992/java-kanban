public class Epic extends Task {
    public String epicName;

    Epic (String name, String description, String epicName) {
        super(name, description);
        this.epicName = epicName;
    }
}
