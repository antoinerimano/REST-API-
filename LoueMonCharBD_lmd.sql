--
-- Script de population de la base de données de LoueMonChar
--


-- Ajout des types d'utilisateur

INSERT INTO `autobd`.`type_utilisateur` (`id`, `type`) VALUES ('1', 'Proprio');
INSERT INTO `autobd`.`type_utilisateur` (`id`, `type`) VALUES ('2', 'Client');



-- Ajout des adresses

INSERT INTO `autobd`.`adresse` (`id`, `appartement`, `numéro_municipal`, `rue`, `ville`, `état`, `code_postal`, `pays`) VALUES ('1', '1', '1234', 'viau', 'montreal', 'QC', 'H1M3K5', 'canada');
INSERT INTO `autobd`.`adresse` (`id`, `appartement`, `numéro_municipal`, `rue`, `ville`, `état`, `code_postal`, `pays`) VALUES ('2', '2', '5678', 'beaubien', 'montreal', 'QC', 'H1M5H7', 'canada');
INSERT INTO `autobd`.`adresse` (`id`, `appartement`, `numéro_municipal`, `rue`, `ville`, `état`, `code_postal`, `pays`) VALUES ('3', '3', '9101', 'rosemont', 'montreal', 'QC', 'H2K3R5', 'canada');
INSERT INTO `autobd`.`adresse` (`id`, `appartement`, `numéro_municipal`, `rue`, `ville`, `état`, `code_postal`, `pays`) VALUES ('4', '4', '5721', 'jarry', 'montreal', 'QC', 'H1K9H8', 'canada');



-- Ajout des utilisateurs

INSERT INTO `autobd`.`utilisateur` (`code`, `type_id`, `nom`, `prénom`, `courriel`, `adresse_id`, `téléphone`, `numPermis`, `id_auth0`) VALUES ('1', '1', 'Elatris', 'Adham', 'adham@gmail.com', '1', '5149999999', 'RIMA1902932131', 'auth0|656fe9c419c6480cbd88e491');
INSERT INTO `autobd`.`utilisateur` (`code`, `type_id`, `nom`, `prénom`, `courriel`, `adresse_id`, `téléphone`, `numPermis`, `id_auth0`) VALUES ('2', '1', 'Tir', 'Young', 'young@gmail.com', '2', '5142222222', 'OPID1905632131', 'auth0|657fa10db037b915578c9eb9');
INSERT INTO `autobd`.`utilisateur` (`code`, `type_id`, `nom`, `prénom`, `courriel`, `adresse_id`, `téléphone`, `numPermis`, `id_auth0`) VALUES ('3', '2', 'Marcelin', 'Billy', 'billy@gmail.com', '3', '5147777777', 'OPIU908732890', 'auth0|657fa13cd4eada6b02d20a35');
INSERT INTO `autobd`.`utilisateur` (`code`, `type_id`, `nom`, `prénom`, `courriel`, `adresse_id`, `téléphone`, `numPermis`, `id_auth0`) VALUES ('4', '2', 'Rimano', 'Antoine', 'antoine@gmail.com', '4', '5143333333', 'RIOA1902362131', 'auth0|657f9c9eece6959078dae0b2');



-- Ajout des voitures

INSERT INTO `autobd`.`voiture` (`code`, `code_propriétaire`, `marque`, `transmission`, `modèle`, `année`, `image`, `etat`, `prix`) VALUES ('1', '2', 'honda', 'Automatique', 'civic', '2015', 'honda.png', 'Disponible', '400');
INSERT INTO `autobd`.`voiture` (`code`, `code_propriétaire`, `marque`, `transmission`, `modèle`, `année`, `image`, `etat`, `prix`) VALUES ('2', '1', 'mazda', 'Automatique', 'cx5', '2013', 'mazda.png', 'Disponible', '300');
INSERT INTO `autobd`.`voiture` (`code`, `code_propriétaire`, `marque`, `transmission`, `modèle`, `année`, `image`, `etat`, `prix`) VALUES ('3', '4', 'nissan', 'Manuel', 'rogue', '2012', 'nissan.png', 'Indisponible', '250');
INSERT INTO `autobd`.`voiture` (`code`, `code_propriétaire`, `marque`, `transmission`, `modèle`, `année`, `image`, `etat`, `prix`) VALUES ('4', '3', 'ford', 'Manuel', 'focus', '2016', 'ford.png', 'Indisponible', '600');



-- Ajout des réservation

INSERT INTO `autobd`.`reservation` (`code`, `utilisateur_id`, `voiture_id`, `date`) VALUES ('1', '4', '3', '2023-02-16');
INSERT INTO `autobd`.`reservation` (`code`, `utilisateur_id`, `voiture_id`, `date`) VALUES ('2', '2', '4', '2020-11-19');
