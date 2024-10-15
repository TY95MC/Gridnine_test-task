package com.gridnine.testing;

import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.filter.FlightFilterImpl;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    static FlightFilter filter;
    static List<Flight> data;
    static List<Flight> result;
    static Predicate<Flight> flightPredicate;
    static Predicate<Segment> segmentPredicate;
    static Flight flight1;
    static Flight flight2;
    static Flight flight3;
    static Flight flight4;
    static Flight flight5;

    @BeforeAll
    static void init() {
        filter = new FlightFilterImpl();
        //flight with 1 segment
        flight1 = createFlight(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        //flight with 2 segments, interval 30 minutes
        flight2 = createFlight(LocalDateTime.now(), LocalDateTime.now().plusHours(1).plusMinutes(30),
                LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(4));
        //flight with 3 segments, interval 140 minutes
        flight3 = createFlight(LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4),
                LocalDateTime.now().plusHours(4).plusMinutes(20), LocalDateTime.now().plusHours(6));
        //flight with 3 segments, interval 180 minutes
        flight4 = createFlight(LocalDateTime.now(), LocalDateTime.now().plusHours(2),
                LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4),
                LocalDateTime.now().plusHours(6), LocalDateTime.now().plusHours(8));
        //flight with arrival of segment is before departure of next segment
        flight5 = createFlight(LocalDateTime.now(), LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(4));
    }

    @Test
    void shouldFilterSegmentsIntervalSummaryMoreThan90MinutesSuccessfully() {
        data = List.of(flight1, flight2, flight3, flight4);
        flightPredicate = flight -> {
            long duration = 0;
            List<Segment> tmp = flight.getSegments();
            if (tmp.size() < 2) {
                return false; //исключение билетов без пересадок
            }
            for (int i = 1; i <= tmp.size() - 1; i++) {
                duration += Duration.between(tmp.get(i - 1).getArrivalDate(), tmp.get(i).getDepartureDate()).toMinutes();
            }
            return duration <= 90;
        };
        result = filter.flightFilter(data, flightPredicate);
        assertEquals(1, result.size());
    }

    @Test
    void shouldFilterIntervalBetweenSegmentsMaximum50MinutesSuccessfully() {
        data = List.of(flight2, flight3, flight4);
        flightPredicate = flight -> {
            long duration, max = 0;
            List<Segment> tmp = flight.getSegments();
            if (tmp.size() < 2) {
                return false; //исключение билетов без пересадок
            }
            for (int i = 1; i <= tmp.size() - 1; i++) {
                duration = Duration.between(tmp.get(i - 1).getArrivalDate(), tmp.get(i).getDepartureDate()).toMinutes();
                if (duration > max) {
                    max = duration;
                }
            }
            return max <= 50;
        };
        result = filter.flightFilter(data, flightPredicate);
        assertEquals(1, result.size());
    }

    @Test
    void shouldFilterSegmentDurationLessThan90MinutesSuccessfully() {
        data = List.of(flight1, flight2, flight3);
        segmentPredicate = segment -> segment.getDepartureDate().plusMinutes(90).isBefore(segment.getArrivalDate());
        result = filter.segmentFilter(data, segmentPredicate);
        assertEquals(1, result.size());
    }

    @Test
    void shouldFilterSegmentDepartureBeforeArrivalOfPreviousSuccessfully() {
        data = List.of(flight1, flight4, flight5);
        flightPredicate = flight -> {
            List<Segment> tmp = flight.getSegments();
            if (tmp.size() < 2) {
                return false; //исключение билетов без пересадок
            }
            for (int i = 1; i <= tmp.size() - 1; i++) {
                if (tmp.get(i - 1).getArrivalDate().isAfter(tmp.get(i).getDepartureDate())) {
                    return false;
                }
            }
            return true;
        };
        result = filter.flightFilter(data, flightPredicate);
        assertEquals(1, result.size());
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}