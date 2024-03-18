package com.example.cycle14;

import java.util.List;

public class CircularBusRoute implements BusRoute {
  private final List<BusStop> busStops;
  private final int busStopCount;

  public CircularBusRoute(final List<BusStop> busStops) {
    if (busStops == null || busStops.isEmpty()) {
      throw new IllegalArgumentException("Bus route must have at least one bus stop");
    }

    this.busStops = List.copyOf(busStops);
    this.busStopCount = busStops.size();
  }

  public BusStop getFirstBusStop() {
    return busStops.get(0);
  }

  @Override
  public BusStop getNextBusStop(final BusStop currentBusStop) {
    if (!busStops.contains(currentBusStop)) {
      throw new IllegalArgumentException("Bus stop does not belong to bus route");
    }

    final int currBusStopIndex = busStops.indexOf(currentBusStop);
    final int nextBusStopIndex = (currBusStopIndex + 1) % busStopCount;
    return busStops.get(nextBusStopIndex);
  }
}

