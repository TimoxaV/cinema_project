package com.cinema;

import com.cinema.exceptions.AuthenticationException;
import com.cinema.lib.Injector;
import com.cinema.model.CinemaHall;
import com.cinema.model.Movie;
import com.cinema.model.MovieSession;
import com.cinema.model.Order;
import com.cinema.model.ShoppingCart;
import com.cinema.model.User;
import com.cinema.security.AuthenticationService;
import com.cinema.service.CinemaHallService;
import com.cinema.service.MovieService;
import com.cinema.service.MovieSessionService;
import com.cinema.service.OrderService;
import com.cinema.service.ShoppingCartService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.apache.log4j.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class);
    private static Injector injector = Injector.getInstance("com.cinema");

    public static void main(String[] args) throws AuthenticationException {
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.getAll().forEach(logger::info);

        Movie sully = new Movie();
        sully.setTitle("Sully");
        movieService.add(sully);
        movieService.getAll().forEach(logger::info);

        logger.info("------------------Cinema Halls--------------------------");
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(150);
        cinemaHall.setDescription("Simple hall");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        cinemaHallService.getAll().forEach(logger::info);

        logger.info("------------------Movie Sessions--------------------------");
        MovieSession sessionFastAndFurious1 = new MovieSession();
        sessionFastAndFurious1.setCinemaHall(cinemaHall);
        sessionFastAndFurious1.setMovie(fastAndFurious);
        sessionFastAndFurious1.setShowTime(LocalDateTime.now());
        MovieSessionService sessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        sessionService.add(sessionFastAndFurious1);

        MovieSession sessionSully1 = new MovieSession();
        sessionSully1.setCinemaHall(cinemaHall);
        sessionSully1.setMovie(sully);
        sessionSully1.setShowTime(LocalDateTime.parse("2020-10-06T00:00:00"));
        sessionService.add(sessionSully1);

        MovieSession sessionSully2 = new MovieSession();
        sessionSully2.setCinemaHall(cinemaHall);
        sessionSully2.setMovie(sully);
        sessionSully2.setShowTime(LocalDateTime.parse("2020-10-06T23:59:59"));
        sessionService.add(sessionSully2);

        MovieSession sessionFastAndFurious2 = new MovieSession();
        sessionFastAndFurious2.setCinemaHall(cinemaHall);
        sessionFastAndFurious2.setMovie(fastAndFurious);
        sessionFastAndFurious2.setShowTime(LocalDateTime.now().minusDays(2));
        sessionService.add(sessionFastAndFurious2);

        logger.info("------------------Find Fast And Furious Sessions--------------");
        sessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now())
                .forEach(logger::info);
        System.out.println("------------------Find Sully Sessions--------------------------");
        sessionService.findAvailableSessions(sully.getId(), LocalDate.now())
                .forEach(logger::info);

        logger.info("------------------Users Check--------------------------");
        AuthenticationService authService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        authService.register("new1@gmail.com", "1234");
        authService.register("new2@gmail.com", "1234");
        User new1 = authService.login("new1@gmail.com", "1234");
        User new2 = authService.login("new2@gmail.com", "1234");
        logger.info(new1);
        logger.info(new2);

        logger.info("------------------ShoppingCarts Check--------------------------");
        ShoppingCartService cartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        cartService.addSession(sessionFastAndFurious1, new1);
        cartService.addSession(sessionSully1, new1);
        ShoppingCart cart1 = cartService.getByUser(new1);
        System.out.println(cart1);
        cartService.addSession(sessionFastAndFurious1, new2);
        cartService.addSession(sessionSully1, new2);
        cartService.addSession(sessionSully2, new2);
        ShoppingCart cart2 = cartService.getByUser(new2);

        logger.info("------------------Orders Check--------------------------");
        OrderService orderService =
                (OrderService) injector.getInstance(OrderService.class);
        Order order1 = orderService.completeOrder(cart1.getTickets(), cart1.getUser());
        logger.info(order1);
        logger.info(cartService.getByUser(new1));
        Order order2 = orderService.completeOrder(cart2.getTickets(), cart2.getUser());
        logger.info(order2);
        logger.info(orderService.getOrderHistory(new1));
    }
}
