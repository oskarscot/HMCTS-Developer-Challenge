package scot.oskar.hmcts.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scot.oskar.hmcts.backend.data.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
