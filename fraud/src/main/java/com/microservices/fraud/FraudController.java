package com.microservices.fraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fraud")
public class FraudController {

    FraudService fraudService;

    @Autowired
    public FraudController(FraudService fraudService)
    {
        this.fraudService = fraudService;
    }

    @GetMapping("/{customerId}")
    public boolean isFraudster(@PathVariable("customerId") Integer customerID) {
        return fraudService.isFraudulentCustomer(customerID);
    }
}
