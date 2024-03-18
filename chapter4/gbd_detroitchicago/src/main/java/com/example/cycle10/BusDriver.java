package com.example.cycle10;

import java.util.Set;


public interface BusDriver {
  BusStop driveToNextBusStop();

  BusStop getCurrentBusStop();

  Set<Rumor> getRumors();

  void setRumors(Set<Rumor> rumors);
}
