package com.example.cycle3;

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
  public BusStop getNextBusStop(BusStop currentBusStop) {
    return busStops.get(0);
  }
}
