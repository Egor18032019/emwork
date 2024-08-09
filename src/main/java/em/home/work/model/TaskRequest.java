package em.home.work.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import em.home.work.utils.Priority;
import em.home.work.utils.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskRequest {
    Status status;
    String contractor;
    Priority priority;
    String description;

    public TaskRequest(@JsonProperty(value = "status", required = true) Status status,
                       @JsonProperty(value = "contractor", required = true) String contractor,
                       @JsonProperty(value = "priority", required = true) Priority priority,
                       @JsonProperty(value = "description", required = true) String description) {
        this.status = status;
        this.contractor = contractor;
        this.priority = priority;
        this.description = description;
    }

    public TaskRequest(String great, String dady, String priority, String makeGood) {
        this.status = Status.valueOf(great);
        this.priority = Priority.valueOf(priority);
        this.contractor = dady;
        this.description = makeGood;
    }
}
