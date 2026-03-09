package com.bookmyshow.bms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bms.model.Theater;
import com.bookmyshow.bms.model.User;

public  interface TheaterRepository extends JpaRepository<Theater , Integer>{

    public List<Theater> findByOwner(User owner);

}
