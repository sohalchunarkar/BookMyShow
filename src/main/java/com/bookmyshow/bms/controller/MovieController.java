package com.bookmyshow.bms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.bms.RequestDto.MovieRequestDto;
import com.bookmyshow.bms.model.Movie;
import com.bookmyshow.bms.service.MovieService;

@RestController
@RequestMapping("/movie/api")
public class MovieController {
    @Autowired
    MovieService movieService;

    @PostMapping("/saveMovie/{id}")
    public ResponseEntity<?> saveMovie(@RequestBody MovieRequestDto movieRequestDto) {
        try {
            Movie movie = movieService.registerMoive(movieRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(movie);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            // return new ResponseEntity(response , HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/deleteMovie/{movieId}/{userId}")
    public ResponseEntity<?> deleteMovie(@PathVariable UUID movieId, @PathVariable UUID userId) {
        try {
            String responseStr = movieService.deleteMovie(movieId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", responseStr);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getAllMovies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllMovies());
    }

}
