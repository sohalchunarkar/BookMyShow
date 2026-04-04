package com.bookmyshow.bms.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.bms.Enums.UserType;
import com.bookmyshow.bms.Exception.UserIsNotOwnerException;
import com.bookmyshow.bms.Exception.UserNotFoundException;
import com.bookmyshow.bms.RequestDto.TheaterRequestDto;

import com.bookmyshow.bms.model.Theater;
import com.bookmyshow.bms.model.User;
import com.bookmyshow.bms.repository.TheaterRepository;
import com.bookmyshow.bms.repository.UserRepository;

@Service
public class TheaterService {
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    UserRepository userRepository;

    public Theater registerTheater(TheaterRequestDto request) {
        Optional<User> owneroptional = userRepository.findById(request.getOwnerid());
        if (owneroptional.isPresent()) {
            User owner = owneroptional.get();
            if (owner.getUserType() != UserType.THEATER_OWNER) {
                throw new UserIsNotOwnerException("The current user is not owner and cannot own theater");
            }
        } else {
            throw new UserNotFoundException("The user Does not Exist");
        }

        Theater th = new Theater();
        th.setTheaterName(request.getTheaterName());
        th.setCity(request.getCity());
        th.setState(request.getState());
        th.setPincode(request.getPincode());
        th.setOwner(owneroptional.get());

        return theaterRepository.save(th);

    }

    public List<Theater> getTheaterByOwnerId(UUID id) {
        Optional<User> owneroptional = userRepository.findById(id);
        if (owneroptional.isPresent()) {
            User owner = owneroptional.get();
            if (owner.getUserType() != UserType.THEATER_OWNER) {
                throw new UserIsNotOwnerException("The current user is not owner and cannot own theater");
            }
        } else {
            throw new UserNotFoundException("The user Does not Exist");
        }


        return theaterRepository.findByOwner(owneroptional.get());

    }
}
