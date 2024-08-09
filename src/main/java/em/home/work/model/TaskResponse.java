package em.home.work.model;

import em.home.work.store.comments.Comment;
import em.home.work.store.tasks.Task;
import em.home.work.utils.Priority;
import em.home.work.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    Long id;
    String creator;
    Status status;
    String description;
    String contractor;
    Priority priority;
    List<Comment> comments;


    public TaskResponse(Task task) {
        this.id = task.getId();
        this.creator = task.getCreator();
        this.status = task.getStatus();
        this.description = task.getDescription();
        this.contractor = task.getContractor();
        this.comments = task.getComments();
        this.priority = task.getPriority();
    }
}
