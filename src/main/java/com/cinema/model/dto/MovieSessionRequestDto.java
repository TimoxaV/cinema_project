package com.cinema.model.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieSessionRequestDto {
    @NotNull
    private String showTime;
    @NotNull
    private Long cinemaHallId;
    @NotNull
    private Long movieId;
}
