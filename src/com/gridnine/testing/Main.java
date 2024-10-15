package com.gridnine.testing;

import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.filter.predicate.PredicateFactory;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.util.FlightBuilder;

import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("Фильтр вылета до текущего момента времени");
        Predicate<Flight> condition1 = PredicateFactory.getDepartureBeforeNowPredicate();
        FlightFilter.filter(flights, condition1).forEach(System.out::println);

        System.out.println("\nФильтр сегментов перелета с датой прилёта раньше даты вылета");
        Predicate<Flight> condition2 = PredicateFactory.getSegmentDepartureBeforeArrivalPredicate();
        FlightFilter.filter(flights, condition2).forEach(System.out::println);

        System.out.println("\nФильтр суммарного интервала времени между пересадками");
        Predicate<Flight> condition3 = PredicateFactory.getFlightGroundTwoHoursPredicate();
        FlightFilter.filter(flights, condition3).forEach(System.out::println);
    }
}