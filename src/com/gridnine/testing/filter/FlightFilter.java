package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface FlightFilter {
    List<Flight> flightFilter(List<Flight> flights, Predicate<Flight>... flightPredicates);

    List<Flight> segmentFilter(List<Flight> flights, Predicate<Segment>... segmentPredicates);
}