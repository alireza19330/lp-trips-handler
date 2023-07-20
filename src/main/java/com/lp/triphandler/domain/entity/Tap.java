package com.lp.triphandler.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Tap {

    private final Long id;
    private final LocalDateTime dateTime;
    private final TapType tapType;
    private final String stopId;
    private final String companyId;
    private final String busId;
    private final String pan;


}
