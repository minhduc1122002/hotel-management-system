-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Feb 07, 2022 at 04:56 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

CREATE TABLE `bills` (
  `billID` int(50) NOT NULL,
  `reservationID` int(50) NOT NULL,
  `billDate` date NOT NULL,
  `billAmount` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bills`
--

INSERT INTO `bills` (`billID`, `reservationID`, `billDate`, `billAmount`) VALUES
(1, 2, '2021-12-12', 33000),
(2, 5, '2021-12-14', 19500);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customerIDNumber` int(50) NOT NULL,
  `customerName` varchar(50) NOT NULL,
  `customerNationality` varchar(20) NOT NULL,
  `customerGender` varchar(10) NOT NULL,
  `customerPhoneNo` int(50) NOT NULL,
  `customerEmail` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customerIDNumber`, `customerName`, `customerNationality`, `customerGender`, `customerPhoneNo`, `customerEmail`) VALUES
(2510092, 'Quốc Khánh', 'Viet Nam', 'Male', 97938274, 'khanh3000@gmail.com'),
(9348957, 'TestName', 'China', 'Male', 9247375, 'test@gmail.com'),
(255100992, 'Dave Jum', 'China', 'Male', 673950337, 'daveJ@gmail.com'),
(355184941, 'Sakura Matou', 'Japan', 'Female', 53250430, 'sakura87@gmail.com'),
(422510092, 'Billy Dope', 'USA', 'Male', 84927442, 'billyb9@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `reservationID` int(50) NOT NULL,
  `customerIDNumber` int(10) NOT NULL,
  `roomNumber` varchar(20) NOT NULL,
  `checkInDate` date NOT NULL,
  `checkOutDate` date NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Checked In'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reservations`
--

INSERT INTO `reservations` (`reservationID`, `customerIDNumber`, `roomNumber`, `checkInDate`, `checkOutDate`, `status`) VALUES
(1, 422510092, '100', '2021-12-01', '2021-12-14', 'Checked In'),
(2, 255100992, '303', '2021-12-01', '2021-12-12', 'Checked Out'),
(3, 355184941, '202', '2021-12-01', '2021-12-13', 'Checked In'),
(4, 2510092, '102', '2021-12-10', '2021-12-13', 'Checked In'),
(5, 9348957, '701', '2021-12-01', '2021-12-14', 'Checked Out');

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `roomNumber` varchar(20) NOT NULL,
  `roomType` varchar(50) NOT NULL,
  `price` int(20) NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'Not Booked'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`roomNumber`, `roomType`, `price`, `status`) VALUES
('100', 'Single', 1000, 'Booked'),
('101', 'Double', 1500, 'Not Booked'),
('102', 'Single', 1000, 'Booked'),
('200', 'Family', 3000, 'Not Booked'),
('201', 'Luxury', 5000, 'Not Booked'),
('202', 'Double', 1500, 'Booked'),
('301', 'Luxury', 5000, 'Not Booked'),
('302', 'Luxury', 5000, 'Not Booked'),
('303', 'Family', 3000, 'Not Booked'),
('401', 'Single', 1000, 'Not Booked'),
('402', 'Double', 1000, 'Not Booked'),
('501', 'Presidential Suite', 10000, 'Not Booked'),
('502', 'Presidential Suite', 10000, 'Not Booked'),
('701', 'Double', 1500, 'Not Booked');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `gender` varchar(20) NOT NULL,
  `securityQuestion` varchar(100) NOT NULL,
  `answer` varchar(200) NOT NULL,
  `address` varchar(200) NOT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `username`, `password`, `gender`, `securityQuestion`, `answer`, `address`, `status`) VALUES
(1, 'Duc', 'minhduc1122002', 'Duc2002lol@', 'Male', 'What is the name of your first pet?', 'Jeff', 'Ha Noi', NULL),
(2, 'Khanh', 'khanh0140', 'iamironman3', 'Male', 'What is the name of the town where you were born?', 'Ha Noi', 'Ha Noi', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bills`
--
ALTER TABLE `bills`
  ADD PRIMARY KEY (`billID`),
  ADD UNIQUE KEY `fk_bills_res` (`reservationID`) USING BTREE;

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customerIDNumber`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`reservationID`),
  ADD KEY `fk_customers_res` (`customerIDNumber`),
  ADD KEY `fk_rooms_res` (`roomNumber`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`roomNumber`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bills`
--
ALTER TABLE `bills`
  MODIFY `billID` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `reservationID` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bills`
--
ALTER TABLE `bills`
  ADD CONSTRAINT `fk_bills_res` FOREIGN KEY (`reservationID`) REFERENCES `reservations` (`reservationID`) ON UPDATE CASCADE;

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `fk_customers_res` FOREIGN KEY (`customerIDNumber`) REFERENCES `customers` (`customerIDNumber`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_rooms_res` FOREIGN KEY (`roomNumber`) REFERENCES `rooms` (`roomNumber`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
