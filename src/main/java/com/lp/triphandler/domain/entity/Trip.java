package com.lp.triphandler.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Trip {
    private final LocalDateTime started;
    private final LocalDateTime finished;
    private final long durationSecs;
    private final String fromStopId;
    private final String toStopId;
    private final float chargeAmount;
    private final String companyId;
    private final String busId;
    private final String pan;
    private final TripStatus status;


}
