package com.example.cycle17;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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

  @Test
  void testParse_withMultipleDriversDifferentBusStopAndRumor() {
    // GIVEN
    final var busDriverSpec1 = "bus-stop-a;rumor1";
    final var busDriverSpec2 = "bus-stop-b;rumor2";
    final var parser = new BusDriversParserImpl();

    // WHEN
    final var busDrivers = parser.parse(List.of(busDriverSpec1, busDriverSpec2));

    // THEN
    assertBusStopsAreNotSame(busDrivers);
    assertNotEquals(busDrivers.get(0).getRumors(), busDrivers.get(1).getRumors());
  }

  @Test
  void testParse_withMultipleDriversWithCommonBusStop() {
    // GIVEN
    final var busDriverSpec1 = "bus-stop-a;rumor1";
    final var busDriverSpec2 = "bus-stop-a;rumor2";
    final var parser = new BusDriversParserImpl();

    // WHEN
    final var busDrivers = parser.parse(List.of(busDriverSpec1, busDriverSpec2));

    // THEN
    assertBusStopsAreSame(busDrivers);
  }

  @Test
  void testParse_withMultipleDriversAndCommonRumor() {
    // GIVEN
    final var busDriverSpec1 = "bus-stop-a;rumor1";
    final var busDriverSpec2 = "bus-stop-b;rumor1";
    final var parser = new BusDriversParserImpl();

    // WHEN
    final var busDrivers = parser.parse(List.of(busDriverSpec1, busDriverSpec2));

    // THEN
    assertEquals(busDrivers.get(0).getRumors(), busDrivers.get(1).getRumors());
  }

  private void assertHasCircularBusRouteWithOneStop(final List<BusDriver> busDrivers) {
    assertEquals(1, busDrivers.size());
    final var busDriver = busDrivers.get(0);
    final var originalBusStop = busDriver.getCurrentBusStop();
    final var nextBusStop = busDriver.driveToNextBusStop();
    assertSame(originalBusStop, nextBusStop);
  }

  private void assertBusStopsAreNotSame(final List<BusDriver> busDrivers) {
    assertEquals(2, busDrivers.size());
    final var driver1Stop1 = busDrivers.get(0).getCurrentBusStop();
    final var driver2Stop1 = busDrivers.get(1).getCurrentBusStop();
    assertNotSame(driver1Stop1, driver2Stop1);
  }

  private void assertBusStopsAreSame(final List<BusDriver> busDrivers) {
    final var driver1Stop = busDrivers.get(0).getCurrentBusStop();
    final var driver2Stop = busDrivers.get(1).getCurrentBusStop();
    assertSame(driver1Stop, driver2Stop);
  }
}

