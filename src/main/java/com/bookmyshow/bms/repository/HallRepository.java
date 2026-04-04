package com.bookmyshow.bms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bms.model.Hall;

public interface HallRepository extends JpaRepository<Hall, UUID> {

}
