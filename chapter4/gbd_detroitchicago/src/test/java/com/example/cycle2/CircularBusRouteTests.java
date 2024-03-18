package com.example.cycle2;

import org.junit.jupiter.api.Test;

import java.util.List;

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
}
