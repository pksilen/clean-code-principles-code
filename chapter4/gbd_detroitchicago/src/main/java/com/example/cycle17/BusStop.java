package com.example.cycle17;


public interface BusStop {
  void shareRumorsWithDrivers();

  void add(final BusDriver busDriver);

  void remove(final BusDriver busDriver);
}