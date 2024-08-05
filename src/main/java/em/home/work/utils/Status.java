package em.home.work.utils;

public enum Status {
    GREAT("great"), //создан
    REVIEWED("reviewed"), // на рассмотрение
    // отправлен исполнителю
    SENT("sent");

    private final String sts;

    Status(String sts) {
        this.sts = sts;
    }

    public String getStatus() {
        return sts;
    }
}
