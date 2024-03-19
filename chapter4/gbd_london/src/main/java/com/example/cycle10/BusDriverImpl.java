package com.example.cycle10;

import java.util.Set;

public class BusDriverImpl implements BusDriver {
  private final BusRoute busRoute;
  private Set<Rumor> rumors;
  private BusStop currentBusStop;

  public BusDriverImpl(final BusRoute busRoute, final Set<Rumor> rumors) {
    this.busRoute = busRoute;
    this.rumors = Set.copyOf(rumors);
    currentBusStop = busRoute.getFirstBusStop();
    currentBusStop.add(this);
  }

  @Override
  public BusStop driveToNextBusStop() {
    currentBusStop.remove(this);
    currentBusStop = busRoute.getNextBusStop(currentBusStop);
    currentBusStop.add(this);
    return currentBusStop;
  }

  @Override
  public BusStop getCurrentBusStop() {
    return currentBusStop;
  }

  @Override
  public Set<Rumor> getRumors() {
    return Set.copyOf(rumors);
  }

  @Override
  public void setRumors(Set<Rumor> rumors) {
    this.rumors = Set.copyOf(rumors);
  }
}



