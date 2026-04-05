package com.example.inventory_service.repository;


import com.example.inventory_service.Dto.CustomerPendingDTO;
import com.example.inventory_service.Dto.LedgerDTO;
import com.example.inventory_service.Dto.ReportDTO;
import com.example.inventory_service.Dto.ReportView;
import com.example.inventory_service.entity.CrateTransaction;
import com.example.inventory_service.entity.LedgerView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CrateTransactionRepository extends JpaRepository<CrateTransaction, Long> {

  List<CrateTransaction> findByPersonIdAndPersonType(Long personId, String personType);
  List<CrateTransaction> findByPersonType(String personType);
  List<CrateTransaction> findByTransactionDate(LocalDate date);
  @Query(value = """
SELECT
f.id as id,
f.name as name,
SUM(
CASE
WHEN ct.transaction_type='FARMER_TAKE' THEN ct.crate_count
WHEN ct.transaction_type='FARMER_RETURN' THEN -ct.crate_count
END
) as pendingCrates
FROM crate_transaction ct
JOIN farmer f ON f.id = ct.person_id
WHERE ct.person_type='FARMER'
GROUP BY f.id,f.name
""", nativeQuery = true)
  List<ReportView> getFarmerReport();
  @Query(value = """
SELECT
c.id as id,
c.name as name,
SUM(
CASE
WHEN ct.transaction_type='CUSTOMER_TAKE' THEN ct.crate_count
WHEN ct.transaction_type='CUSTOMER_RETURN' THEN -ct.crate_count
END
) as pendingCrates
FROM crate_transaction ct
JOIN customer c ON c.id = ct.person_id
WHERE ct.person_type='CUSTOMER'
GROUP BY c.id,c.name
""", nativeQuery = true)
  List<ReportView> getCustomerReport();

  @Query(value = """
SELECT
c.id as id,
c.name as name,
SUM(
CASE
WHEN ct.transaction_type='CUSTOMER_TAKE' THEN ct.crate_count
WHEN ct.transaction_type='CUSTOMER_RETURN' THEN -ct.crate_count
END
) as pendingCrates
FROM crate_transaction ct
JOIN customer c ON c.id = ct.person_id
WHERE ct.person_type='CUSTOMER'
GROUP BY c.id,c.name
HAVING SUM(
CASE
WHEN ct.transaction_type='CUSTOMER_TAKE' THEN ct.crate_count
WHEN ct.transaction_type='CUSTOMER_RETURN' THEN -ct.crate_count
END
) >= 5
""", nativeQuery = true)
  List<ReportView> getCustomerPendingFivePlus();

  @Query(value = """
SELECT
ct.transaction_date as transactionDate,
ct.transaction_type as transactionType,
ct.crate_count as crateCount,

SUM(
CASE
WHEN ct.transaction_type='FARMER_TAKE' THEN ct.crate_count
WHEN ct.transaction_type='FARMER_RETURN' THEN -ct.crate_count
END
) OVER (ORDER BY ct.transaction_date) as balance

FROM crate_transaction ct
WHERE ct.person_type='FARMER'
AND ct.person_id = :farmerId
ORDER BY ct.transaction_date
""", nativeQuery = true)
  List<LedgerView> getFarmerLedger(@Param("farmerId") Long farmerId);

  @Query(value = """
SELECT
ct.transaction_date as transactionDate,
ct.transaction_type as transactionType,
ct.crate_count as crateCount,

SUM(
CASE
WHEN ct.transaction_type='CUSTOMER_TAKE' THEN ct.crate_count
WHEN ct.transaction_type='CUSTOMER_RETURN' THEN -ct.crate_count
END
) OVER (ORDER BY ct.transaction_date) as balance

FROM crate_transaction ct
WHERE ct.person_type='CUSTOMER'
AND ct.person_id = :customerId
ORDER BY ct.transaction_date
""", nativeQuery = true)
  List<LedgerView> getCustomerLedger(@Param("customerId") Long customerId);
  @Query(value = """
SELECT
COALESCE(SUM(
CASE
WHEN transaction_type = 'SHOP_PURCHASE' THEN crate_count
WHEN transaction_type IN ('FARMER_RETURN','CUSTOMER_RETURN') THEN crate_count
WHEN transaction_type IN ('FARMER_TAKE','CUSTOMER_TAKE') THEN -crate_count
END
),0)
FROM crate_transaction
""", nativeQuery = true)
  Integer getShopStock();
  @Query("SELECT t FROM CrateTransaction t ORDER BY t.transactionDate DESC")
  List<CrateTransaction> findRecentTransactions();

  @Query(value = """
SELECT
    SUM(CASE
        WHEN t.transactionType LIKE '%TAKE' THEN t.crateCount
        WHEN t.transactionType LIKE '%RETURN' THEN -t.crateCount
        ELSE 0
    END)
FROM CrateTransaction t
WHERE t.personId = :personId AND t.personType = :personType
""")
  Integer getBalance(
    @Param("personId") Long personId,
    @Param("personType") String personType
  );
  @Query(value="""
SELECT t.personId,
       SUM(CASE
            WHEN t.transactionType LIKE '%TAKE' THEN t.crateCount
            WHEN t.transactionType LIKE '%RETURN' THEN -t.crateCount
            ELSE 0
       END)
FROM CrateTransaction t
WHERE t.personType = :type
AND t.personId IN :ids
GROUP BY t.personId
""")
  List<Object[]> getBulkBalance(
    @Param("type") String type,
    @Param("ids") List<Long> ids
  );
}



