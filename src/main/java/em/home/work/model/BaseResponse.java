package em.home.work.model;

import em.home.work.store.tasks.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Список DB")
public class BaseResponse {
    List<Task> taskName;
}
