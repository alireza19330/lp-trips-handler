package com.lp.triphandler.application.service;

import com.lp.triphandler.domain.entity.Tap;
import com.lp.triphandler.domain.entity.Trip;

import java.util.List;

public interface TripService {

    /**
     * @param taps
     * @return list of trip according to the given list of taps
     */
    List<Trip> toTrips(List<Tap> taps);
}
