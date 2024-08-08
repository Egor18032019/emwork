package em.home.work.store.tasks;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> findAllByCreator(String name);

    Page<Task> findAllByContractor(String name, Pageable paging);
}
