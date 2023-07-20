package com.lp.triphandler.application.service;

import com.lp.triphandler.application.config.RatesConfig;
import com.lp.triphandler.domain.entity.Tap;
import com.lp.triphandler.domain.entity.TapType;
import com.lp.triphandler.domain.entity.Trip;
import com.lp.triphandler.domain.entity.TripStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimpleTripService implements TripService {

    private final RatesConfig ratesConfig;

    /**
     * @param taps
     * @return list of trips according to the given taps
     */
    @Override
    public List<Trip> toTrips(List<Tap> taps) {
        return taps.stream().collect(Collectors.groupingBy(Tap::getPan))
                .values()
                .stream().map(this::toPanTrips)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<Trip> toPanTrips(List<Tap> taps) {
        // assumption: it always starts with tap ON

        List<Trip> trips = new ArrayList<>();
        int i = 0;
        while (i < taps.size()) {
            Tap currentTap = taps.get(i);
            Tap nextTap = i + 1 >= taps.size() ? null : taps.get(i + 1);
            TripStatus status = getTripStatus(currentTap, nextTap);

            Trip trip = new Trip(currentTap.getDateTime(), getFinished(currentTap, nextTap, status),
                    getDuration(currentTap, nextTap, status),
                    currentTap.getStopId(), getToStopId(currentTap, nextTap, status),
                    getCharge(currentTap, nextTap, status), currentTap.getCompanyId(),
                    currentTap.getBusId(), currentTap.getPan(), status);

            trips.add(trip);
            if (TripStatus.INCOMPLETE.equals(status)) {
                i++;
            } else {
                i += 2;
            }
        }

        return trips;
    }

    private TripStatus getTripStatus(Tap currentTap, Tap nextTap) {
        if (Objects.isNull(nextTap) || nextTap.getTapType().equals(TapType.ON)) {
            return TripStatus.INCOMPLETE;
        }
        if (currentTap.getStopId().equals(nextTap.getStopId())) {
            return TripStatus.CANCELED;
        }
        return TripStatus.COMPLETED;
    }

    private float getCharge(Tap currentTap, Tap nextTap, TripStatus status) {
        return switch (status) {
            case COMPLETED -> ratesConfig.getRates().get(currentTap.getStopId()).get(nextTap.getStopId());
            case CANCELED -> 0;
            case INCOMPLETE -> calculateMaxCharge(currentTap);
        };
    }

    private float calculateMaxCharge(Tap currentTap) {
        return ratesConfig.getRates().get(currentTap.getStopId()).values().stream().reduce(Float.MIN_VALUE, Float::max);
    }

    private String getToStopId(Tap currentTap, Tap nextTap, TripStatus status) {
        return switch (status) {
            case COMPLETED, CANCELED -> nextTap.getStopId();
            case INCOMPLETE -> currentTap.getStopId();
        };
    }

    private LocalDateTime getFinished(Tap currentTap, Tap nextTap, TripStatus status) {
        return switch (status) {
            case COMPLETED, CANCELED -> nextTap.getDateTime();
            case INCOMPLETE -> currentTap.getDateTime();
        };
    }

    private long getDuration(Tap currentTap, Tap nextTap, TripStatus status) {
        return switch (status) {
            case COMPLETED, CANCELED ->
                    nextTap.getDateTime().atZone(ZoneId.systemDefault()).toEpochSecond() - currentTap.getDateTime().atZone(ZoneId.systemDefault()).toEpochSecond();
            case INCOMPLETE -> 0;
        };
    }
}
