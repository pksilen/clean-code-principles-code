package com.example.cycle15;

import java.util.List;
import java.util.Set;

public class BusDriversParserImpl implements BusDriversParser {

  @Override
  public List<BusDriver> parse(List<String> busDriverSpecs) {
    return busDriverSpecs.stream().map(this::getBusDriver).toList();
  }

  private BusDriver getBusDriver(String busDriverSpec) {
    return new BusDriverImpl(
      new CircularBusRoute(List.of(new BusStopImpl())),
      Set.of(new Rumor())
    );
  }
}

