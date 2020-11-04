package com.cinema.controllers;

import com.cinema.model.CinemaHall;
import com.cinema.model.dto.CinemaHallRequestDto;
import com.cinema.model.dto.CinemaHallResponseDto;
import com.cinema.service.CinemaHallService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema-halls")
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;
    private final ModelMapper modelMapper;

    @Autowired
    public CinemaHallController(CinemaHallService cinemaHallService, ModelMapper modelMapper) {
        this.cinemaHallService = cinemaHallService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public void addCinemaHall(@RequestBody @Valid CinemaHallRequestDto cinemaHallRequestDto) {
        cinemaHallService.add(modelMapper.map(cinemaHallRequestDto, CinemaHall.class));
    }

    @GetMapping
    public List<CinemaHallResponseDto> getCinemaHalls() {
        return cinemaHallService.getAll()
                .stream()
                .map(hall -> modelMapper.map(hall, CinemaHallResponseDto.class))
                .collect(Collectors.toList());
    }
}
