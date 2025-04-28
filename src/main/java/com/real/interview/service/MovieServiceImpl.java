package com.real.interview.service;

import org.springframework.stereotype.Service;

import com.real.interview.dto.MovieRequest;
import com.real.interview.dto.MovieResponse;
import com.real.interview.model.Movie;
import com.real.interview.repository.MovieRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieResponse createMovie(MovieRequest movieRequest) {
        Movie movie = new Movie();
        movie.setTitle(movieRequest.getTitle());
        movie.setReleaseYear(movieRequest.getReleaseYear());
        
        Movie savedMovie = movieRepository.save(movie);
        return mapToMovieResponse(savedMovie);
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        return mapToMovieResponse(movie);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponse updateMovie(Long id, MovieRequest movieRequest) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        
        movie.setTitle(movieRequest.getTitle());
        movie.setReleaseYear(movieRequest.getReleaseYear());
        
        Movie updatedMovie = movieRepository.save(movie);
        return mapToMovieResponse(updatedMovie);
    }

    @Override
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }

    @Override
    public List<MovieResponse> searchMovies(String title, Integer releaseYear) {
        List<Movie> movies;
        
        if (title != null && releaseYear != null) {
            movies = movieRepository.findByTitleContainingIgnoreCaseAndReleaseYear(title, releaseYear);
        } else if (title != null) {
            movies = movieRepository.findByTitleContainingIgnoreCase(title);
        } else if (releaseYear != null) {
            movies = movieRepository.findByReleaseYear(releaseYear);
        } else {
            movies = movieRepository.findAll();
        }
        
        return movies.stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());
    }

    private MovieResponse mapToMovieResponse(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear()
        );
    }
}