# Railway Reservation System

A Java-based Railway Reservation System developed using Core Java, Java Swing, JDBC, and MySQL.

## Features

- User Registration and Login
- View Available Trains
- Search Trains by Source and Destination
- Book Railway Tickets
- Automatic Seat Number Assignment
- Available Seat Management
- View Booking History
- Cancel Tickets
- Restore Available Seat after Cancellation
- Logout

## Technologies Used

- Java
- Java Swing
- JDBC
- MySQL
- IntelliJ IDEA

## Database

Database Name:

railway_db

Main Tables:

- users
- trains
- bookings

## Project Structure

- DBConnection.java
- DBTest.java
- User.java
- UserDAO.java
- Train.java
- TrainDAO.java
- Booking.java
- BookingDAO.java
- LoginGUI.java
- RegisterGUI.java
- DashboardGUI.java
- railway_db.sql

## How to Run

1. Install Java JDK.
2. Install MySQL.
3. Create the `railway_db` database.
4. Execute the SQL commands from `railway_db.sql`.
5. Add MySQL Connector/J to the project.
6. Update the MySQL username and password in `DBConnection.java`.
7. Run `LoginGUI.java`.
8. Register a new user and login.
9. Use the dashboard to view, search, book, and cancel railway tickets.

## Database Connection

The application uses JDBC to connect Java with MySQL.

Update the following values in `DBConnection.java` according to your MySQL setup:

- Database URL
- MySQL username
- MySQL password

## Java Concepts Used

- Classes and Objects
- Encapsulation
- Constructors
- Methods
- ArrayList
- Exception Handling
- JDBC
- SQL Queries
- Event Handling
- Java Swing GUI

## Learning Experience

This project helped me understand how to develop a practical Java application with database connectivity. I learned how to perform CRUD-related database operations using JDBC, design GUI screens using Swing, manage railway bookings, handle seat availability, and implement ticket cancellation.

## Developed For

Java Programming Internship – Phase 2

SQROCK IT SOLUTIONS