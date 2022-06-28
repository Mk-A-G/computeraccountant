package com.computeraccountant.computeraccountant.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class UserAccount implements Serializable {

  private UserAccount() {
  }

  @Id
  private String username;
  private String name;

  private String surname;

  private Date creationDate;

  @OneToMany(mappedBy="username")
  private Set<BankAccount> bankAccounts;


  @Builder(access = AccessLevel.PUBLIC)

  public UserAccount(String username, String name, String surname, Date creationDate,
      Set<BankAccount> bankAccounts) {
    this.username = username;
    this.name = name;
    this.surname = surname;
    this.creationDate = creationDate;
    this.bankAccounts = bankAccounts;
  }
}
