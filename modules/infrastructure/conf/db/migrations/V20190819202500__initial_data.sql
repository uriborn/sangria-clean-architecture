INSERT INTO `droid` (`id`, `name`, `primary_function`)
VALUES
  (1,'C-3PO','Protocol'),
  (2,'R2-D2','Astromech');

INSERT INTO `droid_episode` (`droid_id`, `episode_id`)
VALUES
  (1,1),
  (1,2),
  (1,3),
  (2,1),
  (2,2),
  (2,3);

INSERT INTO `episode` (`id`, `name`)
VALUES
  (1,'NewHope'),
  (2,'Empire'),
  (3,'Jedi');

INSERT INTO `human` (`id`, `name`, `home_planet`)
VALUES
  (1,'Luke Skywalker','Tatooine'),
  (2,'Darth Vade','Tatooine'),
  (3,'Han Solo',NULL),
  (4,'Leia Organa','Alderaan'),
  (5,'Wilhuff Tarkin',NULL);

INSERT INTO `human_episode` (`human_id`, `episode_id`)
VALUES
  (1,'1'),
  (1,'2'),
  (1,'3'),
  (2,'1'),
  (2,'2'),
  (2,'3'),
  (3,'1'),
  (3,'2'),
  (3,'3'),
  (4,'1'),
  (4,'2'),
  (4,'3'),
  (5,'1'),
  (5,'2'),
  (5,'3');
