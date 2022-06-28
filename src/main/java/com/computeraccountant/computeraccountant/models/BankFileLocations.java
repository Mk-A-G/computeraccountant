package com.computeraccountant.computeraccountant.models;

import lombok.ToString;

@ToString
public enum BankFileLocations {
  MONZO("Monzo","D:/markg/Downloads/MonzoTransactions.xlsx"),
  HALIFAX("Halifax","D:/markg/Downloads/11133760_20212429_0203.xlsx");

  public String bankName;
  public String locations;

  BankFileLocations(String bankName, String locations) {
    this.bankName = bankName;
    this.locations = locations;
  }






}
