CREATE TABLE users
(
    id            BIGINT PRIMARY KEY,
    name          VARCHAR(500),
    date_of_birth DATE,
    role          VARCHAR(20),
    password      VARCHAR(500)
);

CREATE TABLE account
(
    id              BIGINT PRIMARY KEY,
    user_id         BIGINT UNIQUE NOT NULL,
    balance         DECIMAL(10, 2),
    initial_deposit DECIMAL(10, 2),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE email_data
(
    id      BIGINT PRIMARY KEY,
    user_id BIGINT              NOT NULL,
    email   VARCHAR(200) UNIQUE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE phone_data
(
    id      BIGINT PRIMARY KEY,
    user_id BIGINT             NOT NULL,
    phone   VARCHAR(13) UNIQUE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);