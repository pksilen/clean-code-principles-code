package com.example.cycle4;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusDriverImplTests {
  final Rumor rumor1 = new Rumor();
  final Rumor rumor2 = new Rumor();

  @Test
  void testGetRumors() {
    // GIVEN
    final var initialRumors = new HashSet<>(Set.of(rumor1, rumor2));
    final var busDriver = new BusDriverImpl(initialRumors);

    // WHEN
    Set<Rumor> rumors = busDriver.getRumors();

    // THEN
    assertEquals(rumors, initialRumors);
  }
}

