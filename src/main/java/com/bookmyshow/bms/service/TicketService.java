package com.bookmyshow.bms.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.bookmyshow.bms.Exception.SeatAlreadyBookedException;
import com.bookmyshow.bms.RequestDto.TicketRequestDto;
import com.bookmyshow.bms.model.Seat;
import com.bookmyshow.bms.model.Show;
import com.bookmyshow.bms.model.Ticket;
import com.bookmyshow.bms.model.User;
import com.bookmyshow.bms.repository.SeatRepository;
import com.bookmyshow.bms.repository.ShowRepository;
import com.bookmyshow.bms.repository.TicketRepository;
import com.bookmyshow.bms.repository.UserRepository;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShowRepository showRepository;
    @Autowired
    SeatRepository seatRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(TicketRequestDto request) {
        // 1. Fetch User and Show
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Show show = showRepository.findById(request.getShowId())
            .orElseThrow(() -> new RuntimeException("Show not found"));

        // 2. Fetch all requested Seats securely directly from DB
        List<Seat> requestedSeats = seatRepository.findAllById(request.getSeatIds());
        if (requestedSeats.size() != request.getSeatIds().size()) {
            throw new RuntimeException("Some requested seats do not exist in the database!");
        }

        // 3. Security: Verify those specific seats physically belong to the correct Hall Hosting this show!
        for (Seat seat : requestedSeats) {
            if (!seat.getHall().getId().equals(show.getHall().getId())) {
                throw new RuntimeException("Security Error: Seat " + seat.getSeatNo() + " does not physically belong to this Hall!");
            }
        }

        // 4. Concurrency Validation: Ensure none of the seats are inherently tied to an existing ticket for this show
        List<Ticket> existingTickets = ticketRepository.findByShow(show);
        for (Ticket ticket : existingTickets) {
            for (Seat bookedSeat : ticket.getSeats()) {
                if (request.getSeatIds().contains(bookedSeat.getId())) {
                    throw new SeatAlreadyBookedException("Seat " + bookedSeat.getSeatNo() + " is already booked for this show!");
                }
            }
        }

        // 5. Checkout Calculation using the price directly burned onto the Seats
        int totalAmount = 0;
        for (Seat seat : requestedSeats) {
            totalAmount += seat.getPrice();
        }

        // 6. Generate secure alphanumeric Booking ID
        String bookingId = "BMS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // 7. Assemble and push the Ticket into the Postgres database
        Ticket ticket = new Ticket();
        ticket.setBookingId(bookingId);
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setSeats(requestedSeats);
        ticket.setTotalAmount(totalAmount);

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByUserId(UUID userId) {
        return ticketRepository.findByUser_Id(userId);
    }
}
