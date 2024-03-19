package com.example.cycle11;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CircularBusRouteTests {
  @Mock
  BusStop busStopMock1;

  @Mock
  BusStop busStopMock2;

  @Test
  void testConstructor_whenNoBusStops() {
    // GIVEN
    final List<BusStop> noBusStops = List.of();

    // WHEN + THEN
    assertThrows(IllegalArgumentException.class, () -> {
      new CircularBusRoute(noBusStops);
    });
  }

  @Test
  void testGetFirstBusStop() {
    // GIVEN
    final var busStops = List.of(busStopMock1, busStopMock2);
    final var busRoute = new CircularBusRoute(busStops);

    // WHEN
    final var firstBusStop = busRoute.getFirstBusStop();

    // THEN
    assertSame(firstBusStop, busStopMock1);
  }

  @Test
  void testGetNextBusStop_whenOneBusStop() {
    // GIVEN
    final var busRoute = new CircularBusRoute(List.of(busStopMock1));

    // WHEN
    BusStop nextBusStop = busRoute.getNextBusStop(busStopMock1);

    // THEN
    assertSame(nextBusStop, busStopMock1);
  }

  @Test
  void testGetNextBusStop_whenBusStopDoesNotBelongToRoute() {
    // GIVEN
    final var busRoute = new CircularBusRoute(List.of(busStopMock1));

    // WHEN + THEN
    assertThrows(IllegalArgumentException.class, () -> {
      busRoute.getNextBusStop(busStopMock2);
    });
  }

  @Test
  void testGetNextBusStop_whenNextBusStopInListExists() {
    // GIVEN
    final var busRoute = new CircularBusRoute(List.of(busStopMock1, busStopMock2));

    // WHEN
    final var nextBusStop = busRoute.getNextBusStop(busStopMock1);

    // THEN
    assertSame(nextBusStop, busStopMock2);
  }

  @Test
  void testGetNextBusStop_whenNoNextBusStopInList() {
    // GIVEN
    final var busRoute = new CircularBusRoute(List.of(busStopMock1, busStopMock2));

    // WHEN
    BusStop nextBusStop = busRoute.getNextBusStop(busStopMock2);

    // THEN
    assertSame(nextBusStop, busStopMock1);
  }
}

