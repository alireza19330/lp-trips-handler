package com.lp.triphandler.application.service

import com.lp.triphandler.application.exception.TapDetailsFormatException
import com.lp.triphandler.application.util.CsvReader
import com.lp.triphandler.domain.entity.TapType
import spock.lang.Specification
import spock.lang.Unroll

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

    @Unroll
    def "csvTapsReader throws TapDetailsFormatException if the given list does not invalid"() {
        given:
        csvReader.readCsvFile(_) >> [tapDetails]

        when:
        csvTapsReader.getTaps()

        then:
        thrown(TapDetailsFormatException)

        where:
        tapDetails                                                                               | _
        [""]                                                                                     | _
        ["1kjh", "22-01-2023 13:00:00 ", "ON", "Stop1", "Company1", "Bus37", "5500550055005500"] | _
        ["1", "22-01-2023 13:00 ", "ON", "Stop1", "Company1", "Bus37", "5500550055005500"]       | _
        ["1", "22-01-2023 13:00:00 ", "OON", "Stop1", "Company1", "Bus37", "5500550055005500"]   | _
        ["1", "22-01-2023 13:00:00 ", "ON", "Company1", "Bus37", "5500550055005500"]             | _
    }

    def "csvTapsReader successfully creates list of taps from the given raw list"() {
        given:
        csvReader.readCsvFile(_) >> [["1", "22-01-2023 13:00:00", "ON", "Stop1", "Company1", "Bus37", "5500005555555559"]]

        when:
        def taps = csvTapsReader.getTaps()

        then:
        noExceptionThrown()

        and:
        assert taps.size() == 1
        assert taps[0].id == 1
        assert taps[0].tapType == TapType.ON
    }


}
