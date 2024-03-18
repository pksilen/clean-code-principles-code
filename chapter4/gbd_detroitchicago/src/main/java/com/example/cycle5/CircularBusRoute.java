package com.example.cycle5;

import java.util.List;

public class CircularBusRoute implements BusRoute {
  private final List<BusStop> busStops;

  public CircularBusRoute(final List<BusStop> busStops) {
    if (busStops == null || busStops.isEmpty()) {
      throw new IllegalArgumentException("Bus route must have at least one bus stop");
    }
    this.busStops = List.copyOf(busStops);
  }

  @Override
  public BusStop getNextBusStop(final BusStop currentBusStop) {
    if (!busStops.contains(currentBusStop)) {
      throw new IllegalArgumentException("Bus stop does not belong to bus route");
    }

    if (busStops.size() == 1) {
      return busStops.get(0);
    }

    int currBusStopIndex = busStops.indexOf(currentBusStop);
    return busStops.get(currBusStopIndex + 1);
  }
}
