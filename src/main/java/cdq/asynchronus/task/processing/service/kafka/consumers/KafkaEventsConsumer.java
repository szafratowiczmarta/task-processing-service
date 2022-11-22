package cdq.asynchronus.task.processing.service.kafka.consumers;

import cdq.asynchronus.task.processing.service.processor.TaskProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaEventsConsumer {
    TaskProcessor taskProcessor;

    @KafkaListener(topics = {"${topic.task.created}"})
    public void receiveTaskCreatedRecord(ConsumerRecord<String, String> consumerRecord) {
        log.info("Received task created event");
        try {
            taskProcessor.processTask(consumerRecord);
        } catch (Exception e) {
            log.error(String.format("Unable to process event with offset: %s, error: %s", consumerRecord.offset(), e.getMessage()));
            e.printStackTrace();
        }
    }

}
