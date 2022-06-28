package com.computeraccountant.computeraccountant.transaction;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.computeraccountant.computeraccountant.models.BankFileLocations;
import com.computeraccountant.computeraccountant.models.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TransactionControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testRestTemplate;

  private final String LOCALHOST = "http://localhost:";
  private final String TRANSACTIONS = "/personal-Finance/v1/transactions";
  private final String TRANSACTION = "\"bank\":\"Halifax\",\"date\":\"12/11/2020\",\"name\":\"gunata la mela\",\"accountNumber\":\"11133760\",\"sortCode\":\"'11-00-74\",\"transactionAmount\":\"256.14\"}";


  @ParameterizedTest
  @EnumSource(BankFileLocations.class)
  void testTransactionsOneBankNotNull(BankFileLocations bk) {
    assertNotNull(this.testRestTemplate.getForObject(
        LOCALHOST + port + TRANSACTIONS + "/localFile/oneBank?bank=" + bk.bankName.toUpperCase()
            + "&fileLocation=" + bk.locations, String.class));
  }

  @ParameterizedTest
  @EnumSource(BankFileLocations.class)
  void testTransactionsOneBank(BankFileLocations bk) {
    assertThat(this.testRestTemplate.getForObject(
        LOCALHOST + port + TRANSACTIONS + "/localFile/oneBank?bank=" + bk.bankName.toUpperCase()
            + "&fileLocation=" + bk.locations, String.class)).contains("accountNumber");
  }

  @Test
  void testReturnAllTransactionsFromDBNotNull() {
    assertNotNull(this.testRestTemplate.getForObject(
        LOCALHOST + port + TRANSACTIONS, String.class));
  }

  @Test
  void testReturnAllTransactionsFromDB() {
    assertThat(this.testRestTemplate.getForObject(
        LOCALHOST + port + TRANSACTIONS, String.class)).contains("11133760").contains("'04-00-04");
  }

  @Test
  void testReturnAllTransactionsByDateNotNull() {
    assertNotNull(this.testRestTemplate.getForObject(
        LOCALHOST + port + TRANSACTIONS + "/date?date=12/11/2017", String.class));
  }

  @Test
  void testReturnAllTransactionsByDate() {
    assertThat(this.testRestTemplate.getForObject(
        LOCALHOST + port + TRANSACTIONS + "/date?date=12/11/2017", String.class)).contains("12/11/2017");
  }

  @Test
  void testPutTransactionsNotNull() throws JsonProcessingException {
    assertNotNull(this.testRestTemplate.postForObject(
        LOCALHOST + port + TRANSACTIONS + "/addNew", generateTransaction(), String.class));
  }

  private String generateTransaction() throws JsonProcessingException {

    Transaction transaction = Transaction.builder().transactionAmount(Double.valueOf("256.14"))
        .accountNumber("11133760").date("12/11/2017").name("gunata la mela").sortCode("'11-00-74")
        .build();

    ObjectMapper objectMapper = new ObjectMapper();

    return objectMapper.writeValueAsString(transaction);

  }

}