CREATE TABLE `carDb`.`car` (
                               `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                               `number` VARCHAR(40) NOT NULL UNIQUE,
                               `brand` VARCHAR(40) NOT NULL,
                               `model` VARCHAR(40) NOT NULL,
                               `color` VARCHAR(40) NOT NULL,
                               `year` VARCHAR(40) NOT NULL,
                               `added` VARCHAR(40) NOT NULL
)