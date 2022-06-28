package com.computeraccountant.computeraccountant.transaction;

import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import com.computeraccountant.computeraccountant.utils.Controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController implements Controllers {

  private TransactionService transactionService;

  private final Logger LOG = LoggerFactory.getLogger(TransactionController.class);

  @Autowired
  private TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }


  @GetMapping("/transactions")
  public ResponseEntity<String> returnAllTransactionsFromDB() {
    LOG.info("Returning transactions");
    return transactionService.returnTransactions();
  }

  @GetMapping("/transactions/added")
  public ResponseEntity<String> returnAddedTransactionsFromDB() {
    LOG.info("Returning transactions");
    return transactionService.returnTransactionsAddedByDate();
  }

  @GetMapping("/transactions/added/accounts")
  public ResponseEntity<String> returnAddedByAccountsTransactionsFromDB() {
    LOG.info("Returning transactions");
    return transactionService.returnTransactionsAddedByDateAndBank();
  }

  @PostMapping(
      value = "/transactions/addNew",
      headers = "Accept=application/json",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> putTransactionInTable(@RequestBody String request)
      throws ProcessingException {
    LOG.info("Incoming request{}", request);
    return transactionService.saveTransaction(request);
  }

  @GetMapping("/transactions/localFile/oneBank")
  public ResponseEntity<String> returnTransactions(@RequestParam String bank,
      @RequestParam String fileLocation) throws ProcessingException {
    LOG.info("Returning transactions for bank {} and file location {}", bank, fileLocation);
    return transactionService.returnLocalTransactions(bank, fileLocation);
  }

  @GetMapping("/transactions/date")
  public ResponseEntity<String> returnAllTransactionsByDate(@RequestParam String date) {
    LOG.info("Returning transactions");
    return transactionService.returnTransactionsFromDate(date);
  }


}
