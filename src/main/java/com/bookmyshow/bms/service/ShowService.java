package com.bookmyshow.bms.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.bms.Exception.ShowTimingOverlapException;
import com.bookmyshow.bms.Exception.UserIsNotOwnerException;
import com.bookmyshow.bms.Exception.UserNotFoundException;
import com.bookmyshow.bms.RequestDto.ShowRequestDto;
import com.bookmyshow.bms.ResponseDto.SeatResponseDto;
import com.bookmyshow.bms.model.Hall;
import com.bookmyshow.bms.model.Movie;
import com.bookmyshow.bms.model.Seat;
import com.bookmyshow.bms.model.Show;
import com.bookmyshow.bms.model.Ticket;
import com.bookmyshow.bms.model.User;
import com.bookmyshow.bms.repository.HallRepository;
import com.bookmyshow.bms.repository.MovieRepository;
import com.bookmyshow.bms.repository.ShowRepository;
import com.bookmyshow.bms.repository.TicketRepository;
import com.bookmyshow.bms.repository.UserRepository;

@Service
public class ShowService {

    @Autowired
    ShowRepository showRepository;

    @Autowired
    HallRepository hallRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    public Show addShow(ShowRequestDto request) {

        // 1. Fetch Related Entities
        Optional<User> ownerOpt = userRepository.findById(request.getOwnerId());
        if (ownerOpt.isEmpty()) {
            throw new UserNotFoundException("User/Owner does not exist");
        }
        User owner = ownerOpt.get();

        Optional<Hall> hallOpt = hallRepository.findById(request.getHallId());
        if (hallOpt.isEmpty()) {
            throw new RuntimeException("Hall not found");
        }
        Hall hall = hallOpt.get();

        Optional<Movie> movieOpt = movieRepository.findById(request.getMovieId());
        if (movieOpt.isEmpty()) {
            throw new RuntimeException("Movie not found");
        }
        Movie movie = movieOpt.get();

        // 2. Authorize
        if (!hall.getTheater().getOwner().getId().equals(owner.getId())) {
            throw new UserIsNotOwnerException("Current user is not the owner of this theater!");
        }

        // Calculate End Time
        LocalTime calculatedEndTime = request.getStartTime().plusMinutes(movie.getMovieDuration());

        // 3. Time Overlap Validation
        List<Show> existingShows = showRepository.findByHallAndShowDate(hall, request.getShowDate());

        for (Show existingShow : existingShows) {
            boolean isOverlapping = (request.getStartTime().isBefore(existingShow.getEndTime())) &&
                    (calculatedEndTime.isAfter(existingShow.getStartTime()));

            if (isOverlapping) {
                throw new ShowTimingOverlapException("This hall is already booked during this time frame!");
            }
        }

        // 4. Create and Save Request
        Show newShow = new Show();
        newShow.setShowDate(request.getShowDate());
        newShow.setStartTime(request.getStartTime());
        newShow.setEndTime(calculatedEndTime);
        newShow.setBasePrice(request.getBasePrice());
        newShow.setMovie(movie);
        newShow.setHall(hall);

        return showRepository.save(newShow);
    }

    public List<Show> getShowsByMovieId(UUID movieId) {
        return showRepository.findByMovie_IdAndShowDateGreaterThanEqual(movieId, LocalDate.now());
    }

    public List<Show> getShowsByTheaterId(UUID theaterId) {
        return showRepository.findByHall_Theater_IdAndShowDateGreaterThanEqual(theaterId, LocalDate.now());
    }

    public List<SeatResponseDto> getShowSeats(UUID showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        List<Seat> allSeatsInHall = show.getHall().getSeats();
        List<Ticket> existingTickets = ticketRepository.findByShow(show);

        // Compile a master list of exactly which Seat IDs are already purchased
        Set<UUID> bookedSeatIds = new HashSet<>();
        for (Ticket ticket : existingTickets) {
            for (Seat seat : ticket.getSeats()) {
                bookedSeatIds.add(seat.getId());
            }
        }

        // Loop through all physical seats and construct UI responses matching their
        // real-time state
        List<SeatResponseDto> response = new ArrayList<>();
        for (Seat seat : allSeatsInHall) {
            boolean isBooked = bookedSeatIds.contains(seat.getId());
            response.add(new SeatResponseDto(seat.getId(), seat.getSeatNo(), seat.getPrice(), isBooked));
        }

        return response;
    }
}
