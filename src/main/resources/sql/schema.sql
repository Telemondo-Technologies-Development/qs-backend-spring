--CREATE TABLE IF NOT EXISTS customers(
--    id INT(3) AUTO_INCREMENT PRIMARY KEY,
--    name VARCHAR(40) NOT NULL,
--    queueType INT,
--    FOREIGN KEY (queueType) REFERENCES queue(id)
--);

--CREATE TABLE IF NOT EXISTS admins(
--    id VARCHAR(36) PRIMARY KEY,
--    username VARCHAR(40) NOT NULL,
--    password VARCHAR(40) NOT NULL
--)

CREATE TABLE IF NOT EXISTS counter_types (
    id VARCHAR(36) PRIMARY KEY,
    status INT DEFAULT 1,
    counter_type VARCHAR(36) NOT NULL,
    prefix VARCHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    UNIQUE (prefix)
);

CREATE TABLE IF NOT EXISTS counters (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(36) NOT NULL,
    --  -1 = inactive, 1 = receiving, 2 = entertaining, 3 = pause
    status INT DEFAULT -1,
    current_customer_id VARCHAR(36)
    counter_type_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (counter_type_id) REFERENCES counter_types(id)
    FOREIGN KEY (current_customer_id) REFERENCES queue_users(id)
);

CREATE TABLE IF NOT EXISTS queue_users (
    id VARCHAR(36) PRIMARY KEY,
    ticket_num VARCHAR(36) NOT NULL,
--    1 = regular, 2 = non-regular
    customer_type INT NOT NULL,
    counter_type_id VARCHAR(36) NOT NULL,
    counter_id VARCHAR(36) NOT NULL,
    --  -1 = cancelled, 1 = waiting, 2 = on-counter, 3 = complete
    status INT DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    entertained_at TIMESTAMP NOT NULL,
    estimated_wait_time VARCHAR NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (counter_type_id) REFERENCES counter_types(id),
    FOREIGN KEY (counter_id) REFERENCES counters(id)
);