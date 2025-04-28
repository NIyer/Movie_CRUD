package com.real.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.real.interview.model.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByReleaseYear(Integer releaseYear);
    List<Movie> findByTitleContainingIgnoreCaseAndReleaseYear(String title, Integer releaseYear);
}