package com.example.cycle13;

import java.util.InputMismatchException;

public class MaxDrivenStopCountParserImpl implements MaxDrivenStopCountParser {

  @Override
  public int parse(String maxDrivenStopCountStr) {
    try {
      return Integer.parseInt(maxDrivenStopCountStr);
    } catch (NumberFormatException e) {
      throw new InputMismatchException("Invalid max driven stop count: " + maxDrivenStopCountStr);
    }
  }
}
