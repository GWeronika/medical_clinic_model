-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Cze 12, 2023 at 11:23 AM
-- Wersja serwera: 10.4.28-MariaDB
-- Wersja PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `przychodnia`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `archiwum`
--

CREATE TABLE `archiwum` (
  `id` int(11) NOT NULL,
  `Pracownik` varchar(255) NOT NULL,
  `Pacjent` varchar(255) NOT NULL,
  `Data` date NOT NULL,
  `Opis` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `archiwum`
--

INSERT INTO `archiwum` (`id`, `Pracownik`, `Pacjent`, `Data`, `Opis`) VALUES
(1, 'Grzegorz Nowicki', 'Jan Kowalski', '2023-06-07', 'Zatkanie przewodu słuchowego, powodującego zaburzenia w słyszeniu'),
(11, 'Magdalena Mazur', 'Adam Kowalczyk', '2023-06-11', 'Angina, ostry ból gardła, podwyższona temperatura'),
(12, 'Joanna Kwiatkowska', 'Maria Wojcik', '2023-06-11', 'Zapalenie oskrzeli, mokry kaszel, nasilające się bóle mięśni');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `badanie`
--

CREATE TABLE `badanie` (
  `id` int(11) NOT NULL,
  `data` date NOT NULL,
  `czas` time NOT NULL,
  `idPielengniarki` int(11) NOT NULL,
  `idPacjenta` int(11) NOT NULL,
  `idSkierownania` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `badanie`
--

INSERT INTO `badanie` (`id`, `data`, `czas`, `idPielengniarki`, `idPacjenta`, `idSkierownania`) VALUES
(1, '2023-07-17', '00:15:00', 17, 1, 1),
(2, '2023-07-26', '00:15:00', 17, 3, 2),
(3, '2023-08-31', '00:20:00', 17, 1, 4),
(4, '2022-11-02', '00:15:00', 17, 4, 3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `dostepnewizyty`
--

CREATE TABLE `dostepnewizyty` (
  `idLekarza` int(11) NOT NULL,
  `Data` date NOT NULL,
  `Godzina` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dostepnewizyty`
--

INSERT INTO `dostepnewizyty` (`idLekarza`, `Data`, `Godzina`) VALUES
(1, '2023-06-08', '09:00:00'),
(1, '2023-06-08', '10:00:00'),
(1, '2023-06-09', '14:30:00'),
(1, '2023-06-09', '15:30:00'),
(1, '2023-06-10', '11:00:00'),
(1, '2023-06-10', '12:00:00'),
(2, '2023-06-08', '09:30:00'),
(2, '2023-06-08', '10:30:00'),
(2, '2023-06-09', '15:00:00'),
(2, '2023-06-09', '16:00:00'),
(2, '2023-06-10', '12:30:00'),
(2, '2023-06-10', '13:30:00'),
(3, '2023-06-08', '11:00:00'),
(3, '2023-06-08', '12:00:00'),
(3, '2023-06-09', '16:30:00'),
(3, '2023-06-09', '17:30:00'),
(3, '2023-06-10', '13:00:00'),
(3, '2023-06-10', '14:00:00'),
(4, '2023-06-08', '09:30:00'),
(4, '2023-06-08', '10:30:00'),
(4, '2023-06-09', '15:00:00'),
(4, '2023-06-09', '16:00:00'),
(4, '2023-06-10', '12:30:00'),
(4, '2023-06-10', '13:30:00'),
(5, '2023-06-08', '11:00:00'),
(5, '2023-06-08', '12:00:00'),
(5, '2023-06-09', '16:30:00'),
(5, '2023-06-09', '17:30:00'),
(5, '2023-06-10', '13:00:00'),
(5, '2023-06-10', '14:00:00'),
(6, '2023-06-08', '09:00:00'),
(6, '2023-06-08', '10:00:00'),
(6, '2023-06-09', '14:30:00'),
(6, '2023-06-09', '15:30:00'),
(6, '2023-06-10', '11:00:00'),
(6, '2023-06-10', '12:00:00'),
(7, '2023-06-08', '09:30:00'),
(7, '2023-06-08', '10:30:00'),
(7, '2023-06-09', '15:00:00'),
(7, '2023-06-09', '16:00:00'),
(7, '2023-06-10', '12:30:00'),
(7, '2023-06-10', '13:30:00'),
(8, '2023-06-08', '11:00:00'),
(8, '2023-06-08', '12:00:00'),
(8, '2023-06-09', '16:30:00'),
(8, '2023-06-09', '17:30:00'),
(8, '2023-06-10', '13:00:00'),
(8, '2023-06-10', '14:00:00'),
(9, '2023-06-08', '09:30:00'),
(9, '2023-06-08', '10:30:00'),
(9, '2023-06-09', '15:00:00'),
(9, '2023-06-09', '16:00:00'),
(9, '2023-06-10', '12:30:00'),
(9, '2023-06-10', '13:30:00'),
(10, '2023-06-08', '11:00:00'),
(10, '2023-06-08', '12:00:00'),
(10, '2023-06-09', '16:30:00'),
(10, '2023-06-09', '17:30:00'),
(10, '2023-06-10', '13:00:00'),
(10, '2023-06-10', '14:00:00'),
(11, '2023-06-08', '09:00:00'),
(11, '2023-06-08', '10:00:00'),
(11, '2023-06-09', '14:30:00'),
(11, '2023-06-09', '15:30:00'),
(11, '2023-06-10', '11:00:00'),
(11, '2023-06-10', '12:00:00'),
(12, '2023-06-08', '09:30:00'),
(12, '2023-06-08', '10:30:00'),
(12, '2023-06-09', '15:00:00'),
(12, '2023-06-09', '16:00:00'),
(12, '2023-06-10', '12:30:00'),
(12, '2023-06-10', '13:30:00'),
(13, '2023-06-08', '11:00:00'),
(13, '2023-06-08', '12:00:00'),
(13, '2023-06-09', '16:30:00'),
(13, '2023-06-09', '17:30:00'),
(13, '2023-06-10', '13:00:00'),
(13, '2023-06-10', '14:00:00'),
(14, '2023-06-08', '09:30:00'),
(14, '2023-06-08', '10:30:00'),
(14, '2023-06-09', '15:00:00'),
(14, '2023-06-09', '16:00:00'),
(14, '2023-06-10', '12:30:00'),
(14, '2023-06-10', '13:30:00'),
(15, '2023-06-08', '11:00:00'),
(15, '2023-06-08', '12:00:00'),
(15, '2023-06-09', '16:30:00'),
(15, '2023-06-09', '17:30:00'),
(15, '2023-06-10', '13:00:00'),
(15, '2023-06-10', '14:00:00'),
(16, '2023-06-08', '09:00:00'),
(16, '2023-06-08', '10:00:00'),
(16, '2023-06-09', '14:30:00'),
(16, '2023-06-09', '15:30:00'),
(16, '2023-06-10', '11:00:00'),
(16, '2023-06-10', '12:00:00'),
(17, '2023-06-08', '09:30:00'),
(17, '2023-06-08', '10:30:00'),
(17, '2023-06-09', '15:00:00'),
(17, '2023-06-09', '16:00:00'),
(17, '2023-06-10', '12:30:00'),
(17, '2023-06-10', '13:30:00'),
(18, '2023-06-08', '11:00:00'),
(18, '2023-06-08', '12:00:00'),
(18, '2023-06-09', '16:30:00'),
(18, '2023-06-09', '17:30:00'),
(18, '2023-06-10', '13:00:00'),
(18, '2023-06-10', '14:00:00');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `haslo_pacjent`
--

CREATE TABLE `haslo_pacjent` (
  `id` int(11) NOT NULL,
  `haslo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `haslo_pacjent`
--

INSERT INTO `haslo_pacjent` (`id`, `haslo`) VALUES
(1, 'Haslo1.'),
(2, 'Haslo2.'),
(3, 'Haslo3.'),
(4, 'Haslo4.'),
(5, 'Haslo5.');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `haslo_pracownik`
--

CREATE TABLE `haslo_pracownik` (
  `id` int(11) NOT NULL,
  `haslo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `haslo_pracownik`
--

INSERT INTO `haslo_pracownik` (`id`, `haslo`) VALUES
(1, '1'),
(2, '2'),
(3, '3'),
(4, '4'),
(5, '5'),
(6, '6'),
(7, '7'),
(8, '8'),
(9, '9'),
(10, '10'),
(11, '11'),
(12, '12'),
(13, '13'),
(14, '14'),
(15, '15'),
(16, '16'),
(17, '17');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pacjent`
--

CREATE TABLE `pacjent` (
  `id` int(11) NOT NULL,
  `imie` varchar(255) NOT NULL,
  `nazwisko` varchar(255) NOT NULL,
  `data_urodzenia` date NOT NULL,
  `pesel` varchar(11) NOT NULL,
  `plec` enum('k','m','inny') NOT NULL,
  `nrTelefonu` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pacjent`
--

INSERT INTO `pacjent` (`id`, `imie`, `nazwisko`, `data_urodzenia`, `pesel`, `plec`, `nrTelefonu`) VALUES
(1, 'Jan', 'Kowalski', '1990-05-10', '90051012345', 'm', '123456789'),
(2, 'Anna', 'Nowak', '1985-08-15', '85081598765', 'k', '987654321'),
(3, 'Piotr', 'Nowicki', '1992-03-22', '92032245678', 'm', '567890123'),
(4, 'Maria', 'Wojcik', '1983-11-27', '83112765432', 'k', '456789012'),
(5, 'Adam', 'Kowalczyk', '1995-09-04', '95090478901', 'm', '789012345');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pacjent_internista`
--

CREATE TABLE `pacjent_internista` (
  `idPacjenta` int(11) NOT NULL,
  `idInternisty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pacjent_internista`
--

INSERT INTO `pacjent_internista` (`idPacjenta`, `idInternisty`) VALUES
(1, 3),
(2, 15),
(3, 16),
(4, 3),
(5, 15);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pracownik`
--

CREATE TABLE `pracownik` (
  `id` int(11) NOT NULL,
  `imie` varchar(255) NOT NULL,
  `nazwisko` varchar(255) NOT NULL,
  `specjalizacja` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pracownik`
--

INSERT INTO `pracownik` (`id`, `imie`, `nazwisko`, `specjalizacja`) VALUES
(1, 'Karolina', 'Zajac', 'Neurolog'),
(2, 'Tomasz', 'Lewandowski', 'Okulista'),
(3, 'Magdalena', 'Mazur', 'Internista'),
(4, 'Michal', 'Jankowski', 'Chirurg'),
(5, 'Ewa', 'Kaminska', 'Psychiatra'),
(6, 'Katarzyna', 'Wojcik', 'Neonatolog'),
(7, 'Bartlomiej', 'Kowalski', 'Urolog'),
(8, 'Monika', 'Nowakowska', 'Endokrynolog'),
(9, 'Marcin', 'Lis', 'Onkolog'),
(10, 'Joanna', 'Kwiatkowska', 'Pulmonolog'),
(11, 'Grzegorz', 'Nowicki', 'Internista'),
(12, 'Katarzyna', 'Kowalska', 'Internista'),
(13, 'Marta', 'Kowalska', 'Pielegniarka'),
(14, 'Alicja', 'Nowak', 'Pielegniarka'),
(15, 'Karolina', 'Kowalczyk', 'Pielegniarka'),
(16, 'Natalia', 'Wojcik', 'Pielegniarka'),
(17, 'Monika', 'Lis', 'Pielegniarka');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `recepta`
--

CREATE TABLE `recepta` (
  `id` int(11) NOT NULL,
  `idLekarza` int(11) NOT NULL,
  `idPacjenta` int(11) NOT NULL,
  `lek` varchar(255) NOT NULL,
  `mg` int(11) NOT NULL,
  `dataWaznosci` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `recepta`
--

INSERT INTO `recepta` (`id`, `idLekarza`, `idPacjenta`, `lek`, `mg`, `dataWaznosci`) VALUES
(1, 1, 1, 'Paracetamol', 500, '2023-06-30'),
(2, 2, 2, 'Ibuprofen', 200, '2023-07-15'),
(3, 3, 3, 'Amoxicillin', 500, '2023-06-25'),
(4, 2, 4, 'Kwas acetylosalicylowy', 75, '2023-04-17'),
(5, 2, 5, 'Witamina D3', 150, '2022-12-31'),
(6, 7, 4, 'Cholestyramina', 1200, '2022-09-03'),
(7, 3, 2, 'Klopidogrel', 600, '2023-08-01'),
(8, 10, 4, 'Alprazolam', 30, '2022-09-26');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `skierowaniebadanie`
--

CREATE TABLE `skierowaniebadanie` (
  `id` int(11) NOT NULL,
  `nazwa_badania` varchar(255) NOT NULL,
  `idPacjenta` int(11) NOT NULL,
  `idLekarza` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `skierowaniebadanie`
--

INSERT INTO `skierowaniebadanie` (`id`, `nazwa_badania`, `idPacjenta`, `idLekarza`) VALUES
(1, 'Badanie krwi', 1, 3),
(2, 'USG brzucha', 3, 4),
(3, 'Badanie testowe', 4, 3),
(4, 'Laparoskopia', 1, 1),
(7, 'Rezonans magnetyczny kolana', 2, 8),
(8, 'Rezonans magnetyczny', 2, 8),
(9, 'Rezonans', 2, 8),
(10, 'Rezonans magnetyczny kolana', 4, 8),
(11, 'Rezonans magnetyczny kolana', 1, 9),
(12, 'Rezonans magnetyczny kolana', 1, 7);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `skierowaniespecjalista`
--

CREATE TABLE `skierowaniespecjalista` (
  `id` int(11) NOT NULL,
  `idOd` int(11) NOT NULL,
  `idDo` int(11) NOT NULL,
  `idPacjenta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `skierowaniespecjalista`
--

INSERT INTO `skierowaniespecjalista` (`id`, `idOd`, `idDo`, `idPacjenta`) VALUES
(1, 3, 5, 1),
(2, 11, 8, 2),
(3, 12, 10, 3),
(4, 6, 4, 2),
(5, 6, 6, 5),
(6, 8, 10, 3),
(7, 12, 7, 5);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `wizyta`
--

CREATE TABLE `wizyta` (
  `id` int(11) NOT NULL,
  `data` date NOT NULL,
  `czas` time NOT NULL,
  `idLekarza` int(11) NOT NULL,
  `idPacjenta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `wizyta`
--

INSERT INTO `wizyta` (`id`, `data`, `czas`, `idLekarza`, `idPacjenta`) VALUES
(1, '2023-02-28', '00:15:00', 11, 3),
(2, '2022-11-11', '00:20:00', 8, 3),
(3, '2021-03-23', '00:15:00', 2, 1),
(4, '2023-07-01', '00:15:00', 3, 5);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `archiwum`
--
ALTER TABLE `archiwum`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `badanie`
--
ALTER TABLE `badanie`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idPielengniarki` (`idPielengniarki`),
  ADD KEY `idPacjenta` (`idPacjenta`),
  ADD KEY `idSkierownania` (`idSkierownania`);

--
-- Indeksy dla tabeli `dostepnewizyty`
--
ALTER TABLE `dostepnewizyty`
  ADD PRIMARY KEY (`idLekarza`,`Data`,`Godzina`);

--
-- Indeksy dla tabeli `haslo_pacjent`
--
ALTER TABLE `haslo_pacjent`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `haslo_pracownik`
--
ALTER TABLE `haslo_pracownik`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `pacjent`
--
ALTER TABLE `pacjent`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `pesel` (`pesel`),
  ADD UNIQUE KEY `nrTelefonu` (`nrTelefonu`);

--
-- Indeksy dla tabeli `pacjent_internista`
--
ALTER TABLE `pacjent_internista`
  ADD PRIMARY KEY (`idPacjenta`,`idInternisty`),
  ADD KEY `idInternisty` (`idInternisty`);

--
-- Indeksy dla tabeli `pracownik`
--
ALTER TABLE `pracownik`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `recepta`
--
ALTER TABLE `recepta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idLekarza` (`idLekarza`),
  ADD KEY `idPacjenta` (`idPacjenta`);

--
-- Indeksy dla tabeli `skierowaniebadanie`
--
ALTER TABLE `skierowaniebadanie`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idPacjenta` (`idPacjenta`),
  ADD KEY `idLekarza` (`idLekarza`);

--
-- Indeksy dla tabeli `skierowaniespecjalista`
--
ALTER TABLE `skierowaniespecjalista`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idOd` (`idOd`),
  ADD KEY `idDo` (`idDo`),
  ADD KEY `idPacjenta` (`idPacjenta`);

--
-- Indeksy dla tabeli `wizyta`
--
ALTER TABLE `wizyta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idLekarza` (`idLekarza`),
  ADD KEY `idPacjenta` (`idPacjenta`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `archiwum`
--
ALTER TABLE `archiwum`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `badanie`
--
ALTER TABLE `badanie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `haslo_pacjent`
--
ALTER TABLE `haslo_pacjent`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `haslo_pracownik`
--
ALTER TABLE `haslo_pracownik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `pacjent`
--
ALTER TABLE `pacjent`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pracownik`
--
ALTER TABLE `pracownik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `recepta`
--
ALTER TABLE `recepta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `skierowaniebadanie`
--
ALTER TABLE `skierowaniebadanie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `skierowaniespecjalista`
--
ALTER TABLE `skierowaniespecjalista`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `wizyta`
--
ALTER TABLE `wizyta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `badanie`
--
ALTER TABLE `badanie`
  ADD CONSTRAINT `badanie_ibfk_1` FOREIGN KEY (`idPielengniarki`) REFERENCES `pracownik` (`id`),
  ADD CONSTRAINT `badanie_ibfk_2` FOREIGN KEY (`idPacjenta`) REFERENCES `pacjent` (`id`),
  ADD CONSTRAINT `badanie_ibfk_3` FOREIGN KEY (`idSkierownania`) REFERENCES `skierowaniebadanie` (`id`);

--
-- Constraints for table `pacjent_internista`
--
ALTER TABLE `pacjent_internista`
  ADD CONSTRAINT `pacjent_internista_ibfk_1` FOREIGN KEY (`idPacjenta`) REFERENCES `pacjent` (`id`),
  ADD CONSTRAINT `pacjent_internista_ibfk_2` FOREIGN KEY (`idInternisty`) REFERENCES `pracownik` (`id`);

--
-- Constraints for table `recepta`
--
ALTER TABLE `recepta`
  ADD CONSTRAINT `recepta_ibfk_1` FOREIGN KEY (`idLekarza`) REFERENCES `pracownik` (`id`),
  ADD CONSTRAINT `recepta_ibfk_2` FOREIGN KEY (`idPacjenta`) REFERENCES `pacjent` (`id`);

--
-- Constraints for table `skierowaniebadanie`
--
ALTER TABLE `skierowaniebadanie`
  ADD CONSTRAINT `skierowaniebadanie_ibfk_1` FOREIGN KEY (`idPacjenta`) REFERENCES `pacjent` (`id`),
  ADD CONSTRAINT `skierowaniebadanie_ibfk_2` FOREIGN KEY (`idLekarza`) REFERENCES `pracownik` (`id`);

--
-- Constraints for table `skierowaniespecjalista`
--
ALTER TABLE `skierowaniespecjalista`
  ADD CONSTRAINT `skierowaniespecjalista_ibfk_1` FOREIGN KEY (`idOd`) REFERENCES `pracownik` (`id`),
  ADD CONSTRAINT `skierowaniespecjalista_ibfk_2` FOREIGN KEY (`idDo`) REFERENCES `pracownik` (`id`),
  ADD CONSTRAINT `skierowaniespecjalista_ibfk_3` FOREIGN KEY (`idPacjenta`) REFERENCES `pacjent` (`id`);

--
-- Constraints for table `wizyta`
--
ALTER TABLE `wizyta`
  ADD CONSTRAINT `wizyta_ibfk_1` FOREIGN KEY (`idLekarza`) REFERENCES `pracownik` (`id`),
  ADD CONSTRAINT `wizyta_ibfk_2` FOREIGN KEY (`idPacjenta`) REFERENCES `pacjent` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
