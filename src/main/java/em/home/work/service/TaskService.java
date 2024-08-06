package em.home.work.service;

import em.home.work.model.TaskRequest;
import em.home.work.model.TaskIdResponse;
import em.home.work.store.tasks.Task;
import em.home.work.store.tasks.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
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
    UserService userService;

    @Operation(summary = "Создание task со статусом создано и без комментариев")
    public TaskIdResponse greatTask(TaskRequest taskRequest) {
        Long creator = userService.getCurrentUser().getId();
        Task task = new Task(taskRequest.getDescription(), taskRequest.getContractor(),creator);
        Task savedTask = taskRepository.save(task);
        return new TaskIdResponse(savedTask.getId());
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }
}
