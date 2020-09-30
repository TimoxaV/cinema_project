package com.cinema.service.impl;

import com.cinema.dao.MovieDao;
import com.cinema.lib.Inject;
import com.cinema.lib.Service;
import com.cinema.model.Movie;
import com.cinema.service.MovieService;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Inject
    private MovieDao movieDao;

    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
