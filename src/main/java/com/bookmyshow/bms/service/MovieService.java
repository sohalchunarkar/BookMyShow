package com.bookmyshow.bms.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.bms.Exception.UserIsNotOwnerException;
import com.bookmyshow.bms.RequestDto.MovieRequestDto;
import com.bookmyshow.bms.model.Movie;
import com.bookmyshow.bms.model.User;
import com.bookmyshow.bms.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    UserService userService;

    public Movie registerMoive(MovieRequestDto movieRequestDto) {
        Movie m = new Movie();
        m.setMoviename(movieRequestDto.getMoviename());
        m.setGener(movieRequestDto.getGener());
        m.setDescription(movieRequestDto.getDescription());
        m.setMovieDuration(movieRequestDto.getMovieDuration());
        m.setIdofAdminWhoAddedThisMove(movieRequestDto.getIdofAdminWhoAddedThisMove());

        User user = userService.getUserbyidUser(movieRequestDto.getIdofAdminWhoAddedThisMove());
        String type = user.getUserType().toString();
        if (type != "ADMIN") {
            throw new UserIsNotOwnerException("the user is not admin and can't register movie");
        }
        Movie saved = movieRepository.save(m);
        return saved;
    }

    public String deleteMovie(UUID movieId, UUID userId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
        User user = userService.getUserbyidUser(userId);
        
        String type = user.getUserType().toString();
        if (!type.equals("ADMIN") || !movie.getIdofAdminWhoAddedThisMove().equals(userId)) {
            throw new UserIsNotOwnerException("the user is not the admin who added this movie and can't delete it");
        }

        movieRepository.delete(movie);
        return "Movie deleted successfully";
    }

}
