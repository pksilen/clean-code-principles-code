package com.example.cycle17;

import com.example.cycle16.BusDriverImpl;
import com.example.cycle16.BusStopImpl;
import com.example.cycle16.CircularBusRoute;
import com.example.cycle16.Rumor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusDriverImplTests {
  final com.example.cycle16.Rumor rumor1 = new com.example.cycle16.Rumor();
  final com.example.cycle16.Rumor rumor2 = new com.example.cycle16.Rumor();

  @Test
  void testDriveToNextBusStop() {
    // GIVEN
    final var busStopA = new com.example.cycle16.BusStopImpl();
    final var busStopB = new com.example.cycle16.BusStopImpl();
    final var busRoute = new com.example.cycle16.CircularBusRoute(List.of(busStopA, busStopB));
    final var busDriver = new com.example.cycle16.BusDriverImpl(busRoute, Set.of());

    // WHEN
    busDriver.driveToNextBusStop();

    // THEN
    assertEquals(busDriver.getCurrentBusStop(), busStopB);
  }

  @Test
  void testGetRumors() {
    // GIVEN
    final var busDriver = new com.example.cycle16.BusDriverImpl(
      new com.example.cycle16.CircularBusRoute(List.of(new com.example.cycle16.BusStopImpl())), Set.of(rumor1, rumor2)
    );

    // WHEN
    final var rumors = busDriver.getRumors();

    // THEN
    assertEquals(rumors, Set.of(rumor1, rumor2));
  }

  @Test
  void testSetRumors() {
    // GIVEN
    final var rumor3 = new com.example.cycle16.Rumor();
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
