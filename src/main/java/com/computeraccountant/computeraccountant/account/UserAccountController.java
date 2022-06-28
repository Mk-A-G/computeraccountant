package com.computeraccountant.computeraccountant.account;

import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import com.computeraccountant.computeraccountant.utils.Controllers;
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
public class UserAccountController implements Controllers {

  private final UserAccountService userAccountService;

  @Autowired
  private UserAccountController(UserAccountService userAccountService) {
    this.userAccountService = userAccountService;
  }

  private final Logger LOG = LoggerFactory.getLogger(UserAccountController.class);
  private static final String ACCOUNTS = "/accounts";



  @GetMapping(ACCOUNTS)
  public ResponseEntity<String> returnAccount() {
    return userAccountService.returnUsers();
  }

  @PostMapping(
      value = ACCOUNTS + "/add-new",
      headers = "Accept=application/json",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> putAccountsInTable(@RequestBody String request)
      throws ProcessingException {
    LOG.info("Incoming request{}", request);
    return userAccountService.saveUsers(request);
  }

  @GetMapping(ACCOUNTS + "/username")
  public ResponseEntity<String> returnAllAccountsByName(@RequestParam String username) {
    LOG.info("Returning transactions");
    return userAccountService.returnUsersWithUserName(username);
  }
}
