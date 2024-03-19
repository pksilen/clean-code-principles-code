package com.example.cycle19;

import java.util.List;

public interface BusDriversParser {
  List<BusDriver> parse(final List<String> busDriverSpecs);
}

