package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface FlightFilter {
    @SafeVarargs
    static List<Flight> filter(List<Flight> flights, Predicate<Flight>... predicates) {
        Predicate<Flight> predicate = Arrays.stream(predicates).reduce(x -> true, Predicate::and);

        return flights.stream()
                .filter(predicate)
                .collect(Collectors.toUnmodifiableList());
    }
}
