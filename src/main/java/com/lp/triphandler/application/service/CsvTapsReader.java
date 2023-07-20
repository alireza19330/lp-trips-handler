package com.lp.triphandler.application.service;

import com.lp.triphandler.application.exception.NotImplementedException;
import com.lp.triphandler.application.util.CsvReader;
import com.lp.triphandler.domain.entity.Tap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvTapsReader implements TapsReader {

    private final CsvReader csvReader;

    /***
     *
     * @return all tap ons/offs from csv file
     */
    @Override
    public List<Tap> getTaps() {
        return null;
    }

//    private Tap toTap(List<String> tapDetails) {
//        throw new NotImplementedException();
//    }
}
