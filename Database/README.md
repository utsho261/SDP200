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
    -- Create the Blood Bank database
      CREATE DATABASE bloodbank;

   -- Use the database
     USE bloodbank;

   -- Create the Doner table
    CREATE TABLE Doner (
       DonerID INT AUTO_INCREMENT PRIMARY KEY,
       Name VARCHAR(100),
       BloodGroup VARCHAR(10),
       PhoneNumber VARCHAR(15),
        Email VARCHAR(100)
   );

   -- Create the BloodStock table
     CREATE TABLE BloodStock (
       BloodGroup VARCHAR(5) PRIMARY KEY,
       Quantity INT DEFAULT 0
   );

   -- Create the Donations table
   CREATE TABLE Donations (
      DonationID INT AUTO_INCREMENT PRIMARY KEY,
      DonerID INT,
      BloodGroup VARCHAR(10),
      DonationDate DATE,
      Quantity INT,
      FOREIGN KEY (DonerID) REFERENCES Doner(DonerID)
   );

   -- Create the PatientRequests table
    CREATE TABLE PatientRequests (
      RequestID INT AUTO_INCREMENT PRIMARY KEY,
      PatientID INT,
      BloodGroup VARCHAR(10),
      Quantity INT,
      RequestDate DATE,
      Status VARCHAR(20)
   );

   -- Insert blood groups with initial quantity set to 0
   INSERT INTO BloodStock (BloodGroup, Quantity) VALUES 
    ('A+', 0),
    ('A-', 0),
    ('B+', 0),
    ('B-', 0),
    ('AB+', 0),
    ('AB-', 0),
    ('O+', 0),
    ('O-', 0);
