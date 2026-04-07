package com.bookmyshow.bms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.bms.RequestDto.HallRequestDto;
import com.bookmyshow.bms.model.Hall;
import com.bookmyshow.bms.service.HallService;

@RestController
@RequestMapping("/halls")
public class HallController {
    @Autowired
    HallService hallService;

    @PostMapping("/saveHall")
    public ResponseEntity<?>  saveHall(@RequestBody HallRequestDto hallRequestDto)
    {
        try{
            Hall hall = hallService.registerHall(hallRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(hall);
        }
        catch(Exception e)
        {
            Map<String ,String> response = new HashMap<>();
            response.put("message" , e.getMessage());
            // return new ResponseEntity(response , HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
}
