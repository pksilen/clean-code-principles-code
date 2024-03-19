package com.example.cycle14;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


class BusDriversParserTests {

  @Test
  void testParse_withOneDriverOneBusStopAndOneRumor() {
    // GIVEN
    final var busDriverSpec = "bus-stop-a;rumor1";
    final var parser = new BusDriversParserImpl();

    // WHEN
    final var busDrivers = parser.parse(List.of(busDriverSpec));

    // THEN
    assertHasCircularBusRouteWithOneStop(busDrivers);
    assertEquals(1, busDrivers.get(0).getRumors().size());
  }

  private void assertHasCircularBusRouteWithOneStop(final List<BusDriver> busDrivers) {
    assertEquals(1, busDrivers.size());

    final var busDriver = busDrivers.get(0);
    final var originalBusStop = busDriver.getCurrentBusStop();
    final var nextBusStop = busDriver.driveToNextBusStop();

    assertSame(originalBusStop, nextBusStop);
  }
}

