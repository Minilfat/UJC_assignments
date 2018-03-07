package org.trip.store.services;

import org.trip.store.models.Tour;

import java.util.List;

public interface ITourService {

    List<Tour> getAvailableTours();
    void orderTour(Long owner, Long id);
    Tour getTour(Long id);
}
