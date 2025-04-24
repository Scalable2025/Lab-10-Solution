package com.example.orders.service;

import com.example.orders.clients.InventoryClient;
import com.example.orders.rabbitmq.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    RabbitMQProducer rabbitMQProducer;
    InventoryClient inventoryClient;

    @Autowired
    public OrderService(RabbitMQProducer rabbitMQProducer, InventoryClient inventoryClient) {
        this.rabbitMQProducer = rabbitMQProducer;
        this.inventoryClient = inventoryClient;
    }

    public String createOrder(int amount) {
        // todo: Check Inventory

        boolean flag = inventoryClient.checkInventory(amount);

        if(!flag){
            throw new RuntimeException("Not enough inventory!");
        }

        // todo: Send To Shipping
        rabbitMQProducer.sendToShipping(UUID.randomUUID().toString());

        return "Order created successfully!";
    }
}
