package com.lp.triphandler.application.service


import com.lp.triphandler.application.util.CsvReader
import com.lp.triphandler.domain.entity.TapType
import spock.lang.Specification

class CsvTapsReaderTest extends Specification {

    private CsvReader csvReader
    private CsvTapsReader csvTapsReader

    def setup() {
        csvReader = Mock(CsvReader)
        csvTapsReader = new CsvTapsReader(csvReader);
    }


    def "csvTapsReader returns empty list if csvReady returns empty list"() {
        given:
        csvReader.readCsvFile(_) >> Collections.emptyList()

        when:
        List taps = csvTapsReader.getTaps()

        then:
        noExceptionThrown()

        and:
        assert taps.isEmpty()
    }

    def "csvTapsReader throws Exception if the given list is invalid"() {
        given:
        csvReader.readCsvFile(_) >> [[""]]

        when:
        csvTapsReader.getTaps()

        then:
        thrown(IllegalArgumentException)
    }

    def "csvTapsReader successfully creates list of taps from the given raw list"() {
        given:
        csvReader.readCsvFile(_) >> [["1", "22-01-2023 13:00:00", "ON", "Stop1", "Company1", "Bus37", "5500005555555559"]]

        when:
        def taps = csvTapsReader.getTaps()

        then:
        noExceptionThrown()

        and:
        taps.size() == 1
        taps[0].id == 1
        taps[0].tapType == TapType.ON
    }


}
