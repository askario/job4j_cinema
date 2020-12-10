package ru.job4j.cinema.service;

import lombok.val;
import ru.job4j.cinema.model.Place;
import ru.job4j.cinema.model.dto.BookingRequest;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceService {
    private final HallService hallService = HallService.instOf();
    private final AccountService accountService = AccountService.instOf();

    private static final class Lazy {
        private static final PlaceService INST = new PlaceService();
    }

    public static PlaceService instOf() {
        return PlaceService.Lazy.INST;
    }

    public void proceedPlace(BookingRequest bookingRequest) {
        val hallOpt = hallService.getLastHall();

        if (hallOpt.isPresent()) {
            val hall = hallOpt.get();
            val account = accountService.getAccountByPhone(bookingRequest.getAccount().getPhone())
                    .orElseGet(() -> accountService.createAccount(bookingRequest.getAccount()));

            flattened(hall.getPlaces())
                    .stream()
                    .filter(place -> place.getId() == bookingRequest.getId())
                    .findFirst()
                    .ifPresent(place -> {
                        place.setOccupied(true);
                        place.setAccount_id(account.getId());
                    });

            hallService.update(hall);
        }
    }

    private List<Place> flattened(List<List<Place>> places) {
        return places.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
