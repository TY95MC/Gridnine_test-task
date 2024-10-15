package com.gridnine.testing.filter.predicate;

import com.gridnine.testing.model.Flight;

import java.util.function.Predicate;

public abstract class PredicateFactory {
    public static Predicate<Flight> getDepartureBeforeNowPredicate() {
        return new FlightDepartureBeforeNowPredicate().getPredicate();
    }

    public static Predicate<Flight> getSegmentDepartureBeforeArrivalPredicate() {
        return new SegmentDepartureBeforeArrivalPredicate().getPredicate();
    }

    public static Predicate<Flight> getFlightGroundTwoHoursPredicate() {
        return new FlightGroundTimePredicate().getPredicate();
    }
}