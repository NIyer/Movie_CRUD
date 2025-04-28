package com.real.interview.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.real.interview.dto.MovieRequest;
import com.real.interview.dto.MovieResponse;
import com.real.interview.model.Movie;
import com.real.interview.repository.MovieRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @CacheEvict(value = "movies", allEntries = true)
    @Override
    public MovieResponse createMovie(MovieRequest movieRequest) {
        Movie movie = new Movie();
        movie.setTitle(movieRequest.getTitle());
        movie.setReleaseYear(movieRequest.getReleaseYear());
        
        Movie savedMovie = movieRepository.save(movie);
        return mapToMovieResponse(savedMovie);
    }

    @Cacheable("movies")
    @Override
    public MovieResponse getMovieById(Long id) {
        Movie movie = checkIfMovieExist(id);
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
        Movie movie = checkIfMovieExist(id);
        
        movie.setTitle(movieRequest.getTitle());
        movie.setReleaseYear(movieRequest.getReleaseYear());
        
        Movie updatedMovie = movieRepository.save(movie);
        return mapToMovieResponse(updatedMovie);
    }

    private Movie checkIfMovieExist(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    @Override
    public void deleteMovie(Long id) {
        checkIfMovieExist(id);
        movieRepository.deleteById(id);
    }

    @Override
    public List<MovieResponse> searchMovies(String title, Integer releaseYear) {
        List<Movie> movies;
        
        if (title != null && !title.isEmpty() && releaseYear != null) {
            movies = movieRepository.findByTitleContainingIgnoreCaseAndReleaseYear(title, releaseYear);
        } else if (title != null && !title.isEmpty()) {
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