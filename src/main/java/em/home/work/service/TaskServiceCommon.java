package em.home.work.service;

import em.home.work.model.*;
import em.home.work.store.tasks.Task;

import java.util.List;

public interface TaskServiceCommon {
    TaskIdResponse greatTask(TaskRequest taskRequest);
    Task getTask(Long id);
    void updateTask(TaskRequestForUpdate request);
    void deleteTask(Long id);
    List<TaskResponse> getTaskByNameCreator(TaskRequestForFilter request, int page, int limit);
    List<TaskResponse> getTaskByNameContractor(TaskRequestForFilter request, int page, int limit);
}
