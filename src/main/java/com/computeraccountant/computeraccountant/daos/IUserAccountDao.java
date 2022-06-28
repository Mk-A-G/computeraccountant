package com.computeraccountant.computeraccountant.daos;

import com.computeraccountant.computeraccountant.models.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserAccountDao extends JpaRepository<UserAccount, String> {
  public List<UserAccount> findAllByUsername(String username);
}
