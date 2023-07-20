package com.lp.triphandler.application.util;

import com.lp.triphandler.domain.entity.Trip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Service
public class CsvWriter {

    public void writeCSV(List<Trip> trips, String csvFilePath) {
        Path path = Path.of(csvFilePath);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write("Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusId, PAN, Status");
            bw.newLine();

            for (Trip trip : trips) {
                StringJoiner sj = new StringJoiner(",");
                sj.add(trip.getStarted().toString());
                sj.add(trip.getFinished().toString());
                sj.add(String.valueOf(trip.getDurationSecs()));
                sj.add(trip.getFromStopId());
                sj.add(trip.getToStopId());
                sj.add(String.valueOf(trip.getChargeAmount()));
                sj.add(trip.getCompanyId());
                sj.add(trip.getBusId());
                sj.add(trip.getPan());
                sj.add(trip.getStatus().name());

                bw.write(sj.toString());
                bw.newLine();
            }
        } catch (IOException exception) {
            log.error("Unable to write to file: " + exception.getMessage());
        }
    }


}
