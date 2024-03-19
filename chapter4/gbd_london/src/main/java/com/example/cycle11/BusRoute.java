package com.example.cycle11;

public interface BusRoute {
  BusStop getFirstBusStop();

  BusStop getNextBusStop(BusStop currentBusStop);
}

