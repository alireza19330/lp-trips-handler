package com.lp.triphandler.application.service;

import com.lp.triphandler.application.exception.TapDetailsFormatException;
import com.lp.triphandler.application.util.CsvReader;
import com.lp.triphandler.domain.entity.Tap;
import com.lp.triphandler.domain.entity.TapType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvTapsReader implements TapsReader {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final CsvReader csvReader;


    /***
     *
     * @return all tap ons/offs from csv file
     */
    @Override
    public List<Tap> getTaps() {
        return csvReader.readCsvFile("path-to-csv-file").stream()
                .map(line -> toTap(line))
                .collect(Collectors.toList());
    }

    private Tap toTap(List<String> tapDetails) {
        if (Objects.isNull(tapDetails) || tapDetails.size() != 7) {
            throw new TapDetailsFormatException();
        }

        try {
            Long id = Long.parseLong(tapDetails.get(0));
            LocalDateTime dateTime = LocalDateTime.parse(tapDetails.get(1), FORMATTER);
            TapType tapType = TapType.valueOf(tapDetails.get(2));
            String stopId = tapDetails.get(3);
            String companyId = tapDetails.get(4);
            String busId = tapDetails.get(5);
            String pan = tapDetails.get(6);
            return new Tap(id, dateTime, tapType, stopId, companyId, busId, pan);
        } catch (Exception e) {
            throw new TapDetailsFormatException();
        }
    }
}
