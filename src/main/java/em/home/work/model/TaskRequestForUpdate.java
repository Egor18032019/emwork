package em.home.work.model;

import em.home.work.utils.Priority;
import em.home.work.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestForUpdate {
    Long id;
    Status status;
    String contractor;
    Priority priority;
    String description;
    // создателя не редактируем
}
