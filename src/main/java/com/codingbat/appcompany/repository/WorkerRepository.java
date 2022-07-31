package com.codingbat.appcompany.repository;

import com.codingbat.appcompany.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker,Integer> {

    boolean existsByNameAndAddressId(String name, Integer address_id);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
