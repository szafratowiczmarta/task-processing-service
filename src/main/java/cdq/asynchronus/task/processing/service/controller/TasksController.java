package cdq.asynchronus.task.processing.service.controller;

import cdq.asynchronus.task.processing.service.kafka.producers.KafkaEventProducer;
import cdq.asynchronus.task.processing.service.model.Result;
import cdq.asynchronus.task.processing.service.model.Task;
import cdq.asynchronus.task.processing.service.repository.ResultRepository;
import cdq.asynchronus.task.processing.service.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class TasksController {

    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private final ResultRepository resultRepository;
    @Autowired
    private KafkaEventProducer kafkaEventProducer;

    @GetMapping("/tasks")
    List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @PostMapping("/task")
    Task postTask(@RequestParam() String input, @RequestParam() String pattern) {
        Task task = new Task();
        task.setInput(input);
        task.setPattern(pattern);
        Task createdTask = taskRepository.save(task);
        kafkaEventProducer.produceTaskCreatedEvent(createdTask);
        return createdTask;
    }

    @GetMapping("/task/{id}")
    Optional<Task> getTaskById(@PathVariable Integer id) {
        return taskRepository.findById(id);
    }

    @GetMapping("/task/{id}/status")
    Optional<String> getTaskStatus(@PathVariable Integer id) {
        Optional<Task> task = taskRepository.findById(id);
        Optional<String> status = Optional.ofNullable(task.isPresent() ? task.get().getStatus() : null);
        return status;
    }

    @GetMapping("/task/{id}/result")
    Optional<Result> getTaskResult(@PathVariable Integer id) {
        return resultRepository.findById(id);
    }
    
}
