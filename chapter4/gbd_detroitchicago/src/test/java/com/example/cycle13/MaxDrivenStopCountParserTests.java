package com.example.cycle13;

import com.example.cycle12.MaxDrivenStopCountParserImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxDrivenStopCountParserTests {

  @Test
  void testParse_whenItSucceeds() {
    // GIVEN
    final var maxDrivenStopCountStr = "2";
    final var parser = new MaxDrivenStopCountParserImpl();

    // WHEN
    final var maxDrivenStopCount = parser.parse(maxDrivenStopCountStr);

    // THEN
    assertEquals(2, maxDrivenStopCount);
  }
}
