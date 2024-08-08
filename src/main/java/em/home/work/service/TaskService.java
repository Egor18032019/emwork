package em.home.work.service;

import em.home.work.model.TaskRequest;
import em.home.work.model.TaskIdResponse;
import em.home.work.model.TaskRequestForUpdate;
import em.home.work.model.TaskResponse;
import em.home.work.store.tasks.Task;
import em.home.work.store.tasks.TaskRepository;
import em.home.work.store.users.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Task Service", description = "Управление task.")
public class TaskService {
    TaskRepository taskRepository;
    UserService userService;

    @Operation(summary = "Создание task со статусом создано и без комментариев")
    public TaskIdResponse greatTask(TaskRequest taskRequest) {
        String creator = userService.getCurrentUser().getUsername();
        Task task = new Task(creator,"Created", taskRequest.getDescription(), taskRequest.getPriority(),taskRequest.getContractor(),new ArrayList<>());
        Task savedTask = taskRepository.save(task);
        return new TaskIdResponse(savedTask.getId());
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    public void updateTask(TaskRequestForUpdate request) {
        Task task = taskRepository.findById(request.getId()).orElseThrow();
        //todo сделать ошибку 404
        User sender = userService.getCurrentUser();

        if (Objects.equals(sender.getUsername(), request.getContractor())) {
            task.setPriority(request.getPriority());
        }
        //todo создатель может быть исполнителем ?
        if (Objects.equals(sender.getUsername(), task.getCreator())) {
            task.setDescription(request.getDescription());
            task.setContractor(request.getContractor());
            task.setPriority(request.getPriority());
            task.setStatus(request.getStatus());
        }
        taskRepository.save(task);
    }


    public void deleteTask(Long id) {
        User sender = userService.getCurrentUser();
        Task task = taskRepository.findById(id).orElseThrow();
        //todo сделать ошибку 404
        if (Objects.equals(sender.getUsername(), task.getCreator())) {
            taskRepository.delete(task);
        }
    }

    public List<TaskResponse> getTaskByNameCreator(String name) {
        List<Task> tasks = taskRepository.findAllByCreator(name);

        return tasks.stream().map(TaskResponse::new).toList();

    }

    public List<TaskResponse> getTaskByNameContractor(String name, int page, int limit) {
        Page<Task> tasks = taskRepository.findAllByContractor(name, PageRequest.of(page, limit));

        return tasks.stream().map(TaskResponse::new).toList();
    }
}
