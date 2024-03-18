package com.example.cycle4;

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
    return busStops.get(0);
  }

}
