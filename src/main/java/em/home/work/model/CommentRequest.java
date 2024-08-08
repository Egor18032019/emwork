package em.home.work.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequest {
    private String text;
    private Long taskId;

    public CommentRequest(@JsonProperty(value= "text", required = true)String text,
                          @JsonProperty(value= "taskId", required = true)Long taskId) {
        this.text = text;
        this.taskId = taskId;
    }
}
