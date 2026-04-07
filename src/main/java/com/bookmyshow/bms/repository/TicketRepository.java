package com.bookmyshow.bms.repository;

import com.bookmyshow.bms.model.Show;
import com.bookmyshow.bms.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByShow(Show show);
    List<Ticket> findByUser_Id(UUID userId);
}
