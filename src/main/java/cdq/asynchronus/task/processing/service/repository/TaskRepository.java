package cdq.asynchronus.task.processing.service.repository;

import cdq.asynchronus.task.processing.service.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
