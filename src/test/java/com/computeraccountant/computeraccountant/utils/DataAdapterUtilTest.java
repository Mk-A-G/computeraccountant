package com.computeraccountant.computeraccountant.utils;

import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import com.computeraccountant.computeraccountant.models.BankFileLocations;
import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataAdapterUtilTest {


  ExcelExtractor dao = new ExcelExtractor();

  @Test
  void testAdaptMonzoBank() throws Exception {
    Assertions.assertNotNull(
        DataAdapterUtil.adaptMonzoBank(extractData(BankFileLocations.MONZO.locations)));
  }

  @Test
  void testAdaptBank() throws Exception {

    Assertions.assertNotNull(
        DataAdapterUtil.adaptHalifaxBank(extractData(BankFileLocations.HALIFAX.locations)));


  }

  @Test
  void checkIfDataIsEmptyHalifax() throws ProcessingException {
    Assertions.assertThrows(ProcessingException.class,
        () -> DataAdapterUtil.adaptHalifaxBank(null));
  }

  @Test
  void checkIfDataIsEmptyMonzo() throws ProcessingException {
    Assertions.assertThrows(ProcessingException.class,
        () -> DataAdapterUtil.adaptMonzoBank(null));
  }

  private Map<Integer, ArrayList<String>> extractData(String fileLocation) throws Exception {
    return dao.extractRawData(fileLocation).orElseThrow(() -> new ProcessingException("message"));
  }
}