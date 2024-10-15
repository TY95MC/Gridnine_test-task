package com.gridnine.testing.filter.predicate;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.util.function.Predicate;

public class SegmentDepartureBeforeArrivalPredicate implements FlightPredicateCondition {
    @Override
    public Predicate<Flight> getPredicate() {
        Predicate<Segment> arrivalBeforeDeparture =
                segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate());
        return flight -> flight.getSegments().stream().noneMatch(arrivalBeforeDeparture);
    }
}
