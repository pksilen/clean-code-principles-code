package com.example.cycle17;

import com.example.cycle16.BusDriver;
import com.example.cycle16.BusDriverImpl;
import com.example.cycle16.BusStopImpl;
import com.example.cycle16.CircularBusRoute;
import com.example.cycle16.Rumor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusStopImplTests {

  @Test
  void testShareRumorsWithDrivers() {
    // GIVEN
    final var rumor1 = new com.example.cycle16.Rumor();
    final var rumor2 = new com.example.cycle16.Rumor();
    final var rumor3 = new Rumor();
    final var allRumors = Set.of(rumor1, rumor2, rumor3);

    final var busRoute = new com.example.cycle16.CircularBusRoute(List.of(new com.example.cycle16.BusStopImpl()));
    final var busDriver1 = new com.example.cycle16.BusDriverImpl(busRoute, Set.of(rumor1, rumor2));
    final var busDriver2 = new com.example.cycle16.BusDriverImpl(busRoute, Set.of(rumor2));
    final var busDriver3 = new com.example.cycle16.BusDriverImpl(busRoute, Set.of(rumor2, rumor3));

    final var busStop = new com.example.cycle16.BusStopImpl();
    busStop.add(busDriver1);
    busStop.add(busDriver2);
    busStop.add(busDriver3);

    // WHEN
    busStop.shareRumorsWithDrivers();

    // THEN
    for (final com.example.cycle16.BusDriver busDriver : List.of(busDriver1, busDriver2, busDriver3)) {
      assertEquals(busDriver.getRumors(), allRumors);
    }
  }

  @Test
  void testAdd() {
    // GIVEN
    final var busStop = new com.example.cycle16.BusStopImpl();
    com.example.cycle16.BusDriver busDriver = new com.example.cycle16.BusDriverImpl(new com.example.cycle16.CircularBusRoute(List.of(new com.example.cycle16.BusStopImpl())), Set.of());

    // WHEN
    busStop.add(busDriver);

    // THEN
    assertEquals(1, busStop.getBusDriverCount());
  }

  @Test
  void testRemove() {
    // GIVEN
    final var busStop = new com.example.cycle16.BusStopImpl();
    BusDriver busDriver = new BusDriverImpl(new CircularBusRoute(List.of(new BusStopImpl())), Set.of());
    busStop.add(busDriver);

    // WHEN
    busStop.remove(busDriver);

    // THEN
    assertEquals(0, busStop.getBusDriverCount());
  }
}

