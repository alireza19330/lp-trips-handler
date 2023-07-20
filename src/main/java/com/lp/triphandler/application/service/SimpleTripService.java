package com.lp.triphandler.application.service;

import com.lp.triphandler.domain.entity.Tap;
import com.lp.triphandler.domain.entity.Trip;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleTripService implements TripService {

    /**
     * @param taps
     * @return list of trips according to the given taps
     */
    @Override
    public List<Trip> toTrips(List<Tap> taps) {
        return null;
    }
}
