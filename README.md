# Blood Bank Management System

## Overview

The Blood Bank Management System (BBMS) is a Java-based desktop application designed to manage and streamline blood donation and distribution processes. This system handles donor information, blood stock management, donation tracking, and patient blood requests, providing a user-friendly interface for efficient operation.

## Features

- **Donor Management**: Add and manage donor details including name, blood group, phone number, and email.
- **Blood Stock Management**: View and refresh the current blood stock.
- **Donation Tracking**: Record and manage blood donations, including donor details and donation quantity.
- **Patient Requests**: Handle patient blood requests and update the status based on blood availability.

## Technologies Used

- **Java**: Programming language used for the application.
- **Swing**: For creating the graphical user interface.
- **JDateChooser**: For date selection in donation and request forms.
- **MySQL**: For the database management, accessed via JDBC.

## Setup and Installation

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- XAMPP (or any other MySQL server)
- MySQL Database

### Database Setup

1. **Create a Database**: Create a database named `bloodbank` in MySQL.
2. **Create Tables**: Execute the following SQL scripts to create necessary tables:

   ```sql
   CREATE TABLE Doner (
       DonerID INT AUTO_INCREMENT PRIMARY KEY,
       Name VARCHAR(100),
       BloodGroup VARCHAR(10),
       PhoneNumber VARCHAR(15),
       Email VARCHAR(100)
   );

   CREATE TABLE BloodStock (
       BloodGroup VARCHAR(10) PRIMARY KEY,
       Quantity INT
   );

   CREATE TABLE Donations (
       DonationID INT AUTO_INCREMENT PRIMARY KEY,
       DonerID INT,
       BloodGroup VARCHAR(10),
       DonationDate DATE,
       Quantity INT,
       FOREIGN KEY (DonerID) REFERENCES Doner(DonerID)
   );

   CREATE TABLE PatientRequests (
       RequestID INT AUTO_INCREMENT PRIMARY KEY,
       PatientID INT,
       BloodGroup VARCHAR(10),
       Quantity INT,
       RequestDate DATE,
       Status VARCHAR(20)
   );
