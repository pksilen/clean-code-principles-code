package com.example.cycle16;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BusDriversParserImpl implements BusDriversParser {
  private final Map<String, BusStop> nameToBusStop = new HashMap<>();

  @Override
  public List<BusDriver> parse(List<String> busDriverSpecs) {
    return busDriverSpecs.stream().map(this::getBusDriver).toList();
  }

  private BusDriver getBusDriver(final String busDriverSpec) {
    final var parts = busDriverSpec.split(";");
    final var busStopName = parts[0];
    nameToBusStop.computeIfAbsent(busStopName, key -> new BusStopImpl());

    return new BusDriverImpl(
      new CircularBusRoute(List.of(nameToBusStop.get(busStopName))),
      Set.of(new Rumor())
    );
  }
}


