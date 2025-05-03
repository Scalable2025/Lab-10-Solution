package com.microservices.fraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FraudService {

    FraudRepository fraudRepository;

    @Autowired
    public FraudService(FraudRepository fraudRepository)
    {
        this.fraudRepository=fraudRepository;
    }

    public boolean isFraudulentCustomer(Integer customerId) {
        Optional<Fraud> fraudOptional = fraudRepository.findById(customerId);
        if (fraudOptional.isPresent()) {
            return fraudOptional.get().getFraudster();
        } else {
            fraudRepository.save(new Fraud(customerId, false, LocalDateTime.now()));
            return false;
        }
    }
}
