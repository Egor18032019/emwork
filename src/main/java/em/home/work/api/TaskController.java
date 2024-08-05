package em.home.work.api;

import em.home.work.model.TaskRequest;
import em.home.work.model.TaskResponse;
import em.home.work.service.TaskService;
import em.home.work.utils.EndPoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPoint.TASKS)
@Tag(name = "Управление задачами")
public class TaskController {
    TaskService taskService;
    @PostMapping(EndPoint.great)
    @Operation(summary = "создавать новые")
    public ResponseEntity<TaskResponse> greatTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse taskResponse =taskService.greatTask(taskRequest);
        return ResponseEntity.ok(taskResponse);
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

    @GetMapping()
    @Operation(summary = "просматривать")
    public ResponseEntity<String> getTask(@PathVariable Long id) {
        return ResponseEntity.ok("просматривать");
    }

    @DeleteMapping()
    @Operation(summary = "удалять")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        //todo Пользователи могут управлять своими задачами !!
        return ResponseEntity.ok("удалять");
    }
}
/*


 */