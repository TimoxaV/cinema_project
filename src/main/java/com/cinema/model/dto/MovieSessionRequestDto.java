package com.cinema.model.dto;

import lombok.Data;

@Data
public class MovieSessionRequestDto {
    private String showTime;
    private Long cinemaHallId;
    private Long movieId;
}
