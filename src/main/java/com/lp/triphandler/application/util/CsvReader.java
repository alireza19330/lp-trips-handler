package com.lp.triphandler.application.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CsvReader {

    /***
     *
     * @param path of the csv file
     * @return a list of lines read from file. each line is a list of string itself representing the data in each line of csv
     */
    public List<List<String>> readCsvFile(String path) {

        List<List<String>> result = new ArrayList();

        try (BufferedReader br = Files.newBufferedReader(Path.of(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                result.add(Arrays.asList(data).stream().map(it -> it.trim()).collect(Collectors.toList()));
            }
        } catch (Exception e) {
            log.error("unable to read csv file: " + e.getMessage());
        }

        result.remove(0);
        return result;
    }
}
