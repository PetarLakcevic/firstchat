/*
SQLyog Community v13.1.1 (64 bit)
MySQL - 10.1.36-MariaDB : Database - chat_database
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`chat_database` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `chat_database`;

/*Table structure for table `direct_message` */

DROP TABLE IF EXISTS `direct_message`;

CREATE TABLE `direct_message` (
  `user_id_sender` varchar(50) NOT NULL,
  `user_id_receiever` varchar(50) NOT NULL,
  `msg_timestamp` time NOT NULL,
  `message_text` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`user_id_sender`,`user_id_receiever`,`msg_timestamp`),
  KEY `user_id_receiever` (`user_id_receiever`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `direct_message` */

insert  into `direct_message`(`user_id_sender`,`user_id_receiever`,`msg_timestamp`,`message_text`) values 
('1','10','02:48:01','asdfasdf'),
('1','10','03:32:37','ej'),
('1','10','03:33:54','asdf'),
('1','10','03:34:49','asdf'),
('1','11','16:37:25','asdfasdf'),
('1','11','16:37:51','asdfasdf'),
('1','11','16:48:28','oooo'),
('1','11','16:49:54','asdfasdf'),
('1','11','16:49:55','asdfasdf'),
('1','11','16:52:25','zxcvzxcv'),
('1','11','16:52:26','zxcv'),
('1','11','17:18:08','asdf'),
('1','2','00:00:00','dfasdfasdf'),
('1','2','22:22:43','test porukeee'),
('1','3','00:00:00','asdfasdf'),
('1','4','04:20:15','eee'),
('1','4','04:20:26','nova poruka check'),
('1','4','17:17:01','asdf'),
('1','4','17:18:05','asdasd'),
('1','5','17:02:01','asdf'),
('1','6','04:08:20','acoo'),
('1','6','04:11:01','acaca'),
('1','6','04:59:20','proveravam fajl'),
('1','6','04:59:57','radi'),
('1','9','00:50:28','eee brate'),
('1','9','17:19:34','cvalee'),
('10','1','02:48:04','asdfasdf'),
('10','1','02:48:13','ovovovo'),
('10','1','03:32:43','oj ameg'),
('10','1','03:34:02','asdfasdf'),
('10','1','03:34:53','asdf'),
('11','1','16:37:29','asdfas'),
('11','1','16:37:30','asdf'),
('11','1','16:37:49','asdfasdf'),
('11','1','16:49:46','dfggdfg'),
('11','1','16:52:20','dsfsdf'),
('11','1','16:52:21','zxczxc'),
('2','1','00:00:00','ne'),
('4','1','04:20:09','asdf'),
('4','1','04:20:18','eee'),
('4','1','04:20:32','kida'),
('4','1','16:48:33','asd'),
('5','1','17:01:56','asdf'),
('6','1','04:08:33','petreeee'),
('6','1','04:11:06','eee'),
('6','1','04:59:09','Fajl transfer check'),
('6','1','04:59:54','radi i posle?'),
('9','1','17:19:27','asdfasdf'),
('9','1','22:28:16','Petreee'),
('9','1','22:28:36','Ejjj');

/*Table structure for table `friends` */

DROP TABLE IF EXISTS `friends`;

CREATE TABLE `friends` (
  `user_id_1` varchar(50) NOT NULL,
  `user_id_2` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id_1`,`user_id_2`),
  KEY `user_id_2` (`user_id_2`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `friends` */

insert  into `friends`(`user_id_1`,`user_id_2`) values 
('1','2'),
('1','3'),
('1','4'),
('12','1'),
('12','4'),
('13','1'),
('2','3'),
('2','4'),
('2','5'),
('3','4'),
('6','1'),
('6','9'),
('9','1'),
('9','3'),
('9','4');

/*Table structure for table `group` */

DROP TABLE IF EXISTS `group`;

CREATE TABLE `group` (
  `group_id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id_creator` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `picture` longblob,
  PRIMARY KEY (`group_id`),
  KEY `cn` (`user_id_creator`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `group` */

/*Table structure for table `group_members` */

DROP TABLE IF EXISTS `group_members`;

CREATE TABLE `group_members` (
  `group_id` bigint(10) unsigned NOT NULL,
  `user_id` bigint(10) unsigned NOT NULL,
  PRIMARY KEY (`group_id`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `group_members_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `group` (`group_id`),
  CONSTRAINT `group_members_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `group_members` */

/*Table structure for table `group_message` */

DROP TABLE IF EXISTS `group_message`;

CREATE TABLE `group_message` (
  `groupId` bigint(10) unsigned NOT NULL,
  `msg_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id_sender` bigint(10) unsigned NOT NULL,
  `message_text` varchar(200) DEFAULT NULL,
  `picture` longblob,
  PRIMARY KEY (`groupId`,`msg_timestamp`),
  KEY `user_id_sender` (`user_id_sender`),
  CONSTRAINT `group_message_ibfk_1` FOREIGN KEY (`user_id_sender`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `group_message` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `registration_date` timestamp NULL DEFAULT NULL,
  `last_login_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`username`,`password`,`status`,`registration_date`,`last_login_date`) values 
(1,'petar','petar','Second status','1970-01-22 00:00:00','2019-06-29 04:58:54'),
(2,'ivan','ivan','LoL','1970-01-20 00:00:00','2019-06-27 00:00:00'),
(3,'marko','marko','Using pchat','1970-01-23 00:00:00','2019-06-14 00:00:00'),
(4,'milan','milan','PCHAT','2019-06-19 00:00:00','2019-06-29 04:20:05'),
(5,'nikola','nikola','Pchat is cool','1970-01-31 00:00:00','2019-06-28 17:07:02'),
(6,'aca','aca','What is pchat?','1970-01-23 00:00:00','2019-06-29 04:59:02'),
(8,'petar11','123','I am using NPChat!','1970-01-23 00:00:00','2019-06-19 00:00:00'),
(9,'cvale','cvale','I am using NPChat!','2019-06-27 04:13:56','2019-06-28 22:28:06'),
(10,'mile','mile','Koristim ovo','2019-06-27 22:46:34','2019-06-28 03:34:43'),
(11,'milon','milon','asdfasdf','2019-06-28 16:36:59','2019-06-28 16:52:13'),
(12,'sara','smara','I am using NPChat!','2019-06-28 22:12:09','2019-06-28 22:32:05'),
(13,'joki','joki','I am using NPChat!','2019-06-28 22:17:51','2019-06-28 22:17:51'),
(14,'djole','djole','I am using NPChat!','2019-06-28 22:20:55','2019-06-28 22:20:55');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
