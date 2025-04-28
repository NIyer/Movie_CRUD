package com.real.interview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.real.interview.model.Movie;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void whenFindByTitle_thenReturnMovies() {
        // given
        Movie movie = new Movie();
        movie.setTitle("Inception");
        movie.setReleaseYear(2010);
        movieRepository.save(movie);

        // when
        List<Movie> found = movieRepository.findByTitleContainingIgnoreCase("inception");

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getTitle()).isEqualTo("Inception");
    }

    @Test
    public void whenFindByReleaseYear_thenReturnMovies() {
        // given
        Movie movie = new Movie();
        movie.setTitle("The Dark Knight");
        movie.setReleaseYear(2008);
        movieRepository.save(movie);

        // when
        List<Movie> found = movieRepository.findByReleaseYear(2008);

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getReleaseYear()).isEqualTo(2008);
    }
}
