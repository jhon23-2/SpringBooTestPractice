
    -- sql scrip to create table
    CREATE TABLE users (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(50) NOT NULL UNIQUE,
        email VARCHAR(100) NOT NULL UNIQUE,
        password_hash VARCHAR(255) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );


    -- Mock values
INSERT INTO users (username, email, password_hash, created_at, updated_at)
VALUES
("Jhoalmanza", "jhoalmanza@gmail.com", "122112", NOW(), NOW()),
("sandylore", "sandy@gmail.com", "12s112", NOW(), NOW()),
("eineralma", "einer@gmail.com", "12d112", NOW(), NOW()),
("liliana", "liliana@gmail.com", "12d2112", NOW(), NOW());


