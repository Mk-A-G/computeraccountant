package com.computeraccountant.computeraccountant.daos;

import com.computeraccountant.computeraccountant.models.SummedUpTransactions;
import com.computeraccountant.computeraccountant.models.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItransactionDao extends JpaRepository<Transaction,Long> {
  public List<Transaction> findAllByDate(String date);

  @Query(value = "SELECT new com.computeraccountant.computeraccountant.models.SummedUpTransactions(t.accountNumber,SUM(t.transactionAmount),t.date) FROM Transaction t GROUP BY t.date,t.accountNumber")
  List<SummedUpTransactions> findAllSummedUpSplitByAccountAndDate();

  @Query(value = "SELECT new com.computeraccountant.computeraccountant.models.SummedUpTransactions(SUM(t.transactionAmount),t.date) FROM Transaction t GROUP BY t.date ORDER BY t.date ASC")
  List<SummedUpTransactions> findAllSummedUpSplitByDate();
}


