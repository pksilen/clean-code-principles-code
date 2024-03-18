package com.example.cycle9;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    boolean allRumorsWereShared = gossipingBusDrivers.driveUntilAllRumorsShared();

    // THEN
    assertTrue(allRumorsWereShared);
    assertEquals(busDriver1.getRumors(), allRumors);
    assertEquals(busDriver2.getRumors(), allRumors);
  }
}

