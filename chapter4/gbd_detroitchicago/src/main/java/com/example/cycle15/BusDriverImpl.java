package com.example.cycle15;

import java.util.Set;

public class BusDriverImpl implements BusDriver {
  private final BusRoute busRoute;
  private BusStop currentBusStop;
  private Set<Rumor> rumors;

  public BusDriverImpl(BusRoute busRoute, Set<Rumor> rumors) {
    this.busRoute = busRoute;
    this.currentBusStop = busRoute.getFirstBusStop();
    this.currentBusStop.add(this);
    this.rumors = Set.copyOf(rumors);
  }

  public BusStop driveToNextBusStop() {
    this.currentBusStop.remove(this);
    this.currentBusStop = busRoute.getNextBusStop(this.currentBusStop);
    this.currentBusStop.add(this);
    return this.currentBusStop;
  }

  public BusStop getCurrentBusStop() {
    return this.currentBusStop;
  }

  public Set<Rumor> getRumors() {
    return rumors;
  }

  public void setRumors(Set<Rumor> rumors) {
    this.rumors = Set.copyOf(rumors);
  }
}
