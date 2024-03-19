package com.example.cycle7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CircularBusRouteTests {
  @Mock

  BusStop busStopMock1;
  @Mock
  BusStop busStopMock2;

  @Test
  void testConstructor_whenNoBusStops() {
    final List<BusStop> noBusStops = List.of();

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
    assertEquals(firstBusStop, busStopMock1);
  }
}

