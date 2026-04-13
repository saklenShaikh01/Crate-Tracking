package com.khataBook.repository;
import com.khataBook.entity.AccountTransaction;
import com.khataBook.projection.AccountLedgerView;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountTransaction, Long> {

  // 🔥 Ledger
  @Query(value = """
  SELECT
  id,
  transaction_date as transactionDate,
  transaction_type as transactionType,
  amount,

  SUM(
  CASE
  WHEN transaction_type='CREDIT' THEN amount
  WHEN transaction_type='DEBIT' THEN -amount
  END
  ) OVER (ORDER BY transaction_date, id) as balance

  FROM account_transaction
  WHERE person_type=:type AND person_id=:id
  ORDER BY transaction_date, id
  """, nativeQuery = true)
  List<AccountLedgerView> getLedger(@Param("type") String type,
                                    @Param("id") Long id);


  // 🔥 Balance
  @Query(value = """
  SELECT COALESCE(SUM(
  CASE
  WHEN transaction_type='CREDIT' THEN amount
  WHEN transaction_type='DEBIT' THEN -amount
  END
  ),0)
  FROM account_transaction
  WHERE person_type=:type AND person_id=:id
  """, nativeQuery = true)
  Double getBalance(@Param("type") String type,
                    @Param("id") Long id);


  // 🔥 Customer Summary
  @Query(value = """
  SELECT
  person_id as id,

  SUM(
  CASE
  WHEN transaction_type='CREDIT' THEN amount
  WHEN transaction_type='DEBIT' THEN -amount
  END
  ) as balance

  FROM account_transaction
  WHERE person_type='CUSTOMER'
  GROUP BY person_id
  """, nativeQuery = true)
  List<Object[]> getCustomerSummary();
}
