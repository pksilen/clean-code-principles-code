package com.example.cycle2;

import java.util.Set;

public interface BusDriver {
  BusStop driveToNextBusStop();

  Set<Rumor> getRumors();
}

