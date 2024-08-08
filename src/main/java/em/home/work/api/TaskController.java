package em.home.work.api;

import em.home.work.model.*;
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

import java.util.List;
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
    public ResponseEntity<String> updateTask(@RequestBody TaskRequestForUpdate request) {

        taskService.updateTask(request);
        return ResponseEntity.ok("редактировать существующие");
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "получить задачу по id")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {

        try {
            Task task = taskService.getTask(id);
            TaskResponse taskResponse = new TaskResponse(task);
            return ResponseEntity.ok(taskResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/creator")
    @Operation(summary = "получить задачу по имени создателя.")
    public ResponseEntity<ListTaskResponse> getTaskByNameCreator(@RequestBody TaskRequestForFilter request,
                                                                  @RequestParam(defaultValue = "0") int offset,
                                                                  @RequestParam(defaultValue = "5") int limit) {
        //todo Необходимо обеспечить фильтрацию и пагинацию вывода.
        try {
            List<TaskResponse> tasks = taskService.getTaskByNameCreator(request,offset,limit);
            return ResponseEntity.ok(new ListTaskResponse(tasks));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/contractor")
    @Operation(summary = "получить задачу по имени исполнителя.")
    public ResponseEntity<ListTaskResponse> getTaskByNameContractor(@RequestBody TaskRequestForFilter request,
                                                                    @RequestParam(defaultValue = "0") int offset,
                                                                    @RequestParam(defaultValue = "5") int limit) {
        try {
            List<TaskResponse> tasks = taskService.getTaskByNameContractor(request,offset,limit);
            return ResponseEntity.ok(new ListTaskResponse(tasks));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "удалять")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
