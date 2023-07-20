package com.lp.triphandler;

import com.lp.triphandler.application.config.FilesPathConfig;
import com.lp.triphandler.application.service.CsvTapsReader;
import com.lp.triphandler.application.service.TripService;
import com.lp.triphandler.application.util.CsvWriter;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    @Autowired
    private CsvTapsReader reader;

    @Autowired
    private TripService tripService;

    @Autowired
    private CsvWriter csvWriter;

    @Autowired
    private FilesPathConfig filesPathConfig;

    @PostConstruct
    private void run() {
        csvWriter.writeCSV(tripService.toTrips(reader.getTaps()), filesPathConfig.getOutput());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
