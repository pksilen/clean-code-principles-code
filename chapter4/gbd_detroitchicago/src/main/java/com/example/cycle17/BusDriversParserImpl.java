package com.example.cycle17;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BusDriversParserImpl implements BusDriversParser {
  private final Map<String, BusStop> nameToBusStop = new HashMap<>();
  private final Map<String, Rumor> nameToRumor = new HashMap<>();

  @Override
  public List<BusDriver> parse(List<String> busDriverSpecs) {
    return busDriverSpecs.stream().map(this::getBusDriver).toList();
  }

  private BusDriver getBusDriver(String busDriverSpec) {
    final var parts = busDriverSpec.split(";");
    final var busStopName = parts[0];
    final var rumorName = parts[1];
    nameToBusStop.computeIfAbsent(busStopName, key -> new BusStopImpl());
    nameToRumor.computeIfAbsent(rumorName, key -> new Rumor());

    return new BusDriverImpl(
      new CircularBusRoute(List.of(nameToBusStop.get(busStopName))),
      Set.of(nameToRumor.get(rumorName))
    );
  }
}



