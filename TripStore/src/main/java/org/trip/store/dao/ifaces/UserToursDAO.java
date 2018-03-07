package org.trip.store.dao.ifaces;

import java.util.List;

public interface UserToursDAO {
    List<Long> getUserTours(Long userId);
    void insert(Long owner, Long tour);
    List<Long> bookedTours();
}
