package cdq.asynchronus.task.processing.service;

import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class GlobalConstant {

    public static final Properties KAFKA_PROPERTIES = new Properties();

    public static final int THREAD_SLEEP_MILLS = 3000;

    static {
        KAFKA_PROPERTIES.setProperty("bootstrap.servers", "localhost:9092");
        KAFKA_PROPERTIES.setProperty("key.serializer", StringSerializer.class.getName());
        KAFKA_PROPERTIES.setProperty("value.serializer", StringSerializer.class.getName());
    }
}
