package com.example.cycle19;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GossipingBusDrivers {
  private final List<BusDriver> busDrivers;
  private final Set<Rumor> allRumors;
  private int drivenStopCount = 0;

  public GossipingBusDrivers(List<BusDriver> busDrivers) {
    this.busDrivers = List.copyOf(busDrivers);
    this.allRumors = getAllRumors();
  }

  public boolean driveUntilAllRumorsShared(int maxDrivenStopCount) {
    while (true) {
      Set<BusStop> busStops = busDrivers.stream()
        .map(BusDriver::driveToNextBusStop)
        .collect(Collectors.toSet());

      drivenStopCount++;
      shareRumors(busStops);

      if (allRumorsAreShared()) {
        return true;
      } else if (drivenStopCount == maxDrivenStopCount) {
        return false;
      }
    }
  }

  private static void shareRumors(Set<BusStop> busStops) {
    for (final var busStop : busStops) {
      busStop.shareRumorsWithDrivers();
    }
  }

  private Set<Rumor> getAllRumors() {
    return busDrivers.stream()
      .flatMap(driver -> driver.getRumors().stream())
      .collect(Collectors.toSet());
  }

  private boolean allRumorsAreShared() {
    return busDrivers.stream()
      .allMatch(driver -> driver.getRumors().equals(allRumors));
  }
}
