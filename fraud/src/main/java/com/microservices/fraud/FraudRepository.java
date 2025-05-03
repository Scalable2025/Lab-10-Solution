package com.microservices.fraud;

import org.springframework.data.repository.CrudRepository;

public interface FraudRepository extends CrudRepository<Fraud, Integer> {
}
