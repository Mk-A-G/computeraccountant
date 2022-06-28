package com.computeraccountant.computeraccountant.daos;

import com.computeraccountant.computeraccountant.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBankAccountDao extends JpaRepository<BankAccount, String> {

}
