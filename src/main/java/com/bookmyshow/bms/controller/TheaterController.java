package com.bookmyshow.bms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.bms.RequestDto.TheaterRequestDto;
import com.bookmyshow.bms.service.TheaterService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/theaters")
public class TheaterController {
    @Autowired
    TheaterService theaterService;

    @PostMapping("/registertheater")
    public ResponseEntity<?> saveTheater(@RequestBody TheaterRequestDto request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(theaterService.registerTheater(request));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message is ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    @GetMapping("/getTheatersByOwnerId/{id}")
    public ResponseEntity<?> getListOFTheaterOwnedByOwner(@PathVariable UUID id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(theaterService.getListOfTheaterOwnedByOwner(id));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message is ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    @GetMapping("/getTheaterById/{id}")
    public ResponseEntity<?> getTheaterById(@PathVariable UUID id) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(theaterService.getTheaterByTheaterId(id));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message is ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    @GetMapping("/getAllTheaters")
    public ResponseEntity<?> getAllTheaters() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(theaterService.getAllTheaters());
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message is ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
