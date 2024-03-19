package com.example.cycle17;

import com.example.cycle16.MaxDrivenStopCountParserImpl;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MaxDrivenStopCountParserTests {

  @Test
  void testParse_whenItSucceeds() {
    // GIVEN
    final var maxDrivenStopCountStr = "2";
    final var parser = new com.example.cycle16.MaxDrivenStopCountParserImpl();

    // WHEN
    final var maxDrivenStopCount = parser.parse(maxDrivenStopCountStr);

    // THEN
    assertEquals(2, maxDrivenStopCount);
  }

  @Test
  void testParse_whenItFails() {
    // GIVEN
    final var maxDrivenStopCountStr = "invalid";
    final var parser = new MaxDrivenStopCountParserImpl();

    // WHEN + THEN
    assertThrows(InputMismatchException.class, () -> {
      parser.parse(maxDrivenStopCountStr);
    });
  }
}
