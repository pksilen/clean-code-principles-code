package com.example.cycle8;

import com.example.cycle6.BusStop;
import com.example.cycle6.BusStopImpl;
import com.example.cycle6.CircularBusRoute;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

  @Test
  void testGetFirstBusStop() {
    // GIVEN
    final var busStopA = new BusStopImpl();
    final varbusStopB = new BusStopImpl();
    final var busRoute = new CircularBusRoute(List.of(busStopA, busStopB));

    // WHEN
    final var firstBusStop = busRoute.getFirstBusStop();

    / THEN
    assertSame(firstBusStop, busStopA);
  }

  @Test
  void testGetNextBusStop_whenOneBusStop() {
    // GIVEN
    BusStop busStop = new BusStopImpl();
    CircularBusRoute busRoute = new CircularBusRoute(List.of(busStop));

    // WHEN
    BusStop nextBusStop = busRoute.getNextBusStop(busStop);

    // THEN
    assertEquals(nextBusStop, busStop);
  }

  @Test
  void testGetNextBusStop_whenBusStopDoesNotBelongToRoute() {
    // GIVEN
    BusStop busStopA = new BusStopImpl();
    BusStop busStopB = new BusStopImpl();
    CircularBusRoute busRoute = new CircularBusRoute(List.of(busStopA));

    // WHEN + THEN
    assertThrows(IllegalArgumentException.class, () -> {
      busRoute.getNextBusStop(busStopB);
    }, "Bus stop does not belong to bus route");
  }

  @Test
  void testGetNextBusStop_whenNextBusStopInListExists() {
    // GIVEN
    BusStop busStopA = new BusStopImpl();
    BusStop busStopB = new BusStopImpl();
    CircularBusRoute busRoute = new CircularBusRoute(List.of(busStopA, busStopB));

    // WHEN
    BusStop nextBusStop = busRoute.getNextBusStop(busStopA);

    // THEN
    assertEquals(nextBusStop, busStopB);
  }

  @Test
  void testGetNextBusStop_whenNoNextBusStopInList() {
    // GIVEN
    BusStop busStopA = new BusStopImpl();
    BusStop busStopB = new BusStopImpl();
    CircularBusRoute busRoute = new CircularBusRoute(List.of(busStopA, busStopB));

    // WHEN
    BusStop nextBusStop = busRoute.getNextBusStop(busStopB);

    // THEN
    assertEquals(nextBusStop, busStopA);
  }
}

