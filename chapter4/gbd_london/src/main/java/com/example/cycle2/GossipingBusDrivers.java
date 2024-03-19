package com.example.cycle2;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GossipingBusDrivers {
  private final List<BusDriver> busDrivers;
  private final Set<Rumor> allRumors;

  public GossipingBusDrivers(List<BusDriver> busDrivers) {
    this.busDrivers = List.copyOf(busDrivers);
    this.allRumors = getAllRumors();
  }

  public boolean driveUntilAllRumorsShared() {
    while (true) {
      final var busStops = busDrivers.stream()
        .map(BusDriver::driveToNextBusStop)
        .collect(Collectors.toSet());

      for (final var busStop : busStops) {
        busStop.shareRumorsWithDrivers();
      }

      if (allRumorsAreShared()) {
        return true;
      }
    }
  }

  private Set<Rumor> getAllRumors() {
    return busDrivers.stream()
      .flatMap(driver -> driver.getRumors().stream())
      .collect(Collectors.toSet());
  }

  private boolean allRumorsAreShared() {
    return busDrivers.stream()
      .allMatch(driver -> {
        final var rumors = driver.getRumors();
        return rumors.equals(allRumors);
      });
  }
}


