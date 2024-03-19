package com.example.cycle4;

import java.util.Set;

public class BusDriverImpl implements BusDriver {
  private final Set<Rumor> rumors;

  public BusDriverImpl(final Set<Rumor> rumors) {
    this.rumors = Set.copyOf(rumors);
  }

  @Override
  public BusStop driveToNextBusStop() {
    return null;
  }

  @Override
  public Set<Rumor> getRumors() {
    return Set.copyOf(rumors);
  }
}

