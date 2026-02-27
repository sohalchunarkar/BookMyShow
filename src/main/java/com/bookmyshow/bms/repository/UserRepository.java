package com.bookmyshow.bms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bms.model.User;

public interface UserRepository extends JpaRepository<User , Integer>{
 
}
