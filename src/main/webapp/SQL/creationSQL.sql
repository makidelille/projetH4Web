SET character set utf8;

CREATE TABLE `users` (
	`name` varchar(128) NOT NULL,
	`hashPass` varchar(128) NOT NULL,
	`description` varchar(512),
	`imageProfil` varchar(512),
	PRIMARY KEY (`name`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE photos (
	`id` INT NOT NULL AUTO_INCREMENT,
	`auteur` varchar(128) NOT NULL,
	`titre` varchar(128) NOT NULL,
	`description` varchar(1024),
	`image` varchar(512) NOT NULL,
	PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE `comments` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`auteur` varchar(128) NOT NULL,
	`photoId` INT NOT NULL,
	`reponse` INT,
	`commentaire` varchar(1024) NOT NULL,
	`couleur` char(6) DEFAULT '000000',
	`date` DATE NOT NULL,
	PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

ALTER TABLE photos ADD CONSTRAINT `Photos_fk0` FOREIGN KEY (`auteur`) REFERENCES `users`(`name`);

ALTER TABLE `comments` ADD CONSTRAINT `Commentaires_fk0` FOREIGN KEY (`auteur`) REFERENCES `users`(`name`);

ALTER TABLE `comments` ADD CONSTRAINT `Commentaires_fk1` FOREIGN KEY (`photoId`) REFERENCES photos(`id`);

ALTER TABLE `comments` ADD CONSTRAINT `Commentaires_fk2` FOREIGN KEY (`reponse`) REFERENCES `comments`(`id`);

