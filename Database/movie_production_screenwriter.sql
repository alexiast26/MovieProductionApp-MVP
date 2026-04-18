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
-- Table structure for table `screenwriter`
--

DROP TABLE IF EXISTS `screenwriter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `screenwriter` (
  `age` int NOT NULL,
  `screenwriter_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`screenwriter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `screenwriter`
--

LOCK TABLES `screenwriter` WRITE;
/*!40000 ALTER TABLE `screenwriter` DISABLE KEYS */;
INSERT INTO `screenwriter` VALUES (40,1,'Mihai Misu','Male'),(62,2,'Aaron Sorkin','Male'),(38,3,'Phoebe Waller-Bridge','Female'),(65,4,'Charlie Kaufman','Male'),(53,5,'Taylor Sheridan','Male'),(38,6,'Emerald Fennell','Female'),(54,7,'Noah Baumbach','Male'),(66,8,'Spike Lee','Male'),(45,9,'Jordan Peele','Male'),(47,10,'Jonathan Nolan','Male'),(59,11,'Guillermo del Toro','Male'),(75,12,'Andrew Lloyd Webber','Male'),(70,13,'Joel Schumacher','Male'),(44,14,'Chris Van Dusen','Male'),(54,15,'Shonda Rhimes','Female'),(74,16,'Pedro Almodóvar','Male'),(49,17,'Alan Barillaro','Male'),(45,18,'Dan Dinu','Male');
/*!40000 ALTER TABLE `screenwriter` ENABLE KEYS */;
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
