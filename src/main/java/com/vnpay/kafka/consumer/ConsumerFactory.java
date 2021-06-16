package com.vnpay.kafka.consumer;

import java.util.Map;

/**
 * Created by SonCD on 15/04/2021
 */
public interface ConsumerFactory <K, V> {
    String type();
    LcKafkaConsumer<K, V> buildConsumer(Map<String, Object> mapProperties, ConsumerRecordHandler<K, V> consumerRecordHandler);
}
