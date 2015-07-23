/*
SQLyog Community v11.1 (64 bit)
MySQL - 5.5.41-MariaDB : Database - demo-forwarding
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`demo-forwarding` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `demo-forwarding`;

/*Table structure for table `base_users` */

DROP TABLE IF EXISTS `base_users`;

CREATE TABLE `base_users` (
  `id` BIGINT(20) DEFAULT NULL,
  `username` VARCHAR(100) DEFAULT NULL,
  `pwd` VARCHAR(100) DEFAULT NULL,
  `email` VARCHAR(100) DEFAULT NULL
) ENGINE=MYISAM DEFAULT CHARSET=utf8;

/*Data for the table `base_users` */

INSERT  INTO `base_users`(`id`,`username`,`pwd`,`email`) VALUES (1,'1@1.cn','c4ca4238a0b923820dcc509a6f75849b',NULL);

/*Table structure for table `mm_records` */

DROP TABLE IF EXISTS `mm_records`;

CREATE TABLE `mm_records` (
  `id` BIGINT(20) NOT NULL,
  `imei` VARCHAR(100) DEFAULT NULL,
  `imsi` VARCHAR(100) DEFAULT NULL,
  `cp_id` varchar(100) DEFAULT NULL,
  `charge_id` varchar(100) DEFAULT NULL,
  `content` varchar(8000) DEFAULT NULL,
  `ip` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `mm_records` */

/*Table structure for table `receives` */

DROP TABLE IF EXISTS `receives`;

CREATE TABLE `receives` (
  `id` bigint(20) NOT NULL,
  `phone` bigint(20) DEFAULT NULL,
  `dest_addr` varchar(100) DEFAULT NULL,
  `msg` varchar(100) DEFAULT NULL,
  `link_id` varchar(100) DEFAULT NULL,
  `service_id` varchar(100) DEFAULT NULL COMMENT 'in',
  `add_time` bigint(20) DEFAULT NULL,
  `from_ip` varchar(100) DEFAULT NULL,
  `status_report` tinyint(4) DEFAULT NULL COMMENT '0:success;1:failure;3:bill',
  `msg_type` varchar(100) DEFAULT NULL,
  `annotation` varchar(1000) DEFAULT NULL,
  `send_status` tinyint(4) DEFAULT NULL COMMENT '0:normal;-1:scale',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `receives` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
