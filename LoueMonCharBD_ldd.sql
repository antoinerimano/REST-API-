--
-- Script de génération de la structure de la base de données du LoueMonChar
--
CREATE SCHEMA `autobd`;
USE  `autobd`;

CREATE TABLE `adresse` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `appartement` varchar(255) DEFAULT NULL,
                           `numéro_municipal` varchar(255) NOT NULL,
                           `rue` varchar(255) NOT NULL,
                           `ville` varchar(255) NOT NULL,
                           `état` char(2) DEFAULT NULL,
                           `code_postal` varchar(255) NOT NULL,
                           `pays` varchar(255) NOT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `type_utilisateur` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `type` set('Proprio','Client') DEFAULT NULL,
                                    PRIMARY KEY (`id`))
    ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



CREATE TABLE `utilisateur` (
                               `code` varchar(255) NOT NULL,
                               `type_id` int(11) NOT NULL,
                               `nom` varchar(255) NOT NULL,
                               `prénom` varchar(255) NOT NULL,
                               `courriel` varchar(255) NOT NULL,
                               `adresse_id` int(11) DEFAULT NULL,
                               `téléphone` varchar(255) NOT NULL,
                               `numPermis` varchar(255) NOT NULL,
                               `id_auth0` varchar(255) NOT NULL,
                               PRIMARY KEY (`code`),
                               KEY `adresse_id` (`adresse_id`),
                               KEY `utilisateur_ibfk_1` (`type_id`),
                               CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type_utilisateur` (`id`),
                               CONSTRAINT `utilisateur_ibfk_2` FOREIGN KEY (`adresse_id`) REFERENCES `adresse` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



CREATE TABLE `voiture` (
                           `code` varchar(255) NOT NULL,
                           `code_propriétaire` varchar(255) NOT NULL,
                           `marque` varchar(255) NOT NULL,
                           `transmission` set('Manuel','Automatique','Hybride') DEFAULT NULL,
                           `modèle` varchar(255) NOT NULL,
                           `année` int(11) NOT NULL,
                           `image` varchar(255) DEFAULT NULL,
                           `etat` set('Disponible','Indisponible') DEFAULT NULL,
                           `prix` varchar(45) DEFAULT NULL,
                           PRIMARY KEY (`code`),
                           KEY `code_propriétaire` (`code_propriétaire`),
                           CONSTRAINT `voiture_ibfk_1` FOREIGN KEY (`code_propriétaire`) REFERENCES `utilisateur` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



CREATE TABLE `reservation` (
                               `code` varchar(255) NOT NULL,
                               `utilisateur_id` varchar(255) DEFAULT NULL,
                               `voiture_id` varchar(255) DEFAULT NULL,
                               `date` date DEFAULT NULL,
                               PRIMARY KEY (`code`),
                               KEY `utilisateur_id` (`utilisateur_id`),
                               KEY `voiture_id` (`voiture_id`),
                               CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`code`),
                               CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`voiture_id`) REFERENCES `voiture` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



SELECT * FROM autobd.utilisateur;
