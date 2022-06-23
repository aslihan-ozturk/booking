CREATE TABLE doctor (
                               doctor_id BIGINT AUTO_INCREMENT  PRIMARY KEY,
                               doctor_name VARCHAR(100) NOT NULL,
                               hourly_rate NUMBER NOT NULL,
                               branch VARCHAR(50) NOT NULL
);

CREATE TABLE appointment (
                             appointment_id BIGINT AUTO_INCREMENT  PRIMARY KEY,
                             doctor_id BIGINT NOT NULL,
                             patient_id BIGINT NOT NULL,
                             fee DOUBLE NOT NULL,
                             state VARCHAR(1) NOT NULL,
                             start_date DATETIME NOT NULL,
                             end_date DATETIME NOT NULL
);

CREATE TABLE transaction (
                             transaction_id BIGINT AUTO_INCREMENT  PRIMARY KEY,
                             transaction_type VARCHAR(1) NOT NULL,
                             transaction_state VARCHAR(1) NOT NULL,
                             transaction_date DATETIME NOT NULL,
                             price DOUBLE NOT NULL,
                             appointment_id BIGINT NOT NULL

);

CREATE TABLE patient (
                             patient_id BIGINT AUTO_INCREMENT  PRIMARY KEY,
                             patient_name VARCHAR(100) NOT NULL
);

