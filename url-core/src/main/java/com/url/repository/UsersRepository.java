package com.url.repository;

import com.url.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmailOrderByCreateDateDesc(String email);

    Users findByCustomerNoOrderByCreateDateDesc(String customerNo);
}
