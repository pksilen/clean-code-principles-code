package com.example.cycle14;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GossipingBusDriversTests {
  final Rumor rumor1 = new Rumor();
  final Rumor rumor2 = new Rumor();
  final Set<Rumor> allRumors = Set.of(rumor1, rumor2);

  @Test
  void testDriveUntilAllRumorsShared_afterOneStop() {
    // GIVEN
    final var busStop = new BusStopImpl();
    final var busRoute = new CircularBusRoute(List.of(busStop));
    final var busDriver1 = new BusDriverImpl(busRoute, Set.of(rumor1));
    final var busDriver2 = new BusDriverImpl(busRoute, Set.of(rumor2));
    final var gossipingBusDrivers = new GossipingBusDrivers(List.of(busDriver1, busDriver2));

    // WHEN
    boolean allRumorsWereShared = gossipingBusDrivers.driveUntilAllRumorsShared(100);

    // THEN
    assertTrue(allRumorsWereShared);
    assertEquals(busDriver1.getRumors(), allRumors);
    assertEquals(busDriver2.getRumors(), allRumors);
  }

  @Test
  void testDriveUntilAllRumorsShared_afterTwoStops() {
    // GIVEN
    final var busStopA = new BusStopImpl();
    final var busStopB = new BusStopImpl();
    final var busStopC = new BusStopImpl();
    final var busRoute1 = new CircularBusRoute(List.of(busStopA, busStopC));
    final var busRoute2 = new CircularBusRoute(List.of(busStopB, busStopC));
    final var busDriver1 = new BusDriverImpl(busRoute1, Set.of(rumor1));
    final var busDriver2 = new BusDriverImpl(busRoute2, Set.of(rumor2));

    final var gossipingBusDrivers = new GossipingBusDrivers(List.of(busDriver1, busDriver2));

    // WHEN
    boolean allRumorsWereShared = gossipingBusDrivers.driveUntilAllRumorsShared(100);

    // THEN
    assertTrue(allRumorsWereShared);
    assertEquals(busDriver1.getRumors(), allRumors);
    assertEquals(busDriver2.getRumors(), allRumors);
  }

  @Test
  void testDriveUntilAllRumorsShared_whenRumorsAreNotShared() {
    // GIVEN
    final var busStopA = new BusStopImpl();
    final var busStopB = new BusStopImpl();
    final var busRoute1 = new CircularBusRoute(List.of(busStopA));
    final var busRoute2 = new CircularBusRoute(List.of(busStopB));
    final var busDriver1 = new BusDriverImpl(busRoute1, Set.of(rumor1));
    final var busDriver2 = new BusDriverImpl(busRoute2, Set.of(rumor2));
    final var gossipingBusDrivers = new GossipingBusDrivers(List.of(busDriver1, busDriver2));
    final int maxDrivenStopCount = 2;

    // WHEN
    boolean allRumorsWereShared = gossipingBusDrivers.driveUntilAllRumorsShared(maxDrivenStopCount);

    // THEN
    assertFalse(allRumorsWereShared);
    assertEquals(busDriver1.getRumors(), Set.of(rumor1));
    assertEquals(busDriver2.getRumors(), Set.of(rumor2));
  }
}

