package com.bookmyshow.bms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.bms.Exception.UserIsNotOwnerException;
import com.bookmyshow.bms.RequestDto.HallRequestDto;
import com.bookmyshow.bms.model.Hall;
import com.bookmyshow.bms.model.Seat;
import com.bookmyshow.bms.model.Theater;
import com.bookmyshow.bms.repository.HallRepository;
import com.bookmyshow.bms.repository.TheaterRepository;

@Service
public class HallService {
    @Autowired
    HallRepository hallRepository;
    @Autowired
    UserService UserService;
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    TheaterService theaterService;

    public Hall registerHall(HallRequestDto hallRequestDto) {
        Optional<Theater> theateroptional = theaterRepository.findById(hallRequestDto.getTheaterId());
        
        if(!theateroptional.isPresent())
        {
            throw new UserIsNotOwnerException("The theater not found");
           
        }
        if(hallRequestDto.getHallcapacity() != hallRequestDto.getHallrows() * hallRequestDto.getSeatsInEachRow())
        {
            throw new UserIsNotOwnerException("the rows and seats are not equal to the capacity of the hall");
        }
        Hall hall = new Hall();
        hall.setHallname(hallRequestDto.getHallname());
        hall.setHallcapacity(hallRequestDto.getHallcapacity());
        hall.setHallrows(hallRequestDto.getHallrows());
        hall.setSeatsInEachRow(hallRequestDto.getSeatsInEachRow());
        hall.setTheater(theateroptional.get());

        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < hallRequestDto.getHallrows(); i++) {
            char rowName = (char) ('A' + i);
            for (int j = 1; j <= hallRequestDto.getSeatsInEachRow(); j++) {
                Seat seat = new Seat();
                seat.setSeatNo(rowName + "-" + j);
                seat.setRowNo(rowName);
                seat.setColNo(j);
                seat.setHall(hall);
                seats.add(seat);
            }
        }
        hall.setSeats(seats);

        return hallRepository.save(hall);
    }
}
