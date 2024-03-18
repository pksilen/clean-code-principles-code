package com.example.cycle4;


public interface BusRoute {
  BusStop getNextBusStop(final BusStop currentBusStop);
}
