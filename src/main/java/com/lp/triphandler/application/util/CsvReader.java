package com.lp.triphandler.application.util;

import com.lp.triphandler.application.exception.NotImplementedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsvReader {

    /***
     *
     * @param path of the csv file
     * @return a list of lines read from file. each line is a list of string itself representing the data in each line of csv
     */
    public List<List<String>> readCsvFile(String path) {
        throw new NotImplementedException();
    }
}
