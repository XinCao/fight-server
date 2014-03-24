package fight.server.model.constant;

public enum Status {

    DEFAULT(0),
    FAILURE(1),
    SUCCESS(2);
    public int count;

    private Status(int count) {
        this.count = count;
    }
}