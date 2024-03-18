package com.example.cycle11;


public interface BusStop {
  void shareRumorsWithDrivers();

  void add(final BusDriver busDriver);

  void remove(final BusDriver busDriver);
}