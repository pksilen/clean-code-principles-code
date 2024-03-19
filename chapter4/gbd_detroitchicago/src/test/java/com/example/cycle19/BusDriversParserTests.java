package com.example.cycle19;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class BusDriversParserTests {

  @Test
  void testParse_withOneDriverOneBusStopAndOneRumor() {
    // GIVEN
    final var busDriverSpec = "bus-stop-a;rumor1";
    final var parser = new com.example.cycle18.BusDriversParserImpl();

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
    final var parser = new com.example.cycle18.BusDriversParserImpl();

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
    final var parser = new com.example.cycle18.BusDriversParserImpl();

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
    final var parser = new com.example.cycle18.BusDriversParserImpl();

    // WHEN
    final var busDrivers = parser.parse(List.of(busDriverSpec1, busDriverSpec2));

    // THEN
    assertEquals(busDrivers.get(0).getRumors(), busDrivers.get(1).getRumors());
  }

  @Test
  void testParse_withMultipleDriversAndMultipleBusStopsWhereFirstIsCommon() {
    // GIVEN
    final var busDriverSpec1 = "bus-stop-a,bus-stop-b;rumor1";
    final var busDriverSpec2 = "bus-stop-a,bus-stop-c;rumor2";
    final var parser = new BusDriversParserImpl();

    // WHEN
    final var busDrivers = parser.parse(List.of(busDriverSpec1, busDriverSpec2));

    // THEN
    assertOnlyFirstBusStopIsSame(busDrivers);
  }

  @Test
  void testParse_withMultipleDriversAndMultipleRumors() {
    // GIVEN
    final var busDriverSpec1 = "bus-stop-a;rumor1,rumor2,rumor3";
    final var busDriverSpec2 = "bus-stop-b;rumor1,rumor3";
    final var parser = new BusDriversParserImpl();

    // WHEN
    final var busDrivers = parser.parse(List.of(busDriverSpec1, busDriverSpec2));

    // THEN
    assertRumorsDifferByOne(busDrivers);
  }

  private void assertHasCircularBusRouteWithOneStop(final List<com.example.cycle18.BusDriver> busDrivers) {
    assertEquals(1, busDrivers.size());
    final var busDriver = busDrivers.get(0);
    final var originalBusStop = busDriver.getCurrentBusStop();
    final var nextBusStop = busDriver.driveToNextBusStop();
    assertSame(originalBusStop, nextBusStop);
  }

  private void assertBusStopsAreNotSame(final List<com.example.cycle18.BusDriver> busDrivers) {
    assertEquals(2, busDrivers.size());
    final var driver1Stop1 = busDrivers.get(0).getCurrentBusStop();
    final var driver2Stop1 = busDrivers.get(1).getCurrentBusStop();
    assertNotSame(driver1Stop1, driver2Stop1);
  }

  private void assertBusStopsAreSame(final List<com.example.cycle18.BusDriver> busDrivers) {
    final var driver1Stop = busDrivers.get(0).getCurrentBusStop();
    final var driver2Stop = busDrivers.get(1).getCurrentBusStop();
    assertSame(driver1Stop, driver2Stop);
  }

  private void assertOnlyFirstBusStopIsSame(final List<BusDriver> busDrivers) {
    final var driver1Stop1 = busDrivers.get(0).getCurrentBusStop();
    final var driver2Stop1 = busDrivers.get(1).getCurrentBusStop();
    assertSame(driver1Stop1, driver2Stop1);

    final var driver1Stop2 = busDrivers.get(0).driveToNextBusStop();
    final var driver2Stop2 = busDrivers.get(1).driveToNextBusStop();
    assertNotSame(driver1Stop2, driver2Stop2);
  }

  private void assertRumorsDifferByOne(final List<BusDriver> busDrivers) {
    assertEquals(3, busDrivers.get(0).getRumors().size());
    assertEquals(2, busDrivers.get(1).getRumors().size());
    final var rumorDiff = new HashSet<>(busDrivers.get(0).getRumors());
    rumorDiff.removeAll(busDrivers.get(1).getRumors());
    assertEquals(1, rumorDiff.size());
  }
}

