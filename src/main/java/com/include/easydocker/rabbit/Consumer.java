package com.include.easydocker.rabbit;

import com.rabbitmq.client.Channel;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import static org.springframework.amqp.support.AmqpHeaders.CORRELATION_ID;


@Component
public class Consumer {

    @Autowired
    private Environment env;

    @Autowired
    private Map<String, MessageHandler> corrIdAndHandler;

    public void waitForMessageAndHandle(String correlationId, MessageHandler handler){
        corrIdAndHandler.put(correlationId, handler);
    }

    @RabbitListener(
            queues = "${rabbit.reply.queue}",
            concurrency = "1"
    )
    public void receiveMessage(@Payload DockerResponse msg,
                               @Header(CORRELATION_ID) String rk){

        if(corrIdAndHandler.containsKey(rk)) {
            System.out.println("Received msg: " + msg.toString()
                    + " from: " + env.getProperty("rabbit.reply.queue"));

            MessageHandler handler = corrIdAndHandler.get(rk);
            handler.setMessage(msg);
            handler.run();
        }
        else System.out.println("Received not owner msg , skipping...");
    }
}