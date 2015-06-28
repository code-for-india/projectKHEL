CREATE DATABASE kheldb;
 
USE kheldb;

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` text NOT NULL,
  `api_key` varchar(32) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '1',
  `role`  varchar(5) NOT NULL DEFAULT 'user',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
);

CREATE TABLE IF NOT EXISTS `attendance` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `held_on` date NOT NULL,  
  `location_id` int(15) NOT NULL,
  `coordinators` varchar(255) NOT NULL,
  `modules` varchar(255) NOT NULL,
  `beneficiaries` varchar(1000) NOT NULL,
  `comment` varchar(500) NOT NULL,
  `rating`  int(2) NOT NULL DEFAULT 5,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_at` timestamp,  
  `user_submitted` varchar(100) NOT NULL,  
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `location` (
  `id` int(15) NOT NULL AUTO_INCREMENT, 
  `name` varchar(100) NOT NULL,  
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `coordinator` (
  `id` int(15) NOT NULL AUTO_INCREMENT, 
  `name` varchar(100) NOT NULL,  
  `role` varchar(50) NOT NULL,  
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `module` (
  `id` int(15) NOT NULL AUTO_INCREMENT, 
  `name` varchar(100) NOT NULL,  
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `beneficiary` (
  `id` int(15) NOT NULL AUTO_INCREMENT, 
  `location_id` int(15) NOT NULL, 
  `name` varchar(100) NOT NULL,  
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  
  PRIMARY KEY (`id`),
  UNIQUE KEY `location_id` (`location_id`)
);

CREATE TABLE IF NOT EXISTS `mastersync` (
  `id` int(3) NOT NULL AUTO_INCREMENT, 
  `name` varchar(50) NOT NULL,  
  `synctime` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
);

INSERT INTO `mastersync` (`id`, `name`, `synctime`) VALUES
(1, 'locations', 1),
(2, 'modules', 1),
(3, 'coordinators', 1),
(4, 'beneficiaries', 1);


ALTER TABLE beneficiary
ADD FOREIGN KEY (location_id) REFERENCES location(id);

-- For Issue #8 - Age, and Sex fields for beneficiaries 

ALTER TABLE `beneficiary` 
ADD `class`	varchar(10) NULL AFTER `name`,
ADD `age` INT( 3 ) NOT NULL DEFAULT 0 AFTER `class` ,
ADD `sex` CHAR( 1 ) NOT NULL DEFAULT 'M' AFTER `age`;

ALTER TABLE attendance
ADD FOREIGN KEY (location_id) REFERENCES location(id);

ALTER TABLE attendance
ADD `modeoftransport` `mode_of_transport` VARCHAR( 50 ) NULL,
ADD `ratingorgobjectives` `rating_org_objectives` INT( 2 ) NULL DEFAULT '5' ,
ADD `ratingfunforkids` `rating_funforkids` INT( 2 ) NULL DEFAULT '5',
ADD `debriefwhatworked` `debrief_what_worked` varchar(100) NULL,
ADD `debrieftoimprove` `debrief_to_improve` varchar(100) NULL,
ADD `debriefdidntwork` `debrief_didnt_work` varchar(100) NULL;

ALTER TABLE attendance
CHANGE `rating` `rating` INT( 2 ) NULL DEFAULT '5',
CHANGE `comment` `comment` VARCHAR( 500 ) NULL,   
CHANGE `rating` `ratingSessionObjectives` INT( 2 ) NULL DEFAULT '5';
  
ALTER TABLE `beneficiary` 
CHANGE `age` `age` INT( 3 ) NULL DEFAULT '0',
CHANGE `sex` `sex` CHAR( 1 ) NULL DEFAULT 'M';



