package com.bookmyshow.bms.repository;

import com.bookmyshow.bms.model.Hall;
import com.bookmyshow.bms.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ShowRepository extends JpaRepository<Show, UUID> {
    List<Show> findByHallAndShowDate(Hall hall, LocalDate showDate);
    
    // Auto-fetch shows by Movie ID
    List<Show> findByMovie_Id(UUID movieId);
    
    // Auto-fetch shows navigating through Hall to the Theater ID
    List<Show> findByHall_Theater_Id(UUID theaterId);
}
