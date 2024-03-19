package com.example.cycle7;

import java.util.Set;

public interface BusDriver {
  BusStop driveToNextBusStop();

  BusStop getCurrentBusStop();

  Set<Rumor> getRumors();

  void setRumors(Set<Rumor> rumors);
}


