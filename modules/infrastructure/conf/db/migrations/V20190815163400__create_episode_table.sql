CREATE TABLE `episode` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `human_episode` (
  `human_id` INT NOT NULL,
  `episode_id` INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `droid_episode` (
  `droid_id` INT NOT NULL,
  `episode_id` INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
