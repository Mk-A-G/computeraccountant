package com.computeraccountant.computeraccountant.utils;

import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import com.computeraccountant.computeraccountant.models.BankFileLocations;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;


class ExcelExtractorTest {

ExcelExtractor underTest =new ExcelExtractor();

  @ParameterizedTest
  @EnumSource(BankFileLocations.class)
  void testExtractRawData(BankFileLocations bankFileLocations) throws ProcessingException {
    Optional<Map<Integer, ArrayList<String>>> resultOptional = underTest.extractRawData(
        bankFileLocations.locations);
    Assertions.assertTrue(resultOptional.isPresent());
  }

  @Test
  void testLel() throws ProcessingException {
    Optional<Map<Integer, ArrayList<String>>> resultOptional = underTest.extractRawData(
        "D:/markg/Downloads/11133760_20212429_0203_test.xlsx");
    Assertions.assertTrue(resultOptional.isPresent());
  }

    @Test
  void testProcessingException() {
    Assertions.assertThrows(ProcessingException.class, () -> {
      underTest.extractRawData( "invalid location");
    });
  }


}