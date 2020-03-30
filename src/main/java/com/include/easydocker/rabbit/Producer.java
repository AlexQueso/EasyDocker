package com.include.easydocker.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;

import static com.include.easydocker.rabbit.ProducerConfig.DOCKER_SERVICE_EXCHANGE_NAME;
import static com.include.easydocker.rabbit.ProducerConfig.DOCKER_SERVICE_REPLY_QUEUE_NAME;
import static com.include.easydocker.rabbit.ProducerConfig.DOCKER_SERVICE_ROUTING_KEY_NAME;

@Component
public class Producer {
    private RabbitTemplate rabbitTemplate;
    private Consumer consumer;

    public Producer(RabbitTemplate rabbitTemplate, Consumer consumer) {
        this.rabbitTemplate = rabbitTemplate;
        this.consumer = consumer;
    }

    public void sendRealTimeResponse(DockerRequest request, MessageHandler runnable){
        String correlationId = UUID.randomUUID().toString();
        System.out.println("Thread: '{ " + Thread.currentThread().getName() + " }' calc petition '{" + request.toString() + "}'");

        rabbitTemplate.convertAndSend(DOCKER_SERVICE_EXCHANGE_NAME, DOCKER_SERVICE_ROUTING_KEY_NAME, request, m -> {
            m.getMessageProperties().setReplyTo(DOCKER_SERVICE_REPLY_QUEUE_NAME);
            m.getMessageProperties().setCorrelationId(correlationId);
            m.getMessageProperties().setMessageId(correlationId);
            m.getMessageProperties().setHeader("ServiceMethodName", "build");
            m.getMessageProperties().setHeader("ServiceName", "docker");
            return m;
        });

        consumer.waitForMessageAndHandle(correlationId, runnable);
    }

}
