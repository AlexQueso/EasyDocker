package com.include.easydocker.Rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.List;

import static com.include.easydocker.Rabbit.ProducerConfig.DOCKER_SERVICE_REPLY_QUEUE_NAME;


@Component
public class Consumer {

    @RabbitListener(
            queues = DOCKER_SERVICE_REPLY_QUEUE_NAME,
            containerFactory = "simpleRabbitListenerContainerFactory"
    )
    public void receiveBatch(List<DockerResponse> batch) {

        System.out.println("Received batch of correlation " + batch.size() + " from 'workingBatchQueue'");
        batch.forEach(message ->
                System.out.println("Message in 'REPLAY_QUEUE' batch: " + message)
        );
    }
}