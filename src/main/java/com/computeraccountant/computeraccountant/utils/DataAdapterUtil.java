package com.computeraccountant.computeraccountant.utils;

import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import com.computeraccountant.computeraccountant.models.Transaction;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DataAdapterUtil {

  private DataAdapterUtil() {
  }

  public static List<Transaction> adaptHalifaxBank(Map<Integer, ArrayList<String>> data)
      throws ProcessingException {

    checkIfDataIsEmpty(data);
    ArrayList<Transaction> transactions = new ArrayList<>();
    for (Entry<Integer, ArrayList<String>> entrySet : data.entrySet()) {
      if (entrySet.getKey() == 0) {
        continue;
      }

      ArrayList<String> cellList = data.get(entrySet.getKey());

      Transaction transaction = Transaction.builder()
          .date(StringMappingUtil.mapDateToProstgresDate(cellList.get(0)))
          .sortCode(cellList.get(2).substring(1))
          .accountNumber("11133760").name(cellList.get(4)).build();
      if (!cellList.get(5).isEmpty()) {
        transaction.setTransactionAmount(new BigDecimal("-" + cellList.get(5)));
      } else {
        transaction.setTransactionAmount(new BigDecimal(cellList.get(6)));
      }
      transactions.add(transaction);
    }

    return transactions;
  }


  public static List<Transaction> adaptMonzoBank(Map<Integer, ArrayList<String>> data)
      throws ProcessingException {
    ArrayList<Transaction> transactions = new ArrayList<>();
    checkIfDataIsEmpty(data);

    for (Entry<Integer, ArrayList<String>> entrySet : data.entrySet()) {
      if (entrySet.getKey() == 0) {
        continue;
      }
      if (entrySet.getValue().get(1).equals("")) {
        break;
      }

      ArrayList<String> cellList = data.get(entrySet.getKey());

      Transaction transaction = Transaction.builder()
          .date(StringMappingUtil.mapDateToProstgresDate(cellList.get(1)))
          .sortCode("'04-00-04".substring(1))
          .accountNumber("28617113").name(cellList.get(4)).build();
      transaction.setTransactionAmount(new BigDecimal(cellList.get(7)));
      transactions.add(transaction);
    }
    return transactions;
  }

  static void checkIfDataIsEmpty(Map<Integer, ArrayList<String>> data) throws ProcessingException {
    if (data == null || data.isEmpty()) {
      throw new ProcessingException("no data bruh");
    }
  }
}