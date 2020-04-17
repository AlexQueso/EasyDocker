package com.include.easydocker.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableRabbit
public class ProducerConfig {

    @Autowired
    private Environment env;

    static final String DOCKER_SERVICE_EXCHANGE_NAME = "app.docker.service";
    static final String DOCKER_SERVICE_REQUEST_QUEUE_NAME = "app.docker.request";
    static final String DOCKER_SERVICE_ROUTING_KEY_NAME = "docker";

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(DOCKER_SERVICE_EXCHANGE_NAME);
    }

    @Bean
    Queue requestQueue() {
        return QueueBuilder.nonDurable(DOCKER_SERVICE_REQUEST_QUEUE_NAME).build();
    }

    @Bean
    Queue replyQueue() {
        return QueueBuilder.nonDurable(env.getProperty("rabbit.reply.queue")).build();
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
    public Map<String, MessageHandler> responseHandler() {
        return new HashMap<>();
    }
}
