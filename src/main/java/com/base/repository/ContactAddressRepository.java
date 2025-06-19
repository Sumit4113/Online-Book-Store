package com.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.base.entity.ContactAddress;

public interface ContactAddressRepository extends JpaRepository<ContactAddress, Integer> {
    // You can add custom methods here if needed
}
