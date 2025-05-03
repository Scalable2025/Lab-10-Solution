package com.microservices.customer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "fraud",
        url = "${clients.fraud.url}"
)
public interface FraudClient {
    @GetMapping(path = "/fraud/{customerId}")
    boolean isFraudster(@PathVariable("customerId") Integer customerID);
}
