package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilter flightFilter = new FlightFilter(flights);
        System.out.println("Исходный список перелётов:");
        printFlights(flights);

        flightFilter.addFilter("Вылет до текущего момента времени", new Filter() {
            @Override
            public List<Flight> filter(List<Flight> flights) {
                return flights.stream()
                        .filter(flight -> flight.getSegments().stream()
                                .noneMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now())))
                        .collect(Collectors.toList());
            }
        });
        printFlights(flightFilter.selectAndExecuteFilter("Вылет до текущего момента времени"));

        printFlights(flightFilter.setAndExecuteFilter("Имеются сегменты с датой прилёта раньше даты вылета", new Filter() {
            @Override
            public List<Flight> filter(List<Flight> flights) {
                return flights.stream()
                        .filter(flight -> flight.getSegments().stream().
                                noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())))
                        .collect(Collectors.toList());
            }
        }));

        flightFilter.addFilter("Общее время, проведённое на земле превышает два часа", new Filter() {
            @Override
            public List<Flight> filter(List<Flight> flights) {
                return flights.stream().filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    long groundTime = 0;
                    for (int i = 0; i < segments.size() - 1; i++) {
                        Duration duration = Duration.between(segments.get(i).getArrivalDate(), segments.get(i + 1).getDepartureDate());
                        groundTime = groundTime + duration.toHours();
                    }
                    return groundTime <= 2;
                }).collect(Collectors.toList());
            }
        });
        printFlights(flightFilter.selectAndExecuteFilter("Общее время, проведённое на земле превышает два часа"));
    }

    public static void printFlights(List<Flight> flights) {
        flights.forEach(System.out::println);
        System.out.println();
    }
}
