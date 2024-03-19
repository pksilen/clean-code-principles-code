package com.example.cycle1;

import java.util.Set;

public interface BusDriver {
  BusStop driveToNextBusStop();

  Set<Rumor> getRumors();
}

