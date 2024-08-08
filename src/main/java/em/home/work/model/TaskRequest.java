package em.home.work.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskRequest {
    String status;
    String contractor;
    String priority;
    String description;

    public TaskRequest(@JsonProperty(value= "status", required = true)String status,
                       @JsonProperty(value= "contractor", required = true) String contractor,
                       @JsonProperty(value= "priority", required = true)String priority,
                       @JsonProperty(value= "description", required = true)String description) {
        this.status = status;
        this.contractor = contractor;
        this.priority = priority;
        this.description = description;
    }
}
