package com.example.cycle13;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BusStopImpl implements BusStop {
  private final Set<BusDriver> busDrivers = new HashSet<>();

  @Override
  public void shareRumorsWithDrivers() {
    final var allRumors = busDrivers.stream()
      .flatMap(driver -> driver.getRumors().stream())
      .collect(Collectors.toSet());

    for (final var busDriver : busDrivers) {
      busDriver.setRumors(allRumors);
    }
  }

  @Override
  public void add(final BusDriver busDriver) {
    busDrivers.add(busDriver);
  }

  @Override
  public void remove(final BusDriver busDriver) {
    busDrivers.remove(busDriver);
  }
}

