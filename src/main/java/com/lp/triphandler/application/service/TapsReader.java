package com.lp.triphandler.application.service;

import com.lp.triphandler.domain.entity.Tap;

import java.util.List;

public interface TapsReader {

    /***
     *
     * @return all the tap ons/offs
     */
    List<Tap> getTaps();
}
