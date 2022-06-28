package com.computeraccountant.computeraccountant.account;

import com.computeraccountant.computeraccountant.daos.IUserAccountDao;
import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import com.computeraccountant.computeraccountant.models.UserAccount;
import com.computeraccountant.computeraccountant.utils.StringMappingUtil;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

  private final Logger LOG = LoggerFactory.getLogger(UserAccountService.class);

  private final IUserAccountDao userDao;

  @Autowired
  public UserAccountService(IUserAccountDao userDao) {
    this.userDao = userDao;
  }

  public ResponseEntity<String> returnUsers() {
    LOG.info("Returning all users");
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(StringMappingUtil.mapObjectToJSON(userDao.findAll()));
  }

  public ResponseEntity<String> saveUsers(String request) throws ProcessingException {
    LOG.info("Saving new users");
    Optional<UserAccount> transaction = Optional.ofNullable(
        StringMappingUtil.mapFromJson(request, UserAccount.class));

    userDao.save(transaction.orElseThrow(() -> new ProcessingException("")));
    return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON).body("Nice");
  }



  public ResponseEntity<String> returnUsersWithUserName(String userName) {
    LOG.info("Returning All accounts from username: {}", userName);
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(StringMappingUtil.mapObjectToJSON(userDao.findAllByUsername(userName)));
  }
}
