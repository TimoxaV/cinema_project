package com.cinema;

import com.cinema.lib.Injector;
import com.cinema.model.CinemaHall;
import com.cinema.model.Movie;
import com.cinema.model.MovieSession;
import com.cinema.service.CinemaHallService;
import com.cinema.service.MovieService;
import com.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static Injector injector = Injector.getInstance("com.cinema");

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.getAll().forEach(System.out::println);

        Movie sully = new Movie();
        sully.setTitle("Sully");
        movieService.add(sully);
        movieService.getAll().forEach(System.out::println);

        System.out.println("------------------Cinema Halls--------------------------");
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(150);
        cinemaHall.setDescription("Simple hall");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("------------------Movie Sessions--------------------------");
        MovieSession session = new MovieSession();
        session.setCinemaHall(cinemaHall);
        session.setMovie(fastAndFurious);
        session.setShowTime(LocalDateTime.now());
        MovieSessionService sessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        sessionService.add(session);

        session = new MovieSession();
        session.setCinemaHall(cinemaHall);
        session.setMovie(sully);
        session.setShowTime(LocalDateTime.parse("2020-10-06T00:00:00"));
        sessionService.add(session);

        session = new MovieSession();
        session.setCinemaHall(cinemaHall);
        session.setMovie(sully);
        session.setShowTime(LocalDateTime.parse("2020-10-06T23:59:59"));
        sessionService.add(session);

        session = new MovieSession();
        session.setCinemaHall(cinemaHall);
        session.setMovie(fastAndFurious);
        session.setShowTime(LocalDateTime.now().minusDays(2));
        sessionService.add(session);

        System.out.println("------------------Find Fast And Furious Sessions--------------");
        sessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now())
                .forEach(System.out::println);
        System.out.println("------------------Find Sully Sessions--------------------------");
        sessionService.findAvailableSessions(sully.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
