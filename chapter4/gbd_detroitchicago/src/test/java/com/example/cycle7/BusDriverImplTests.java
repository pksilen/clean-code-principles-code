package com.example.cycle7;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusDriverImplTests {
  final Rumor rumor1 = new Rumor();
  final Rumor rumor2 = new Rumor();

  @Test
  void testGetRumors() {
    // GIVEN
    final var busDriver = new BusDriverImpl(
      new CircularBusRoute(List.of(new BusStopImpl())), Set.of(rumor1, rumor2)
    );

    // WHEN
    final var rumors = busDriver.getRumors();

    // THEN
    assertEquals(rumors, Set.of(rumor1, rumor2));
  }

  @Test
  void testSetRumors() {
    // GIVEN
    final var rumor3 = new Rumor();
    final var rumor4 = new Rumor();

    final var busDriver = new BusDriverImpl(
      new CircularBusRoute(List.of(new BusStopImpl())), Set.of(rumor1, rumor2)
    );

    // WHEN
    busDriver.setRumors(Set.of(rumor3, rumor4));

    // THEN
    assertEquals(busDriver.getRumors(), Set.of(rumor3, rumor4));
  }
}
