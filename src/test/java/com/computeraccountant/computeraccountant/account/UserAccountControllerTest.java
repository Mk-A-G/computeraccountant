package com.computeraccountant.computeraccountant.account;

import static com.computeraccountant.computeraccountant.models.BankFileLocations.HALIFAX;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.computeraccountant.computeraccountant.models.BankAccount;
import com.computeraccountant.computeraccountant.models.Transaction;
import com.computeraccountant.computeraccountant.models.UserAccount;
import com.computeraccountant.computeraccountant.utils.StringMappingUtil;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserAccountControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testRestTemplate;

  private final String LOCALHOST = "http://localhost:";

  private final String ACCOUNTS = "/personal-Finance/v1/accounts";

  @Test
  void testReturnAccountNotNull() {
    assertNotNull(this.testRestTemplate.getForObject(
        LOCALHOST + port + ACCOUNTS, String.class));
  }

  @Test
  void testReturnAccount() {
    assertThat(this.testRestTemplate.getForObject(
        LOCALHOST + port + ACCOUNTS, String.class)).contains("Mark");
  }

  @Test
  void testPutAccountsInTableNotNull() {
    assertNotNull(this.testRestTemplate.postForObject(
        LOCALHOST + port + ACCOUNTS + "/add-new", generateAccount(), String.class));
  }

  @Test
  void testReturnAllAccountsByNameNotNull() {
    assertNotNull(this.testRestTemplate.getForObject(
        LOCALHOST + port + ACCOUNTS + "/username?username=MkFunnyGuy", String.class));
  }

  @Test
  void testReturnAllAccountsByName() {
    assertThat(this.testRestTemplate.getForObject(
        LOCALHOST + port + ACCOUNTS + "/username?username=MkFunnyGuy" , String.class)).contains("Mark");
  }

  private Object generateAccount() {
    BankAccount account = BankAccount.builder().transactionList(List.of(Transaction.builder().build())).bank(HALIFAX.bankName).accountNumber("11133760").username("MkFunnyGuy1").sortCode("11133760").build();
    UserAccount userAccount = UserAccount.builder().bankAccounts(Set.of(account)).name("Mark").surname("Gyimah").username("MkFunnyGuy1").build();
    return StringMappingUtil.mapObjectToJSON(userAccount);
  }
}