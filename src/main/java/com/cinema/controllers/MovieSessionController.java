package com.cinema.controllers;

import com.cinema.model.dto.MovieSessionRequestDto;
import com.cinema.model.dto.MovieSessionResponseDto;
import com.cinema.model.dto.mappers.MovieSessionMapper;
import com.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessions")
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final ModelMapper modelMapper;
    private final MovieSessionMapper movieSessionMapper;

    @Autowired
    public MovieSessionController(MovieSessionService movieSessionService, ModelMapper modelMapper,
                                  MovieSessionMapper movieSessionMapper) {
        this.movieSessionService = movieSessionService;
        this.modelMapper = modelMapper;
        this.movieSessionMapper = movieSessionMapper;
    }

    @PostMapping("/add")
    public void addMovieSession(@RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        movieSessionService.add(movieSessionMapper.mapToMovieSession(movieSessionRequestDto));
    }

    @GetMapping("/search")
    public List<MovieSessionResponseDto> getAvailableSessions(@RequestParam Long movieId,
                                                              @RequestParam String date) {
        return movieSessionService.findAvailableSessions(movieId, LocalDate.parse(date)).stream()
                .map(session -> modelMapper.map(session, MovieSessionResponseDto.class))
                .collect(Collectors.toList());
    }
}
