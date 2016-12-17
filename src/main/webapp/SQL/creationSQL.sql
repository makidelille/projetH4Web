CREATE TABLE `users` (
	`name` varchar(128) NOT NULL,
	`hashPass` varchar(128) NOT NULL,
	`description` varchar(512),
	`imageProfil` varchar(512),
	PRIMARY KEY (`name`)
);

CREATE TABLE photos (
	`id` INT NOT NULL,
	`auteur` varchar(128) NOT NULL,
	`titre` varchar(128) NOT NULL,
	`desciption` varchar(1024),
	`image` varchar(512) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `comments` (
	`id` INT NOT NULL,
	`auteur` varchar(128) NOT NULL,
	`photoId` INT NOT NULL,
	`reponse` INT,
	`commentaire` varchar(1024) NOT NULL,
	`couleur` INT,
	`date` DATE NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE photos ADD CONSTRAINT `Photos_fk0` FOREIGN KEY (`auteur`) REFERENCES `users`(`name`);

ALTER TABLE `comments` ADD CONSTRAINT `Commentaires_fk0` FOREIGN KEY (`auteur`) REFERENCES `users`(`name`);

ALTER TABLE `comments` ADD CONSTRAINT `Commentaires_fk1` FOREIGN KEY (`photoId`) REFERENCES photos(`id`);

ALTER TABLE `comments` ADD CONSTRAINT `Commentaires_fk2` FOREIGN KEY (`reponse`) REFERENCES `comments`(`id`);

