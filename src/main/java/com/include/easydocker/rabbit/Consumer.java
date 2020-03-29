package com.include.easydocker.rabbit;

import com.include.easydocker.session.UsersSession;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.include.easydocker.rabbit.ProducerConfig.DOCKER_SERVICE_REPLY_QUEUE_NAME;

@Component
public class Consumer {

    @Autowired
    public UsersSession usersSession;

    @RabbitListener(
            queues = DOCKER_SERVICE_REPLY_QUEUE_NAME,
            containerFactory = "simpleRabbitListenerContainerFactory"
    )
    public void receiveBatch(List<DockerResponse> batch) {
        System.out.println("Received batch of correlation " + batch.size() + " from 'workingBatchQueue'");
//        for (DockerResponse dockerResponse: batch){
//            String message = dockerResponse.getResponse();
//            usersSession.sendLog(message);
//        }
        batch.forEach(message -> System.out.println("Message in 'REPLAY_QUEUE' batch: " + message));
        batch.forEach(message -> {
            try {
                usersSession.sendLog(message.getResponse());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}