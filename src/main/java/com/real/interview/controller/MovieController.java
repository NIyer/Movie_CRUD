package com.real.interview.controller;


import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.real.interview.dto.MovieRequest;
import com.real.interview.dto.MovieResponse;
import com.real.interview.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @Timed(value = "movies.create", description = "Time taken to create a movie")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponse createMovie(@RequestBody MovieRequest movieRequest) {
        return movieService.createMovie(movieRequest);
    }

    @Timed(value = "movies.get.byId", description = "Time taken to get movie by ID")
    @GetMapping("/{id}")
    public MovieResponse getMovieById(
            @PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @Timed(value = "movies.get.all", description = "Time taken to get all movies")
    @GetMapping
    public List<MovieResponse> getAllMovies() {
        return movieService.getAllMovies();
    }

    @Timed(value = "movies.update", description = "Time taken to update a movie")
    @PutMapping("/{id}")
    public MovieResponse updateMovie(
            @PathVariable Long id,
            @RequestBody MovieRequest movieRequest) {
        return movieService.updateMovie(id, movieRequest);
    }

    @Timed(value = "movies.delete", description = "Time taken to delete a movie")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(
            @PathVariable Long id) {
        movieService.deleteMovie(id);
    }

    @Timed(value = "movies.search", description = "Time taken to search movies")
    @GetMapping("/search")
    public List<MovieResponse> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer releaseYear) {
        return movieService.searchMovies(title, releaseYear);
    }
}