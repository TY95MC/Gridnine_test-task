package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FlightFilterImpl implements FlightFilter {
    @SafeVarargs
    public final List<Flight> flightFilter(List<Flight> flights, Predicate<Flight>... flightPredicates) {
        validArgsCheck(flights, flightPredicates);
        Predicate<Flight> predicate = Arrays.stream(flightPredicates).reduce(x -> true, Predicate::and);

        return flights.stream()
                .filter(predicate)
                .collect(Collectors.toUnmodifiableList());
    }

    @SafeVarargs
    public final List<Flight> segmentFilter(List<Flight> flights, Predicate<Segment>... segmentPredicates) {
        validArgsCheck(flights, segmentPredicates);
        Predicate<Flight> predicate = Arrays.stream(segmentPredicates)
                .map(segmentPredicate ->
                        (Predicate<Flight>) flight -> flight.getSegments().stream().noneMatch(segmentPredicate))
                .reduce(x -> true, Predicate::and);

        return flights.stream()
                .filter(predicate)
                .collect(Collectors.toUnmodifiableList());
    }

    @SafeVarargs
    private <T> void validArgsCheck(List<Flight> flights, Predicate<T>... predicates) {
        if (flights == null || flights.isEmpty()) {
            throw new IllegalArgumentException("Flights list must contain at least 1 element");
        }

        if (predicates == null || predicates.length == 0) {
            throw new IllegalArgumentException("Filter list must contain at least 1 element");
        }
    }
}