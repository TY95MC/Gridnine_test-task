package com.gridnine.testing.filter.predicate;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;

public class FlightGroundTimePredicate implements FlightPredicateCondition {
    @Override
    public Predicate<Flight> getPredicate() {
        return flight -> {
            long duration = 0;
            List<Segment> tmp = flight.getSegments();
            if (tmp.size() < 2) {
                return false; //исключение билетов без пересадок
            }
            for (int i = 1; i <= tmp.size() - 1; i++) {
                duration += Duration.between(tmp.get(i - 1).getArrivalDate(), tmp.get(i).getDepartureDate()).toMinutes();
            }
            return duration <= 120;
        };
    }
}
