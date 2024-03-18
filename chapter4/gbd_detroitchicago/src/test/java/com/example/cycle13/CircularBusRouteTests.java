package com.example.cycle13;


import com.example.cycle12.BusStop;
import com.example.cycle12.BusStopImpl;
import com.example.cycle12.CircularBusRoute;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircularBusRouteTests {

  @Test
  void testConstructor_whenNoBusStops() {
    // GIVEN
    final List<com.example.cycle12.BusStop> noBusStops = List.of();

    // WHEN + THEN
    assertThrows(IllegalArgumentException.class, () -> {
      new com.example.cycle12.CircularBusRoute(noBusStops);
    }, "Bus route must have at least one bus stop");
  }

  @Test
  void testGetNextBusStop_whenOneBusStop() {
    // GIVEN
    com.example.cycle12.BusStop busStop = new com.example.cycle12.BusStopImpl();
    com.example.cycle12.CircularBusRoute busRoute = new com.example.cycle12.CircularBusRoute(List.of(busStop));

    // WHEN
    com.example.cycle12.BusStop nextBusStop = busRoute.getNextBusStop(busStop);

    // THEN
    assertEquals(nextBusStop, busStop);
  }

  @Test
  void testGetNextBusStop_whenBusStopDoesNotBelongToRoute() {
    // GIVEN
    com.example.cycle12.BusStop busStopA = new com.example.cycle12.BusStopImpl();
    com.example.cycle12.BusStop busStopB = new com.example.cycle12.BusStopImpl();
    com.example.cycle12.CircularBusRoute busRoute = new com.example.cycle12.CircularBusRoute(List.of(busStopA));

    // WHEN + THEN
    assertThrows(IllegalArgumentException.class, () -> {
      busRoute.getNextBusStop(busStopB);
    }, "Bus stop does not belong to bus route");
  }

  @Test
  void testGetNextBusStop_whenNextBusStopInListExists() {
    // GIVEN
    com.example.cycle12.BusStop busStopA = new com.example.cycle12.BusStopImpl();
    com.example.cycle12.BusStop busStopB = new com.example.cycle12.BusStopImpl();
    com.example.cycle12.CircularBusRoute busRoute = new com.example.cycle12.CircularBusRoute(List.of(busStopA, busStopB));

    // WHEN
    com.example.cycle12.BusStop nextBusStop = busRoute.getNextBusStop(busStopA);

    // THEN
    assertEquals(nextBusStop, busStopB);
  }

  @Test
  void testGetNextBusStop_whenNoNextBusStopInList() {
    // GIVEN
    com.example.cycle12.BusStop busStopA = new com.example.cycle12.BusStopImpl();
    com.example.cycle12.BusStop busStopB = new BusStopImpl();
    com.example.cycle12.CircularBusRoute busRoute = new CircularBusRoute(List.of(busStopA, busStopB));

    // WHEN
    BusStop nextBusStop = busRoute.getNextBusStop(busStopB);

    // THEN
    assertEquals(nextBusStop, busStopA);
  }
}

