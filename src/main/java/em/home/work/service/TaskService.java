package em.home.work.service;

import em.home.work.model.*;
import em.home.work.store.tasks.Task;
import em.home.work.store.tasks.TaskRepository;
import em.home.work.store.users.User;
import em.home.work.utils.Status;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Task Service", description = "Управление task.")
public class TaskService implements TaskServiceCommon {
    TaskRepository taskRepository;
    UserService userService;
    @Transactional
    public TaskIdResponse greatTask(TaskRequest taskRequest) {
        String creator = userService.getCurrentUser().getUsername();
        Task task = new Task(creator, Status.GREAT, taskRequest.getDescription(), taskRequest.getPriority(), taskRequest.getContractor(), new ArrayList<>());
        taskRepository.save(task);
        return new TaskIdResponse(task.getId());
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }
    @Transactional
    public void updateTask(TaskRequestForUpdate request) {
        Task task = taskRepository.findById(request.getId()).orElseThrow();
        //todo сделать ошибку 404
        User sender = userService.getCurrentUser();

        if (Objects.equals(sender.getUsername(), request.getContractor())) {
            // исполнитель вправе менять только статус.
            task.setStatus(request.getStatus());
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

    @Transactional
    public void deleteTask(Long id) {
        User sender = userService.getCurrentUser();
        Task task = taskRepository.findById(id).orElseThrow();

        if (Objects.equals(sender.getUsername(), task.getCreator())) {
            taskRepository.delete(task);
        }
    }

    public List<TaskResponse> getTaskByNameCreator(TaskRequestForFilter request, int page, int limit) {
        //todo  построить с CreteriaAPI и интерфейсом Specification
        // или нативный запрос в бд через jsql
        //запрос в бд с параметрами
        Page<Task> tasksPages = taskRepository.findAllByCreator(request.getName(), PageRequest.of(page, limit));
        List<Task> tasks = filterTasks(tasksPages, request);
        return tasks.stream().map(TaskResponse::new).toList();

    }

    public List<TaskResponse> getTaskByNameContractor(TaskRequestForFilter request, int page, int limit) {
        Page<Task> tasksPages = taskRepository.findAllByContractor(request.getName(), PageRequest.of(page, limit));
        List<Task> tasks = filterTasks(tasksPages, request);
//todo В запросах с пагинацией возвращаем Pageble или аналог вместа списка. Потребителю нужна мета информация об офсетах
        return tasks.stream().map(TaskResponse::new).toList();
    }
    //todo переделать
    private List<Task> filterTasks(Page<Task> tasksPages, TaskRequestForFilter request) {
        List<Task> tasks = tasksPages.stream().toList();
        if (request.getFilterDescription() != null) {
            tasks = tasks.stream().filter(task -> task.getDescription().contains(request.getFilterDescription())).toList();
        }
        if (request.getFilterPriority() != null) {
            tasks = tasks.stream().filter(task -> task.getPriority().equals(request.getFilterPriority())).toList();
        }
        if (request.getFilterContractor() != null) {
            tasks = tasks.stream().filter(task -> task.getContractor().equals(request.getFilterContractor())).toList();
        }
        return tasks;
    }
}
