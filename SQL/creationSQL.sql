CREATE TABLE `Utilisateurs` (
	`Name` varchar(128) NOT NULL,
	`hashPass` varchar(128) NOT NULL,
	`description` varchar(512),
	`imageProfil` varchar(512),
	PRIMARY KEY (`Name`)
);

CREATE TABLE `Photos` (
	`id` INT NOT NULL,
	`auteur` varchar(128) NOT NULL,
	`titre` varchar(128) NOT NULL,
	`desciption` varchar(1024),
	PRIMARY KEY (`id`)
);

CREATE TABLE `Commentaires` (
	`id` INT NOT NULL,
	`auteur` varchar(128) NOT NULL,
	`photoId` INT NOT NULL,
	`reponse` INT,
	`commentaire` varchar(1024) NOT NULL,
	`date` DATE NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `Photos` ADD CONSTRAINT `Photos_fk0` FOREIGN KEY (`auteur`) REFERENCES `Utilisateurs`(`Name`);

ALTER TABLE `Commentaires` ADD CONSTRAINT `Commentaires_fk0` FOREIGN KEY (`auteur`) REFERENCES `Utilisateurs`(`Name`);

ALTER TABLE `Commentaires` ADD CONSTRAINT `Commentaires_fk1` FOREIGN KEY (`photoId`) REFERENCES `Photos`(`id`);

ALTER TABLE `Commentaires` ADD CONSTRAINT `Commentaires_fk2` FOREIGN KEY (`reponse`) REFERENCES `Commentaires`(`id`);

