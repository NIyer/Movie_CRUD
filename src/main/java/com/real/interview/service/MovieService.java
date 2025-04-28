package com.real.interview.service;

import com.real.interview.dto.MovieRequest;
import com.real.interview.dto.MovieResponse;

import java.util.List;

public interface MovieService {
    MovieResponse createMovie(MovieRequest movieRequest);
    MovieResponse getMovieById(Long id);
    List<MovieResponse> getAllMovies();
    MovieResponse updateMovie(Long id, MovieRequest movieRequest);
    void deleteMovie(Long id);
    List<MovieResponse> searchMovies(String title, Integer releaseYear);
}