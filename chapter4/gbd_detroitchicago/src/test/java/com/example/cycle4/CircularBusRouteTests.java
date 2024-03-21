package com.example.cycle4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircularBusRouteTests {
  @Test
  void testConstructor_whenNoBusStops() {
    // GIVEN
    final List<BusStop> noBusStops = List.of();

    // WHEN + THEN
    assertThrows(IllegalArgumentException.class, () -> {
      new CircularBusRoute(noBusStops);
    }, "Bus route must have at least one bus stop");
  }

  @Test
  void testGetNextBusStop_whenOneBusStop() {
    // GIVEN
    BusStop busStop = new BusStopImpl();
    CircularBusRoute busRoute = new CircularBusRoute(List.of(busStop));

    // WHEN
    BusStop nextBusStop = busRoute.getNextBusStop(busStop);

    // THEN
    assertEquals(nextBusStop, busStop);
  }

  @Test
  void testGetNextBusStop_whenBusStopDoesNotBelongToRoute() {
    // GIVEN
    BusStop busStopA = new BusStopImpl();
    BusStop busStopB = new BusStopImpl();
    CircularBusRoute busRoute = new CircularBusRoute(List.of(busStopA));

    // WHEN + THEN
    assertThrows(IllegalArgumentException.class, () -> {
      busRoute.getNextBusStop(busStopB);
    }, "Bus stop does not belong to bus route");
  }
}
