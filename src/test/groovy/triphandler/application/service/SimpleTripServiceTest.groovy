package triphandler.application.service

import com.lp.triphandler.application.service.SimpleTripService
import com.lp.triphandler.application.service.TripService
import com.lp.triphandler.domain.entity.Tap
import com.lp.triphandler.domain.entity.TapType
import com.lp.triphandler.domain.entity.Trip
import com.lp.triphandler.domain.entity.TripStatus
import spock.lang.Specification

import java.time.LocalDateTime

class SimpleTripServiceTest extends Specification {

    private TripService tripService

    private final static float costBetweenOneAndTwo = 3.25
    private final static float costBetweenTwoAndThree = 5.50
    private final static float costBetweenOneAndThree = 7.30

    def setup() {
        tripService = new SimpleTripService()
    }

    def "it creates a completed trip from one tap on and one tap off"() {
        given:
        def taps = [createTap(1, createLocalDateTime(13, 10, 30), TapType.ON, "Stop1", "Company1", "Bus37", "5500550055005500"),
                    createTap(1, createLocalDateTime(13, 10, 45), TapType.OFF, "Stop2", "Company1", "Bus37", "5500550055005500")]

        when:
        def trips = tripService.toTrips(taps)

        then:
        noExceptionThrown()

        and: "a completed trip is created"
        assert trips
        assert trips.size() == 1
        assert trips[0].status == TripStatus.COMPLETED
        assert trips[0].started == taps[0].dateTime
        assert trips[0].finished == taps[1].dateTime
        assert trips[0].chargeAmount == costBetweenOneAndTwo
    }

    def "it creates one incomplete and one completed trip"() {
        given:
        def taps = [createTap(1, createLocalDateTime(13, 10, 30), TapType.ON, "Stop1", "Company1", "Bus37", "5500550055005500"),
                    createTap(2, createLocalDateTime(13, 10, 45), TapType.ON, "Stop2", "Company1", "Bus37", "7700550055005500"),
                    createTap(3, createLocalDateTime(13, 10, 48), TapType.OFF, "Stop3", "Company1", "Bus37", "7700550055005500")]

        when:
        def trips = tripService.toTrips(taps)

        then:
        noExceptionThrown()

        and: "an incomplete trip is created"
        assert trips
        assert trips.size() == 2

        Trip incompleteTrip = trips.stream().find { it.status == TripStatus.INCOMPLETE }
        assert incompleteTrip
        assert incompleteTrip.fromStopId == taps[0].stopId
        assert incompleteTrip.companyId == taps[0].companyId
        assert incompleteTrip.pan == taps[0].pan

        and: "the charge amount should be the maximum"
        assert incompleteTrip.chargeAmount == costBetweenOneAndThree

        and: "a complete trip is created"
        Trip completeTrip = trips.stream().find { it.status == TripStatus.COMPLETED }
        assert completeTrip
        assert completeTrip.fromStopId == taps[1].stopId
        assert completeTrip.toStopId == taps[2].stopId
        assert completeTrip.chargeAmount == costBetweenTwoAndThree
    }

    def "it creates one canceled trip"() {
        given:
        def taps = [createTap(1, createLocalDateTime(13, 10, 30), TapType.ON, "Stop1", "Company1", "Bus37", "5500550055005500"),
                    createTap(2, createLocalDateTime(13, 10, 45), TapType.OFF, "Stop1", "Company1", "Bus37", "5500550055005500")]

        when:
        def trips = tripService.toTrips(taps)

        then:
        noExceptionThrown()

        and: "a canceled trip is created"
        assert trips
        assert trips.size() == 1

        assert trips[0]
        assert trips[0].status == TripStatus.CANCELED
        assert trips[0].fromStopId == taps[0].stopId
        assert trips[0].companyId == taps[0].companyId
        assert trips[0].pan == taps[0].pan
        assert trips[0].chargeAmount == 0
    }

    private Tap createTap(id, dateTime, type, stopId, companyId, busId, pan) {
        new Tap(id, dateTime, type, stopId, companyId, busId, pan)
    }

    private LocalDateTime createLocalDateTime(dayOfMonth, hour, minute) {
        LocalDateTime.of(2023, 7, dayOfMonth, hour, minute)
    }
}
