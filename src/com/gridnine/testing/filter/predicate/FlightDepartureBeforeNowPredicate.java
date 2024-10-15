package com.gridnine.testing.filter.predicate;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class FlightDepartureBeforeNowPredicate implements FlightPredicateCondition {
    @Override
    public Predicate<Flight> getPredicate() {
        Predicate<Segment> departureBeforeNow = segment -> segment.getDepartureDate().isBefore(LocalDateTime.now());
        return flight -> flight.getSegments().stream().noneMatch(departureBeforeNow);
    }
}