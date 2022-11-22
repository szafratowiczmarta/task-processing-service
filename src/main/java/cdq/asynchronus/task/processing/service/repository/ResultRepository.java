package cdq.asynchronus.task.processing.service.repository;

import cdq.asynchronus.task.processing.service.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Integer> {
}
