package com.computeraccountant.computeraccountant.account;

import static com.computeraccountant.computeraccountant.models.BankFileLocations.HALIFAX;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.computeraccountant.computeraccountant.daos.IUserAccountDao;
import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import com.computeraccountant.computeraccountant.models.BankAccount;
import com.computeraccountant.computeraccountant.models.Transaction;
import com.computeraccountant.computeraccountant.models.UserAccount;
import com.computeraccountant.computeraccountant.utils.StringMappingUtil;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

  UserAccountService userAccountService;

  @BeforeEach
  void init(@Mock IUserAccountDao accountDao) {

    userAccountService = new UserAccountService(accountDao);
    Mockito.lenient().when(accountDao.findAll()).thenReturn(List.of(generateAccount()));
    UserAccount userAccount = generateAccount();
    Mockito.lenient().when(accountDao.save(userAccount)).thenReturn(userAccount);
    Mockito.lenient().when(accountDao.findAllByUsername(Mockito.anyString())).thenReturn(List.of(generateAccount()));

     }

  @Test
  void returnAccounts() {

    ResponseEntity<String> response = userAccountService.returnUsers();
    assertThat(response.getBody()).contains("Mark");
  }

  @Test
  void saveAccount() throws ProcessingException {

    ResponseEntity<String> response = userAccountService.saveUsers(StringMappingUtil.mapObjectToJSON(generateAccount()));
    assertThat(response.getBody()).contains("Nice");
  }

  @Test
  void returnAccountsWithUserName() {

    ResponseEntity<String> account = userAccountService.returnUsersWithUserName("MkFunnyGuy1");
    assertThat(account.getBody()).contains("Mark");
  }

  private UserAccount generateAccount() {
    BankAccount account = BankAccount.builder().transactionList(List.of(Transaction.builder().build())).bank(HALIFAX.bankName).accountNumber("11133760").username("MkFunnyGuy").sortCode("11133760").build();
    return  UserAccount.builder().bankAccounts(Set.of(account)).name("Mark").surname("Gyimah").username("MkFunnyGuy1").build();

  }
}