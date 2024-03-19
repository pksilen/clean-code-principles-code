package com.example.cycle5;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusDriverImplTests {
  final Rumor rumor1 = new Rumor();
  final Rumor rumor2 = new Rumor();
  final Rumor rumor3 = new Rumor();
  final Rumor rumor4 = new Rumor();

  @Test
  void testGetRumors() {
    // GIVEN
    final var initialRumors = new HashSet<>(Set.of(rumor1, rumor2));
    final var busDriver = new BusDriverImpl(initialRumors);

    // WHEN
    final var rumors = busDriver.getRumors();

    // THEN
    assertEquals(rumors, initialRumors);
  }

  @Test
  void testSetRumors() {
    // GIVEN
    final var initialRumors = new HashSet<>(Set.of(rumor1, rumor2));
    final var newRumors = new HashSet<>(Set.of(rumor3, rumor4));
    final var busDriver = new BusDriverImpl(initialRumors);

    // WHEN
    busDriver.setRumors(newRumors);

    // THEN
    assertEquals(busDriver.getRumors(), newRumors);
  }
}


