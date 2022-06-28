package com.computeraccountant.computeraccountant.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SummedUpTransactions implements Serializable {
  @JsonInclude(Include.NON_NULL)
  public String accountNumber;
  public BigDecimal totalSum;

  public Date date;


    public SummedUpTransactions(BigDecimal totalSum, Date date) {
      this.totalSum = totalSum;
      this.date = date;
    }

    public SummedUpTransactions(String accountNumber, BigDecimal totalSum, Date date) {
      this.accountNumber = accountNumber;
      this.totalSum = totalSum;
      this.date = date;
    }

}
