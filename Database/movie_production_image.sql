-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: movie_production
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `movie_id` int DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `FKtq2hs9xscd7jdp3r2mldk1yj9` (`movie_id`),
  CONSTRAINT `FKtq2hs9xscd7jdp3r2mldk1yj9` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (6,5,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\phantom1.png'),(7,5,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\phantom2.png'),(8,5,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\phantom3.png'),(12,7,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\bridgerton1.png'),(13,7,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\bridgerton2.png'),(14,7,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\bridgerton3.png'),(21,3,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\barbie2.png'),(22,3,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\barbie3.png'),(23,3,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\barbie4.jpg'),(24,9,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\piper1.png'),(25,9,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\piper2.png'),(26,9,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\piper3.png'),(27,10,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\romania1.png'),(28,10,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\romania2.png'),(29,10,'C:\\Users\\Alexia\\Desktop\\Facultate\\PS\\Tema1\\Images\\romania3.jpg');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-25 15:42:18
