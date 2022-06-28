package com.computeraccountant.computeraccountant.account;

import com.computeraccountant.computeraccountant.daos.IBankAccountDao;
import com.computeraccountant.computeraccountant.daos.IUserAccountDao;
import com.computeraccountant.computeraccountant.daos.ItransactionDao;
import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import com.computeraccountant.computeraccountant.models.BankAccount;
import com.computeraccountant.computeraccountant.models.BankFileLocations;
import com.computeraccountant.computeraccountant.models.UserAccount;
import com.computeraccountant.computeraccountant.processors.ExcelProcessor;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAccountConfig {

  public final Logger LOG = LoggerFactory.getLogger(UserAccountConfig.class);

  @Bean
  @Qualifier("UserAccount")
  CommandLineRunner commandLineRunnerAccount(IUserAccountDao accountDao, IBankAccountDao bankAccountDao,
      ItransactionDao transactionDao,
      ExcelProcessor excelProcessor) {
    return args -> {
      LOG.info("Saving default account objects");
      Set<BankAccount> bankAccountSet = new HashSet<>();
      for (BankFileLocations bank : BankFileLocations.values()) {
        LOG.info("Inserting Default transactions for bank {} and file location {}", bank.bankName,
            bank.locations);
        try {
          bankAccountSet.add(BankAccount.builder().bank(bank.bankName)
              .accountNumber(excelProcessor.extractAllDataAndPersist(bank, bank.locations).get(0).getAccountNumber())
              .username("MkFunnyGuy")
              .sortCode(excelProcessor.extractAllDataAndPersist(bank, bank.locations).get(0).getSortCode())
              .transactionList(excelProcessor.extractAllDataAndPersist(bank, bank.locations))
              .build());

        } catch (ProcessingException e) {
          LOG.info("unable to read file {}", bank.locations);
        }
      }
      bankAccountDao.saveAll(bankAccountSet);
      java.util.Date now = new java.util.Date();
      java.sql.Date date = new java.sql.Date(now.getTime());
      accountDao.save(UserAccount.builder().bankAccounts(bankAccountSet).name("Mark").surname("Gyimah").creationDate(date)
          .username("MkFunnyGuy").build());

    };
  }

}
