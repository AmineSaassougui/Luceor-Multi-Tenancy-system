-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 29 sep. 2023 à 17:29
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `aminelluceor2`
--

-- --------------------------------------------------------

--
-- Structure de la table `routers`
--

CREATE TABLE `routers` (
  `idr` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `quantity` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `admin_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `routers`
--

INSERT INTO `routers` (`idr`, `description`, `name`, `price`, `quantity`, `status`, `admin_id`) VALUES
(16, 'orange router ', 'orange R2', 20, 13, 'true', 1),
(23, 'aa', 'aaa', 20, 20, 'true', 1),
(8, 'first router', 'RADES', 3, 20, 'true', 2);

-- --------------------------------------------------------

--
-- Structure de la table `router_rentals`
--

CREATE TABLE `router_rentals` (
  `id` bigint(20) NOT NULL,
  `rental_date` datetime DEFAULT NULL,
  `return_date` datetime DEFAULT NULL,
  `router_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `router_rentals`
--

INSERT INTO `router_rentals` (`id`, `rental_date`, `return_date`, `router_id`, `user_id`) VALUES
(2, '2023-09-20 19:36:55', NULL, 8, 1),
(3, '2023-09-20 19:42:09', NULL, 8, 1),
(5, '2023-09-22 18:45:44', NULL, 8, 1),
(10, '2023-09-23 20:14:37', NULL, 16, 3),
(11, '2023-09-23 20:14:45', NULL, 23, 3),
(12, '2023-09-23 20:14:48', NULL, 23, 3),
(15, '2023-09-25 12:41:41', NULL, 8, 1),
(22, '2023-09-26 16:17:20', NULL, 16, 1);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `contact_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `address`, `contact_number`, `email`, `name`, `password`, `role`, `status`) VALUES
(1, 'a', '98462354', 'siwar@mailinator.com', 'siwar', 'testsiwar', 'ADMIN', 'true'),
(2, NULL, '98462354', 'ridha@mailinator.com', 'ridha', 'testlyoum', 'ADMIN', 'true'),
(3, NULL, '98462354', 'fethia@mailinator.com', 'fethia', 'password', 'USER', 'true'),
(4, NULL, '98462354', 'ichrak@mailinator.com', 'ichrak', 'password', 'USER', 'true'),
(5, 'aa', '20313348', 'amal@mailinator.com', 'amal', 'password', 'USER', 'true'),
(6, 'aamama', '98437302', 'hamza@mailinator.com', 'hamza', 'hamzapass', 'USER', 'false');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `routers`
--
ALTER TABLE `routers`
  ADD PRIMARY KEY (`idr`),
  ADD KEY `FKnve2xds3yrgo72tjrqm7ej0ve` (`admin_id`);

--
-- Index pour la table `router_rentals`
--
ALTER TABLE `router_rentals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj8ywnlxp8fo5rsv5m0wjnve4t` (`router_id`),
  ADD KEY `FKf3nfyf6sndjn7rfb2lxv7u1jo` (`user_id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `routers`
--
ALTER TABLE `routers`
  MODIFY `idr` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT pour la table `router_rentals`
--
ALTER TABLE `router_rentals`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
