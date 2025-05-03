package com.microservices.customer;

import com.microservices.customer.clients.FraudClient;
import com.microservices.customer.dto.NotificationRequest;
import com.microservices.customer.rabbitmq.RabbitMQConfig;
import com.microservices.customer.rabbitmq.RabbitMQMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    CustomerRepository customerRepository;
    FraudClient fraudClient;
    RabbitMQMessageProducer rabbitMQMessageProducer;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, FraudClient fraudClient, RabbitMQMessageProducer rabbitMQMessageProducer) {
        this.customerRepository = customerRepository;
        this.fraudClient = fraudClient;
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
    }

    public void registerCustomer(Customer request) {

        Customer customer = new Customer(request.getFirstName(), request.getLastName(), request.getEmail());
        customerRepository.saveAndFlush(customer);

        // todo: check if fraudster
        boolean fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse)
        {
            throw new IllegalStateException("fraudster");
        }


        // todo: send notification
        NotificationRequest notificationRequest = new NotificationRequest(customer.getId(), customer.getEmail(), String.format("Hi %s, welcome to Microservices...", customer.getFirstName()));

        rabbitMQMessageProducer.publish(
                notificationRequest,
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.NOTIFICATION_ROUTING_KEY
        );
    }
}
