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

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRealTimeResponse(DockerRequest request){
//        String function = "build";
//        Map<String, String> body = new HashMap<>();
//        body.put("dockerfile", "FROM ubuntu:16.04 \n RUN echo hs234gola \n RUN apt-get update -y && apt-get install -y python-pip python-dev");
//        DockerRequest request = new DockerRequest(function, body);

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
    }

}
