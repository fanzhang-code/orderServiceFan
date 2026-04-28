package com.fandy.orderservicefan.repository;

import com.fandy.orderservicefan.entity.Ledger;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Repository
public class LedgerRepository {

    private final DynamoDbTable<Ledger> table;

    public LedgerRepository(DynamoDbTable<Ledger> ledgerTable) {
        this.table = ledgerTable;
    }

    public void save(Ledger ledger) {
        table.putItem(ledger);
    }
}
