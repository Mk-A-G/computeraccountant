package com.computeraccountant.computeraccountant.models;

import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Entity
@Table
@ToString
public class Transaction {

  private Transaction() {
  }

  @SequenceGenerator(name = "transaction_sequence", sequenceName = "transaction_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
  @Id
  private Long id;

  private Date date;

  private String name;

  private String accountNumber;

  private String sortCode;

  private BigDecimal transactionAmount;

  @Builder(access = AccessLevel.PUBLIC)
  public Transaction(Date date, String name, String accountNumber, String sortCode,
      BigDecimal transactionAmount) {
    this.date = date;
    this.name = name;
    this.accountNumber = accountNumber;
    this.sortCode = sortCode;
    this.transactionAmount = transactionAmount;
  }
}



