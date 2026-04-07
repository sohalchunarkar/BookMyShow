package com.bookmyshow.bms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import com.bookmyshow.bms.RequestDto.TicketRequestDto;
import com.bookmyshow.bms.model.Ticket;
import com.bookmyshow.bms.service.TicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping("/bookTicket")
    public ResponseEntity<?> bookTicket(@RequestBody TicketRequestDto request) {
        try {
            Ticket ticket = ticketService.bookTicket(request);
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTicketsByUser/{userId}")
    public ResponseEntity<List<Ticket>> getTicketsByUser(@PathVariable UUID userId) {
        return new ResponseEntity<>(ticketService.getTicketsByUserId(userId), HttpStatus.OK);
    }
}
