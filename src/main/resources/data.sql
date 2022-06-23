INSERT INTO doctor(doctor_name, hourly_rate, branch) VALUES ('Ayşe Bak', 800.00, 'KBB');
INSERT INTO appointment(patient_id, doctor_id, fee, state, start_date, end_date) VALUES(1, 1, 800.0, 'A', '2022-07-18T10:00:00.000Z', '2022-07-18T11:00:00.000Z' );
INSERT INTO patient(patient_name) values('Aslıhan Yüksel');
INSERT INTO transaction(transaction_type, transaction_state, price, transaction_date, appointment_id) values ('S', 'A', 800.0, '2022-06-18T10:00:00.000Z', 1);
