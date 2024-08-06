package em.home.work.api;

import em.home.work.model.TaskRequest;
import em.home.work.model.TaskIdResponse;
import em.home.work.model.TaskResponse;
import em.home.work.service.TaskService;
import em.home.work.store.tasks.Task;
import em.home.work.utils.EndPoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping(EndPoint.TASKS)
@Tag(name = "Управление задачами")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskService taskService;

    @PostMapping(EndPoint.great)
    @Operation(summary = "создавать новые")
    public ResponseEntity<TaskIdResponse> greatTask(@RequestBody TaskRequest taskRequest) {
        TaskIdResponse taskIdResponse = taskService.greatTask(taskRequest);
        return ResponseEntity.ok(taskIdResponse);
    }

    @PutMapping(EndPoint.update)
    @Operation(summary = "редактировать существующие")
    public ResponseEntity<String> updateTask() {
        //todo Пользователи могут управлять своими задачами !!
        //  а исполнители задачи могут менять статус своих задач.)
        // то есть если создатель то может всё ?
        // а если исполнитель то  может менять только статус своих задач.
        return ResponseEntity.ok("редактировать существующие");
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "получить задачу по id")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {

        try {
            Task task = taskService.getTask(id);
            TaskResponse taskResponse = new TaskResponse(task.getCreator(), task.getStatus(), task.getDescription(), task.getContractor(), task.getComments());
            return ResponseEntity.ok(taskResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "удалять")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        //todo Пользователи могут управлять своими задачами !!
        return ResponseEntity.ok("удалять");
    }
}
/*


 */