package cdq.asynchronus.task.processing.service.processor;

import cdq.asynchronus.task.processing.service.model.Matcher;
import cdq.asynchronus.task.processing.service.model.Result;
import cdq.asynchronus.task.processing.service.model.Task;
import cdq.asynchronus.task.processing.service.repository.ResultRepository;
import cdq.asynchronus.task.processing.service.repository.TaskRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import static cdq.asynchronus.task.processing.service.GlobalConstant.THREAD_SLEEP_MILLS;

@Service
@AllArgsConstructor
@Slf4j
public class TaskProcessor {

    TaskRepository taskRepository;
    ResultRepository resultRepository;

    public void processTask(ConsumerRecord<String, String> consumerRecord) {
        log.info("Processing task" + consumerRecord);

        Task task = new Gson().fromJson(consumerRecord.value(), Task.class);
        Matcher matcher = new Matcher();
        String pattern = task.getPattern();
        String input = task.getInput();
        char[] patternChars = pattern.toCharArray();
        int patternLength = pattern.length();
        int inputLength = input.length();
        int steps = inputLength - patternLength;
        int entirePatternPositon = input.indexOf(pattern);
        double percentPerStep = 100.0d / steps;

        if (entirePatternPositon != -1) {
            matcher = new Matcher(0, entirePatternPositon, pattern);
            updateTaskStatus(task, 100.00);
        } else {
            for (int inputPosition = 0; inputPosition <= steps; inputPosition++) {

                double taskStatus = Math.round(Double.valueOf(inputPosition * percentPerStep));
                char[] subInput = input.substring(inputPosition, inputPosition + patternLength).toCharArray();

                int matchCounter = 0;
                for (int patternPosition = 0; patternPosition < patternLength; patternPosition++) {
                    if (subInput[patternPosition] == patternChars[patternPosition]) {
                        matchCounter++;
                    }
                }

                if (matchCounter > 0) {
                    int typos = patternLength - matchCounter;
                    String foundPattern = String.valueOf(subInput);
                    if (matcher.equals(new Matcher())) {
                        matcher = new Matcher(typos, inputPosition, foundPattern);
                        updateTaskStatus(task, taskStatus);
                        continue;
                    } else if (matcher.getTypos() < typos) {
                        updateTaskStatus(task, taskStatus);
                        continue;
                    } else if (matcher.getTypos() > typos || (matcher.getTypos() == typos && matcher.getPosition() > inputPosition)) {
                        matcher = new Matcher(typos, inputPosition, foundPattern);
                    }
                }

                try {
                    Thread.sleep(THREAD_SLEEP_MILLS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                log.info(String.format("Task status: %.2f%%", taskStatus));
                updateTaskStatus(task, taskStatus);
            }
        }

        if (!matcher.equals(new Matcher())) {
            resultRepository.save(new Result(task.getId(), matcher.getPosition(), matcher.getTypos()));
        }

        log.info(String.format("Task processing completed. Input: %s, Pattern: %s, Matcher: %s", input, pattern, matcher));
    }
    private void updateTaskStatus(Task task, double status) {
        task.setStatus(status + "%");
        taskRepository.save(task);
    }

}
