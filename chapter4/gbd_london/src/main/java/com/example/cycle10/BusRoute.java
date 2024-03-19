package com.example.cycle10;

public interface BusRoute {
  BusStop getFirstBusStop();

  BusStop getNextBusStop(BusStop currentBusStop);
}

