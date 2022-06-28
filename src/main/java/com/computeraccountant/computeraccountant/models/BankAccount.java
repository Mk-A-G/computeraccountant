package com.computeraccountant.computeraccountant.models;

import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table
@EqualsAndHashCode
@ToString
public class BankAccount {

  private BankAccount() {
  }

  @Id
  private String accountNumber;

  private String sortCode;

  private String username;
  private String bank;

  @OneToMany(mappedBy="accountNumber",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Transaction> transactionList;

  @Builder(access = AccessLevel.PUBLIC)
  public BankAccount(String accountNumber, String sortCode, String username, String bank,
      List<Transaction> transactionList) {
    this.accountNumber = accountNumber;
    this.sortCode = sortCode;
    this.username = username;
    this.bank = bank;
    this.transactionList = transactionList;
  }
}
