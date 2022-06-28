package com.computeraccountant.computeraccountant.transaction;

import com.computeraccountant.computeraccountant.daos.ItransactionDao;
import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import com.computeraccountant.computeraccountant.models.BankFileLocations;
import com.computeraccountant.computeraccountant.models.Transaction;
import com.computeraccountant.computeraccountant.processors.ExcelProcessor;
import com.computeraccountant.computeraccountant.utils.StringMappingUtil;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private ExcelProcessor excelProcessor;
  private ItransactionDao transactionDao;
  private final Logger LOG = LoggerFactory.getLogger(TransactionService.class);

  @Autowired
  public TransactionService(ExcelProcessor excelProcessor, ItransactionDao transactionDao) {
    this.excelProcessor = excelProcessor;
    this.transactionDao = transactionDao;
  }

  public ResponseEntity<String> returnLocalTransactions(String bank,
      String fileLocation) throws ProcessingException {
    LOG.info("Returning transactions for bank {} and file location {}", bank, fileLocation);
    List<Transaction> transactions = excelProcessor.extractAllDataAndPersist(
        BankFileLocations.valueOf(bank.toUpperCase()), fileLocation);
    saveListInDB(transactions);
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(StringMappingUtil.mapObjectToJSON(transactions));
  }

  public ResponseEntity<String> returnTransactions() {
    LOG.info("Returning All transactions");
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(StringMappingUtil.mapObjectToJSON(transactionDao.findAll()));
  }

  public ResponseEntity<String> returnTransactionsFromDate(String date) {
    LOG.info("Returning All transactions from {}", date);
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(StringMappingUtil.mapObjectToJSON(transactionDao.findAllByDate(date)));
  }

  public ResponseEntity<String> returnTransactionsAddedByDate() {
    LOG.info("Returning All transactions added by date");
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(StringMappingUtil.mapObjectToJSON(transactionDao.findAllSummedUpSplitByDate()));
  }
  public ResponseEntity<String> returnTransactionsAddedByDateAndBank() {
    LOG.info("Returning All transactions added by date and Account");
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(StringMappingUtil.mapObjectToJSON(transactionDao.findAllSummedUpSplitByAccountAndDate()));
  }


  public ResponseEntity<String> saveTransaction(String request)
      throws ProcessingException {
    LOG.info("Saving transactions");
    Optional<Transaction> transaction = Optional.ofNullable(
        StringMappingUtil.mapFromJson(request, Transaction.class));

    transactionDao.save(transaction.orElseThrow(() -> new ProcessingException("")));
    return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON).body("Nice");
  }


  private void saveListInDB(List<Transaction> transactions) {
    LOG.info("Saving in DB................................................");
    transactionDao.saveAll(transactions);
  }


}