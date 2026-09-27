CREATE DATABASE railway_db;

USE railway_db;

-- Users table
CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- Trains table
CREATE TABLE trains (
                        train_id INT AUTO_INCREMENT PRIMARY KEY,
                        train_number VARCHAR(20) UNIQUE NOT NULL,
                        train_name VARCHAR(100) NOT NULL,
                        source_station VARCHAR(100) NOT NULL,
                        destination_station VARCHAR(100) NOT NULL,
                        departure_time VARCHAR(20),
                        arrival_time VARCHAR(20),
                        available_seats INT NOT NULL
);

-- Bookings table
CREATE TABLE bookings (
                          booking_id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT NOT NULL,
                          train_id INT NOT NULL,
                          passenger_name VARCHAR(100) NOT NULL,
                          age INT,
                          gender VARCHAR(10),
                          seat_number INT,
                          booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          booking_status VARCHAR(20) DEFAULT 'Confirmed',

                          FOREIGN KEY (user_id)
                              REFERENCES users(user_id),

                          FOREIGN KEY (train_id)
                              REFERENCES trains(train_id)
);

-- Sample train data
INSERT INTO trains
(train_number, train_name, source_station, destination_station,
 departure_time, arrival_time, available_seats)
VALUES
    ('12671', 'Nilagiri Express', 'Chennai', 'Coimbatore',
     '21:15', '06:15', 50),

    ('12674', 'Cheran Express', 'Coimbatore', 'Chennai',
     '22:30', '06:00', 45),

    ('12675', 'Kovai Express', 'Chennai', 'Coimbatore',
     '06:10', '14:00', 60),

    ('12637', 'Pandian Express', 'Chennai', 'Madurai',
     '21:40', '07:10', 55),

    ('12638', 'Pandian Express', 'Madurai', 'Chennai',
     '20:30', '06:00', 40);