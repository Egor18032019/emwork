package em.home.work.utils;

import lombok.Getter;

@Getter
public enum Priority {
    HIGH("HIGH"),
    MIDDLE("MIDDLE"),
    LOW("LOW");

    private final String priority;

    Priority(String priority) {
        this.priority = priority;
    }
}
