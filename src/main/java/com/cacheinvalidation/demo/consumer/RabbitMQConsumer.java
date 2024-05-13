package com.cacheinvalidation.demo.consumer;

import com.cacheinvalidation.demo.dto.DebeziumEventPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final RedisTemplate redisTemplate;

    @RabbitListener(queues = {"debezium.public.category", "debezium.public.irembo_service", "debezium.public.grouped_service", "debezium.public.institution", "debezium.public.irembo_service_property", "debezium.public.officer", "debezium.public.office"})
    public void receiveMessage(Message message) throws JsonProcessingException {
        String payload = new String(message.getBody());
         LOGGER.info("Data received ===> {}",payload);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(payload);
        JsonNode jsonPayload = node.path("payload");

        DebeziumEventPayload result = objectMapper.convertValue(jsonPayload, DebeziumEventPayload.class);
        LOGGER.info("DebeziumEventPayload ===> {}", result);

        // TODO: Implement dynamic cache update
        // clear Cache regions
        switch (result.getSource().getTable()) {
            case "category":
                redisTemplate.delete("Category");
                redisTemplate.delete("allActiveCategory");
                break;
            case "irembo_service":
                redisTemplate.delete("IremboService");
                break;
            case "grouped_service":
                redisTemplate.delete("ServiceGroup");
                break;
            case "institution":
                redisTemplate.delete("Institution");
                break;
            case "irembo_service_property":
            case "office":
            case "officer":
                redisTemplate.delete("IremboServiceProperty");
                break;

        }

    }

    @Getter
    public enum ETableNames {
        CATEGORY("category"),
        IREMBO_SERVICE("irembo_service"),
        IREMBO_SERVICE_PROPERTY("irembo_service_property"),
        GROUPED_SERVICE("grouped_service"),
        INSTITUTION("institution"),
        OFFICER("officer"),
        OFFICE("office");

        private final String tableName;

        ETableNames(String tableName) {
            this.tableName = tableName;
        }

    }

    @Getter
    public enum ECacheRegions {
        CATEGORY_REGION("Category"),
        ACTIVE_CATEGORY_REGION("allActiveCategory"),
        IREMBO_SERVICE_REGION("IremboService"),
        SERVICE_GROUP_REGION("ServiceGroup"),
        IREMBO_SERVICE_PROPERTY_REGION("IremboServiceProperty"),
        INSTITUTION_REGION("Institution"),
        ;

        private final String name;

        ECacheRegions(String name) {
            this.name = name;
        }

    }

}
