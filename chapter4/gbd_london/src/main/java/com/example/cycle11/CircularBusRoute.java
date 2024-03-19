package com.example.cycle11;

import java.util.List;

public class CircularBusRoute implements BusRoute {
  private final List<BusStop> busStops;

  public CircularBusRoute(final List<BusStop> busStops) {
    if (busStops.isEmpty()) {
      throw new IllegalArgumentException("Bus route must have at least one bus stop");
    }

    this.busStops = List.copyOf(busStops);
  }

  @Override
  public BusStop getFirstBusStop() {
    return busStops.get(0);
  }

  @Override
  public BusStop getNextBusStop(final BusStop currentBusStop) {
    if (!busStops.contains(currentBusStop)) {
      throw new IllegalArgumentException("Bus stop does not belong to bus route");
    }

    final int currentIndex = busStops.indexOf(currentBusStop);
    final int nextIndex = (currentIndex + 1) % busStops.size();
    return busStops.get(nextIndex);
  }
}

