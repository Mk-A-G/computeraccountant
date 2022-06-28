package com.computeraccountant.computeraccountant.processors;

import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import com.computeraccountant.computeraccountant.models.BankFileLocations;
import com.computeraccountant.computeraccountant.models.Transaction;
import com.computeraccountant.computeraccountant.utils.DataAdapterUtil;
import com.computeraccountant.computeraccountant.utils.ExcelExtractor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ExcelProcessor {

  public List<Transaction> extractAllDataAndPersist(BankFileLocations bankName,String location)
      throws ProcessingException {
    Map<Integer, ArrayList<String>> data = ExcelExtractor.extractRawData(location).orElseThrow(() -> new ProcessingException("nodata?"));
    switch (bankName) {

      case HALIFAX:

        return DataAdapterUtil.adaptHalifaxBank(data);

      case MONZO:

        return DataAdapterUtil.adaptMonzoBank(data);

      default:

        throw new ProcessingException("Unknown bank. will implement soon");

    }
  }
}

