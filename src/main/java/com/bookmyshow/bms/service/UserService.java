package com.bookmyshow.bms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.bms.Exception.UserNotFoundException;
import com.bookmyshow.bms.RequestDto.UserRequestDto;
import com.bookmyshow.bms.ResponseDto.UserResponseDto;
import com.bookmyshow.bms.model.User;
import com.bookmyshow.bms.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserResponseDto registerUser(UserRequestDto UserRequestDto) {
        User user = new User();
        // convert to in user
        user.setUsername(UserRequestDto.getUsername());
        user.setPassword(UserRequestDto.getPassword());
        user.setEmail(UserRequestDto.getEmail());
        user.setPhoneNumber(UserRequestDto.getPhoneNumber());
        user.setAddress(UserRequestDto.getAddress());
        user.setCity(UserRequestDto.getCity());
        user.setPincode(UserRequestDto.getPincode());
        user.setUserType(UserRequestDto.getUserType());

        // saved user
        User savedUser = userRepository.save(user);
        // convert dto in response

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(savedUser.getId());
        userResponseDto.setUsername(savedUser.getUsername());
        userResponseDto.setEmail(savedUser.getEmail());
        userResponseDto.setPhoneNumber(savedUser.getPhoneNumber());
        userResponseDto.setAddress(savedUser.getAddress());
        userResponseDto.setCity(savedUser.getCity());
        userResponseDto.setPincode(savedUser.getPincode());
        userResponseDto.setUserType(savedUser.getUserType());

        return userResponseDto;

    }

    public User getUserbyidUser(int id)
    {
            Optional<User> u = userRepository.findById(id);
            if(u.isEmpty()){
                throw new UserNotFoundException("The user Not found");
            }

            return u.get();
    }

}
