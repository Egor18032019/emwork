package em.home.work.service;

import em.home.work.model.TaskRequest;
import em.home.work.model.TaskResponse;
import em.home.work.store.tasks.Task;
import em.home.work.store.tasks.TaskRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Task Service", description = "Управление task.")
public class TaskService {
    TaskRepository taskRepository;

    public TaskResponse greatTask(TaskRequest taskRequest) {
        Task task = new Task(taskRequest.getStatus(), taskRequest.getDescription(), taskRequest.getContractor());
        Task savedTask = taskRepository.save(task);
    return new TaskResponse(savedTask.getId());
    }
}
