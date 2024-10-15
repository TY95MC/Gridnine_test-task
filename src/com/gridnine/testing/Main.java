package com.gridnine.testing;

import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.filter.FlightFilterImpl;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.util.FlightBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilter filter = new FlightFilterImpl();

        System.out.println("Фильтр вылета до текущего момента времени");
        Predicate<Segment> departureBeforeNow = segment -> segment.getDepartureDate().isBefore(LocalDateTime.now());
        filter.segmentFilter(flights, departureBeforeNow).forEach(System.out::println);

        System.out.println("\nФильтр сегментов перелета с датой прилёта раньше даты вылета");
        Predicate<Segment> arrivalBeforeDeparture =
                segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate());
        filter.segmentFilter(flights, arrivalBeforeDeparture).forEach(System.out::println);

        System.out.println("\nФильтр суммарного интервала времени между пересадками");
        Predicate<Flight> segmentIntervalsMoreThanTwoHours = flight -> {
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

        filter.flightFilter(flights, segmentIntervalsMoreThanTwoHours).forEach(System.out::println);
    }
}