package com.alby.userservice.configuration;


import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RabbitMQConfiguration {
    @Value("${rabbitmq.queue.authenticate.request}")
    private String rabbitMQQueueAuthenticateRequest;

    @Value("${rabbitmq.queue.authenticate.response}")
    private String rabbitMQQueueAuthenticateResponse;

    @Value("${rabbitmq.exchange.authenticate}")
    private String rabbitMQExchangeAuthenticate;

    @Bean
    public Queue authenticateRequestQueue() {
        return new Queue(rabbitMQQueueAuthenticateRequest);
    }

    @Bean
    public Queue authenticateResponseQueue() {
        return new Queue(rabbitMQQueueAuthenticateResponse);
    }

    @Bean
    public DirectExchange authenticateExchange() {
        return new DirectExchange(rabbitMQExchangeAuthenticate);
    }

    @Bean
    public Binding requestBinding(Queue requestQueue, DirectExchange exchange) {
        return BindingBuilder.bind(requestQueue).to(exchange).with(rabbitMQQueueAuthenticateRequest);
    }

    @Bean
    public Binding responseBinding(Queue responseQueue, DirectExchange exchange) {
        return BindingBuilder.bind(responseQueue).to(exchange).with(rabbitMQQueueAuthenticateResponse);
    }
}
