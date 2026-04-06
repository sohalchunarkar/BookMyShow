package com.bookmyshow.bms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmyshow.bms.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, UUID> {

}
