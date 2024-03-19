package com.example.cycle18;

import java.util.List;

public interface BusDriversParser {
  List<BusDriver> parse(final List<String> busDriverSpecs);
}

