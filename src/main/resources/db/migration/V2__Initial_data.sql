CREATE SEQUENCE IF NOT EXISTS user_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS email_sequence START WITH 1 INCREMENT BY 1;

INSERT INTO users (id, name, date_of_birth, role, password) VALUES
                                                          (1, 'Иван Иванов', '1990-01-01', 'ROLE_USER', '$2a$10$9uVpzbwiXGGWWPOurtbam.Ogn1J.Q0w5BJIUSip99oHyQO6t/Skui'), -- password123
                                                          (2, 'Петр Петров', '1985-05-15', 'ROLE_USER', '$2a$10$I7LyBRt/CndVM8xZKpAeL.H5qegfnM0dDxxLBfjsriyOJmgqel3Lu'); -- password456

INSERT INTO email_data (id, email, user_id) VALUES
                                                (1, 'ivan@example.com', 1),
                                                (2, 'petr@example.com', 2),
                                                (3, 'ivanov@example.com', 1),
                                                (4, 'petrov@example.com', 2);

INSERT INTO account (id, user_id, balance, initial_deposit) VALUES
                                               (1, 1, 100.00, 100.00),
                                               (2, 2, 150.00, 150.00);