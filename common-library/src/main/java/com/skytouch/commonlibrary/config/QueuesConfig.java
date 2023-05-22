package com.skytouch.commonlibrary.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class QueuesConfig {
    public static final String LIST_PRODUCTS_QUEUE = "list_products_queue";
    public static final String LIST_PRODUCTS_KEY = "list_products";
    public static final String ADD_PRODUCTS_QUEUE = "add_products_queue";
    public static final String ADD_PRODUCTS_KEY = "add_products";
    public static final String EXCHANGE = "products_task_exchange";

    @Bean
    public Queue listProductsQueue() {
        return new Queue(LIST_PRODUCTS_QUEUE);
    }

    @Bean
    public Queue addProductsQueue() {
        return new Queue(ADD_PRODUCTS_QUEUE);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding listProductsBinding(Queue listProductsQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(listProductsQueue)
                .to(directExchange)
                .with(LIST_PRODUCTS_KEY);
    }

    @Bean
    public Binding addProductsBinding(Queue addProductsQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(addProductsQueue)
                .to(directExchange)
                .with(ADD_PRODUCTS_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());

        return template;
    }
}
