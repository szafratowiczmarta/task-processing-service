package cdq.asynchronus.task.processing.service.kafka.producers;

import cdq.asynchronus.task.processing.service.model.Task;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static cdq.asynchronus.task.processing.service.GlobalConstant.KAFKA_PROPERTIES;

@Service
@NoArgsConstructor
@Slf4j
public class KafkaEventProducer {
    @Value("${topic.task.created}")
    private String topic;

    public void produceTaskCreatedEvent(Task task){
        Producer<String, String> producer = new KafkaProducer<>(KAFKA_PROPERTIES);

        producer.send(new ProducerRecord<>(topic, String.valueOf(task.getId()), new Gson().toJson(task)));
        log.info(String.format("Message %s sent to topic %s", task, topic));

        producer.close();
    }
}
