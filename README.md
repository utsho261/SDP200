# Blood Bank Management System

## Overview

The Blood Bank Management System (BBMS) is a desktop application developed in Java that helps manage blood donations, track blood stock, and process patient requests. The application provides functionalities to add and manage donors, record blood donations, maintain blood stock levels, and handle patient blood requests efficiently.

## Features

- **Donor Management:** 
  - Add new donors with details including Name, Blood Group, Phone Number, and Email.
  - View and update donor information.

- **Blood Stock Management:** 
  - View current blood stock levels.
  - Update blood stock when donations are made.

- **Donation Management:** 
  - Record blood donations with details including Donor ID, Blood Group, Donation Date, and Quantity.
  - Automatically update blood stock based on donations.

- **Patient Request Management:** 
  - Process patient blood requests.
  - Check blood availability and update stock accordingly.

## Database Setup

### SQL Scripts

1. **Create Database and Tables:**

   ```sql
   -- Create database if it doesn't exist
   CREATE DATABASE IF NOT EXISTS bloodbank;
   USE bloodbank;

   -- Doner table
   CREATE TABLE IF NOT EXISTS Doner (
       DonerID INT AUTO_INCREMENT PRIMARY KEY,
       Name VARCHAR(100),
       BloodGroup VARCHAR(10),
       PhoneNumber VARCHAR(20),
       Email VARCHAR(100)
   );

   -- BloodStock table
   CREATE TABLE IF NOT EXISTS BloodStock (
       BloodGroup VARCHAR(10) PRIMARY KEY,
       Quantity INT
   );

   -- Donations table
   CREATE TABLE IF NOT EXISTS Donations (
       DonationID INT AUTO_INCREMENT PRIMARY KEY,
       DonerID INT,
       BloodGroup VARCHAR(10),
       DonationDate DATE,
       Quantity INT,
       FOREIGN KEY (DonerID) REFERENCES Doner(DonerID),
       FOREIGN KEY (BloodGroup) REFERENCES BloodStock(BloodGroup)
   );

   -- PatientRequest table
   CREATE TABLE IF NOT EXISTS PatientRequest (
       RequestID INT AUTO_INCREMENT PRIMARY KEY,
       PatientID INT,
       BloodGroup VARCHAR(10),
       Quantity INT,
       RequestDate DATE,
       FOREIGN KEY (BloodGroup) REFERENCES BloodStock(BloodGroup)
   );
