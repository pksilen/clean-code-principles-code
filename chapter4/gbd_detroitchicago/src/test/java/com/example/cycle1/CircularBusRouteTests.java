package com.example.cycle1;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CircularBusRouteTests {

  @Test
  void testConstructor_whenNoBusStops() {
    final List<BusStop> emptyList = List.of();

    assertThrows(IllegalArgumentException.class, () -> {
      new CircularBusRoute(emptyList);
    }, "Bus route must have at least one bus stop");
  }
}
