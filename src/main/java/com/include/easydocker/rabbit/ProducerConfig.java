package com.include.easydocker.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableRabbit
public class ProducerConfig {

    static final String DOCKER_SERVICE_EXCHANGE_NAME = "app.docker.service";
    static final String DOCKER_SERVICE_REQUEST_QUEUE_NAME = "app.docker.request";
    static final String DOCKER_SERVICE_REPLY_QUEUE_NAME = "app.docker.reply";
    static final String DOCKER_SERVICE_ROUTING_KEY_NAME = "docker";

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setConcurrentConsumers(1);
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(1);
        return factory;
    }

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
        return QueueBuilder.nonDurable(DOCKER_SERVICE_REPLY_QUEUE_NAME).build();
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

    @Bean
    public Consumer listener() {
        return new Consumer();
    }

}
