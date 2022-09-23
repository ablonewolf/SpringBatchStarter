-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 23, 2022 at 01:06 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `university`
--

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`id`, `email`, `first_name`, `last_name`) VALUES
(1, 'arka.bhuiyan@bjitgroup.com', 'Arka', 'Bhuiyan'),
(2, 'farhan.zaman@bjitgroup.com', 'Farhan', 'Zaman'),
(3, 'nipa.howladar@bjitgroup.com', 'Nipa', 'Howladar'),
(4, 'akif.azwad@bjitgroup.com', 'Akif', 'Azwad'),
(5, 'riazul.rana@bjitgroup.com', 'Riazul', 'Rana'),
(6, 'zareen.zia@bjitgroup.com', 'Zareen', 'Zia'),
(7, 'faiaz.mursalin@bjitgroup.com', 'Faiaz', 'Mursalin'),
(8, 'saif.sohel@bjitgroup.com', 'Saif', 'Sohel'),
(9, 'iftekhar.alam@bjitgroup.com', 'Iftekhar', 'Alam'),
(10, 'somoy.subandhu@bjitgroup.com', 'Somoy', 'Subandhu');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
