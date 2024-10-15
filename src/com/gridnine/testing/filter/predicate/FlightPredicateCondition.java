package com.gridnine.testing.filter.predicate;

import com.gridnine.testing.model.Flight;

import java.util.function.Predicate;

public interface FlightPredicateCondition {
    Predicate<Flight> getPredicate();
}
