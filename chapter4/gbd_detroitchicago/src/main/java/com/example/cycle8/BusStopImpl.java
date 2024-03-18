package com.example.cycle8;

import java.util.HashSet;
import java.util.Set;

public class BusStopImpl implements BusStop {
  private final Set<BusDriver> busDrivers = new HashSet<>();

  @Override
  public void shareRumorsWithDrivers() {
    final Set<Rumor> allRumors = new HashSet<>();

    for (final BusDriver busDriver : busDrivers) {
      allRumors.addAll(busDriver.getRumors());
    }

    for (final BusDriver busDriver : busDrivers) {
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
  
  public int getBusDriverCount() {
    return busDrivers.size();
  }
}

