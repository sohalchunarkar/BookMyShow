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

import com.bookmyshow.bms.RequestDto.ShowRequestDto;
import com.bookmyshow.bms.model.Show;
import com.bookmyshow.bms.service.ShowService;

@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    ShowService showService;

    @PostMapping("/add")
    public ResponseEntity<?> addShow(@RequestBody ShowRequestDto request) {
        try {
            Show show = showService.addShow(request);
            return new ResponseEntity<>(show, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Show>> getShowsByMovie(@PathVariable UUID movieId) {
        List<Show> shows = showService.getShowsByMovieId(movieId);
        return new ResponseEntity<>(shows, HttpStatus.OK);
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<Show>> getShowsByTheater(@PathVariable UUID theaterId) {
        List<Show> shows = showService.getShowsByTheaterId(theaterId);
        return new ResponseEntity<>(shows, HttpStatus.OK);
    }
}
