package com.bookmyshow.bms.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.bms.RequestDto.UserRequestDto;
import com.bookmyshow.bms.ResponseDto.UserResponseDto;
import com.bookmyshow.bms.model.User;
import com.bookmyshow.bms.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/User/api")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/SaveUser")
    public ResponseEntity<?> saveUser(@RequestBody UserRequestDto userRequestDto) {

        try {
            UserResponseDto user = userService.registerUser(userRequestDto);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            Map<String ,String> response = new HashMap<>();
            response.put("message" , e.getMessage());
            // return new ResponseEntity(response , HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @GetMapping("/findbyid/{id}")
    public User getUserbyId(@PathVariable UUID id)
    {
        return userService.getUserbyidUser(id);
    }

}
