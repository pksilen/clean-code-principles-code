package com.example.cycle3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircularBusRouteTests {

  @Test
  void testConstructor_whenNoBusStops() {
    final List<BusStop> emptyList = List.of();
    
    assertThrows(IllegalArgumentException.class, () -> {
      new CircularBusRoute(emptyList);
    }, "Bus route must have at least one bus stop");
  }

  @Test
  void testGetNextBusStop_whenOneBusStop() {
    BusStop busStop = new BusStopImpl();
    CircularBusRoute busRoute = new CircularBusRoute(List.of(busStop));

    BusStop nextBusStop = busRoute.getNextBusStop(busStop);

    assertEquals(nextBusStop, busStop);
  }
}
