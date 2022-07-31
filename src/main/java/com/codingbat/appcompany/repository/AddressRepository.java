package com.codingbat.appcompany.repository;

import com.codingbat.appcompany.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {

    boolean existsByStreetAndHauseNumber(String street, Integer hauseNumber);

    boolean existsByStreetAndHauseNumberAndIdNot(String street, Integer hauseNumber, Integer id);
}
