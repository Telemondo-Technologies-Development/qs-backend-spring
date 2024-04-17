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

CREATE TABLE IF NOT EXISTS queues(
    id VARCHAR(36) PRIMARY KEY,
    counter_type_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (counter_type_id) REFERENCES counter_types(id)
    counter_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (counter_id) REFERENCES counters(id)
--  -1 = cancelled, 1 = waiting, 2 = on-counter, 3 = complete
    status INT DEFAULT 1,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS counters(
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(36) NOT NULL,
--  -1 = inactive, 1 = active, 2 = pause,
    status INT DEFAULT -1,
    counter_type_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (counter_type_id) REFERENCES counter_types(id)
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS counter_types(
    id VARCHAR(36) PRIMARY KEY,
    counter_type VARCHAR(36) NOT NULL
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
)

