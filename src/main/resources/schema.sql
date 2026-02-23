CREATE TABLE IF NOT EXISTS orders (
    orderId TEXT PRIMARY KEY,
    customerId TEXT NOT NULL,
    itemId TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    status TEXT
);

CREATE TABLE IF NOT EXISTS ledger (
    ledgerId TEXT PRIMARY KEY,
    customerId TEXT NOT NULL,
    orderId TEXT NOT NULL,
    transferAmount INTEGER NOT NULL,
    operationType TEXT NOT NULL,
    createTime TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS idempotency_records (
    idempotencyKey TEXT PRIMARY KEY,
    fingerprint TEXT NOT NULL,
    statusCode TEXT,
    responseBody TEXT,
    createTime TEXT NOT NULL
);