package com.computeraccountant.computeraccountant.transaction;

import static com.computeraccountant.computeraccountant.models.BankFileLocations.MONZO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.computeraccountant.computeraccountant.daos.ItransactionDao;
import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import com.computeraccountant.computeraccountant.models.BankFileLocations;
import com.computeraccountant.computeraccountant.models.Transaction;
import com.computeraccountant.computeraccountant.processors.ExcelProcessor;
import com.computeraccountant.computeraccountant.utils.StringMappingUtil;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {


  TransactionService transactionService;


  @BeforeEach
  void init(@Mock ItransactionDao transactionDao, @Mock ExcelProcessor excelProcessor)
      throws ProcessingException {

    transactionService = new TransactionService(excelProcessor, transactionDao);
    Mockito.lenient().when(transactionDao.findAll()).thenReturn(List.of(generateTransaction()));
    Transaction transaction = generateTransaction();
    Mockito.lenient().when(transactionDao.save(transaction)).thenReturn(transaction);
    Mockito.lenient().when(transactionDao.saveAll(Mockito.anyList())).thenReturn(null);
    Mockito.lenient().when(
        excelProcessor.extractAllDataAndPersist(Mockito.any(BankFileLocations.class),
            Mockito.anyString())).thenReturn(List.of(generateTransaction()));
    Mockito.lenient().when(transactionDao.findAllByDate(Mockito.anyString()))
        .thenReturn(List.of(generateTransaction()));

  }


  @Test
  void returnLocalTransactions() throws ProcessingException {
    ResponseEntity<String> response = transactionService.returnLocalTransactions(MONZO.bankName,
        MONZO.locations);
    assertThat(response.getBody()).contains("gunata la mela");
  }

  @Test
  void returnTransactions() {
    ResponseEntity<String> response = transactionService.returnTransactions();
    assertThat(response.getBody()).contains("gunata la mela");
  }

  @Test
  void returnTransactionsFromDate() {
    ResponseEntity<String> account = transactionService.returnTransactionsFromDate("MkFunnyGuy1");
    assertThat(account.getBody()).contains("12/11/2017");
  }

  @Test
  void saveTransaction() throws ProcessingException {
    ResponseEntity<String> response = transactionService.saveTransaction(
        StringMappingUtil.mapObjectToJSON(generateTransaction()));
    assertThat(response.getBody()).contains("Nice");
  }


  private Transaction generateTransaction() {

    return Transaction.builder().transactionAmount(Double.valueOf("256.14"))
        .accountNumber("11133760").date("12/11/2017").name("gunata la mela").sortCode("'11-00-74")
        .build();


  }
}