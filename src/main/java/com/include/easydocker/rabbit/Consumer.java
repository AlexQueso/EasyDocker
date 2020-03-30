package com.include.easydocker.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.util.Map;

import static com.include.easydocker.rabbit.ProducerConfig.DOCKER_SERVICE_REPLY_QUEUE_NAME;
import static org.springframework.amqp.support.AmqpHeaders.CORRELATION_ID;


@Component
public class Consumer {

    @Autowired
    private Map<String, MessageHandler> corrIdAndHandler;

    public void waitForMessageAndHandle(String correlationId, MessageHandler handler){
        corrIdAndHandler.put(correlationId, handler);
    }

    @RabbitListener(
            queues = DOCKER_SERVICE_REPLY_QUEUE_NAME,
            concurrency = "1"
    )
    public void receiveMessage(@Payload DockerResponse msg, @Header(CORRELATION_ID) String rk) {
        System.out.println("Received msg: " + msg.toString() + " from 'app.docker.reply'");

        MessageHandler handler = corrIdAndHandler.get(rk);
        handler.setMessage(msg);
        handler.run();
    }
}