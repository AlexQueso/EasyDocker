package com.include.easydocker.Rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProducerConfig {

    static final String DOCKER_SERVICE_EXCHANGE_NAME = "app.docker.service";
    static final String DOCKER_SERVICE_REQUEST_QUEUE_NAME = "app.docker.request";
    static final String DOCKER_SERVICE_REPLY_QUEUE_NAME = "app.docker.reply";
    static final String DOCKER_SERVICE_ROUTING_KEY_NAME = "docker";

    private final RabbitTemplate rabbitTemplate;
    private final ConnectionFactory connectionFactory;

    @Autowired
    public ProducerConfig(RabbitTemplate rabbitTemplate, ConnectionFactory connectionFactory) {
        this.rabbitTemplate = rabbitTemplate;
        this.connectionFactory = connectionFactory;
    }


    @Bean
    DirectExchange exchange() {
        return new DirectExchange(DOCKER_SERVICE_EXCHANGE_NAME);
    }

    @Bean
    Queue requestQueue() {
        return QueueBuilder.durable(DOCKER_SERVICE_REQUEST_QUEUE_NAME).build();
    }

    @Bean
    Queue replyQueue() {
        return QueueBuilder.durable(DOCKER_SERVICE_REPLY_QUEUE_NAME).build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(requestQueue()).to(exchange()).with(DOCKER_SERVICE_ROUTING_KEY_NAME);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    AsyncRabbitTemplate template() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(DOCKER_SERVICE_REPLY_QUEUE_NAME);
        return new AsyncRabbitTemplate(rabbitTemplate, container);
    }

}
