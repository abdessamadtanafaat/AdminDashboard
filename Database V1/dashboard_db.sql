-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dashboard_db
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `expired_time_email` datetime(6) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `is_used_token_email` bit(1) DEFAULT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `last_logout` datetime(6) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token_email` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `imagedata` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'tanafaat.rca.16@gmail.com','2024-03-20 23:38:25.380183','tanafaat',_binary '',_binary '','2024-03-21 00:41:28.558553','2024-03-20 23:35:31.098518','ABDESSAMAD','$2a$10$AWOgId6XTSow.bLimPqJTOJb5IfjbCuo2yWGcpcGA8P5a4Mj4EpcS','WrtxTGfBBRGE-2oM-kOeHQa58W22rQWXexFCakkot9FVwTZ6SlVLcdBgKsf8EsL1LJrieBxfaJce94K3ueLewg','/avatars/tanafaat_ABDESSAMAD_avatar.png',_binary 'xڥX�7��\�W\�h\�b�֨�-%}\�WQE\��F\�#�j���\�;*�\�#�B\�o?\���s��9\��<?\��\����H�/ÿddd�\�Jr:dd�\����\��v\Z-z�\�\��i\�Rӓ�������\�\r�\���{G�[�\Z��j�s�:\�\�x7kFw\�f}��T��?1�����!t\�~�WM��\�\�.Oۗ+#ۭz\\�7w,W�#�b:��a{�p����_0\�:\��\�u\�|\�\�\�\�g\�\�҉�����{�n\r\n�ĭ���z�\�>�\�\�?۝�\�\�\�k?��\�8�7�8$\�\��B\Z\���-�Ϗ\"ۿ�Gc\����^M\�\�.���\�v��	6�ߧa6on\����2#]���A�\�أU\�&��^�\�ɴql�dgvw�x~\�R/��\�$o$�c�z�0��\�\�\�\�\�Y����\�f^]_\�\�c\�\�w�\�|[�\�grw�\�n纙\�ɝ\�B\��)οU?k(0}\�?��\����^���\�HD\�j�W�\�\�չG�Jڀ�O�f\�dJ\\�\�\�F[\�\\�o�V\�`�\�.����9�1��|\�jX�\'\Z���i��p4�\��(�\�zb�?e\�ޠ0��,�H\�Ym\�>ZvoP�t;�@��mw��___g\���!�z\�\Z	�{�>��}L�\\A.:tx�z0szA<�$\rl��lu�\�VL�^^]x7k\�M\�TOe��ŏlu\�Oq�c	�M�YCA��BP���vy#���n]+��\�ٸ\��\"L\\�zs �\��%)}��u�dao,iܹR�u�\�\�ܾT�u��=^]ޟ\�>Z\�;�\�\��-�B\�5\���Z�Nw\�)�^�{\��m�0\�]�\�	i��=.I���VÜ\�\�\�պqlo\�tvH�i\�Z\�2~\�\�x\�Otx\�tj�|�r4����HYNZ\�\'ko\�,\�`�\�\��\�l�2\\d^/\����@�V��X͎=<&�\"R$n�IPIlJU[Ё�,g\�\�_	\�g�\�\�3#�x\�c��m�\�\�o{\��Q\�7����3\��\'|�6Ҭ\�+�\�\�?��\�qJ��j�婛:0�\�]L-�z\'-�r�Bu�z�\�#XaP�)�}X�y��6\�,\�R\�DI��>�f�Y�q6 �,c\�G?P:� ��ɨK4=\�l��5�;�\�8��\�K�=H�	1\�)�=l��\�k�o�	���#ߊ\�W6�J�Ƹ�7��iQR�\�ǌ*�O	\�Cs\���xGl\Z7��[\�g~���\�\Z@9�͘\�*�\�X���T�X\��V��/BL�4@�\����\0xh�\�?\�7�-\�G�PTw�\n�+F�(� /\�(w�\�Q�T�@\�\Z�r\��>�l\���#\n1\�bnO\nҨЩT5\�\n��C�(�\�\�O\�\0��\�ɐ��?�\��3֘\�b�\�]�-2m�0H�\�-\��o��%l�\�\�+<\�\�3�\rv\��@,�\"\�?\�m:���Q;��slE��8*m齒��[�q9���w���d�?\�>\�A?�K�dS\���?�l\�Q\�lK\�Օk�\�3�i�+=s\�nT\�\�\�ʉT�yX\�ʠ\�-�ĳ47?��]\�+\�\�#���S�\"\��_\�\�صe�+��z�=\�[U{\�#�\�a�\�v�\�, �!��.97�-\�N�\�;\��J7\\��������\n\\X\�V8�\�FL��\�]\�Pe($ÿ>�\�D\�\�\�\�\�Pe~h;�ä��$��Rݧ[��Ü\�5�p\�J\�V\��G�\���V\�Q�W�д�z\�\�\�\�̷�m;���\�;��\�|\n�\'\��\�h�e	\�\�\�AP\�N�O�C\r��ߙF`ߪ\�t\�x\�\�\��njj?\�\'\�\��\�O�\�kM�O䡤8}\�\rt\0niG�#<��c��O\�\�.PJ\�)Dg�\�>\r$\�\"T\�\��ۢԁ��0\�\'huϒ�	R���O\�͵13<�����\�x\�r㨉�Z?\�q\0�o�5f)6�0�=犻�\n�k\�\r�8\�\�Gg*\"��つ��\��=\�\��χ��h�\��`\�5\�c�u$\�E�f\�#�SjFts�Z*\�7Zx\�}\�?>$\�XT(]���\���Iwd��\�\�Z;U\Z{�t<a�FD\0ds\�\�D\Zt_ z�o|�4\��Z��\�\���M�aY�3S\�||�G\�F4� ڣ��$�ym�p�$\��ߌ��5=���\�Y1�iL��j,jA\�l(\�$F\�b\�\�7��Z\�\�3���,�O���3\�<F\�\�J\�C�n\�:_x����x�	Y��&���\�\�a\�߈\�\�\�!Y\�s��O�LW曩RN�0�P���\�i\��K�JW\�W����vm_X�E(\�nJ�����Kt\�f�?�6�A��\��@V\�y��%��j[\��\�eU+���\Z���E��w�\�\�\�~�\'o!e�I�z�z�E��\�_Ocj]��m���o͔8�*r��`��wԶ76\�\�*��z@�6)LOW��tp\�7\0�\"\�>�\�\���O%�z�,\�:\�\�]D\�*d\�?�tK\�\�\�c��o����\�>�\�t�\�K`��x\�h2%�Vij�jC`|\�.����Y,�\r0�X���p�.�3�-^�\��w�k\�)\�|m>�h�{d����_��t\�6kwL\��8^�!X��\r�Ś�hhr,�0�n��b\�[��E��wu�\��\�\�\\!tɳ\�\�X�\�==c=z\�Si��ɮ�:�+	\�­K>{-:P,\�oz\�\�\�&Rt�\�}a�\�.�:1�L\�\�M\�:y\�%w��P\�Y\�_)|�6n��ĸ�\��E�ug9z�0\�TP�\�e�d\'|�@=\�TA�\�Y�>��\rD�\\QS\�OU~�;\�\'��E\ZR�����a\���\�5\�\�s*����\�\��7\�e�d�9Q4=�K)ׁ\���?\�\�U6..6T#�/��$\�٥o�{Q\�H�~�\�5N�0��h�s~\�\�Ȫ7\�19��\��igF�eA�uPv�EP(|{\�\�\�׸`\��M\n���\���E�<)�P\�\�\�_�\�D�p\�]z��\�[[u�ce2̣�J+�}^\�P�*\�:��\���I\�w���d4��\�zI\�*-�L:T�\�wg\�%�}��\�\�wz�n��\rz7��t.jV<\�=E�Fx{�\�ܝ�\�G`;OWF]\�v�fG0�\�v=\�@�\��2Pwk1���4\\�	��\�YnZ\�\��Xm��U��4�\\Φ��N�\���3I!\�n�$�j�D���\�O�\�ч;n�:	����I�	\�sM5�6#\�I鵓�\�>?\�˘\��\\���Bq�Wq\��\�\�Q{\�Ϣ�DDb�t�\�r��;�]qw|�\�$͚4\�锍�	�$���pwJ�\�\�\�D%ݾ\ZM�\�\\\�މ\�\�F��i\�%��w.�Ҩ:?||!��X_��]�\���NLK�\n@[L�1�&:\�y\��\�m��෍t�RD��\�U\n�j\0�\ZD�EA#C9\�\�Wq�Orե�*�0��v���Ə��CDfԾ+\��-؏��|�E\�ܪ\�t�-\�\�x/�nx�\��3ap�8t��ƼÀ\�Of�\�yy7�v\�U\��E	�*%��\�ٽ\�\���-\�o\�vՕ�\�\�%Po\�]Km��u�\����L�\�c�¸\�\�?��R������־b:z�k\��C�Z��\�_���A���\r\�\Z�d\�p�$m�E\�z�A�˰�\�\�uK=\�Je\�:�9�:ӎ\�Ҭl�g+s�\�y��\�\Z�J\�v\�9z\��f-���G\'�oh{�L,��V\�����Ss�\�o��\�\�I\� !`\0�4l�sr\�k\"ȿux~^\Z4\� \�nz�X�d)H�\Z~�����o]3\���*E\�\���|�=V\�y\"\n��\�|\�\��\���J>�G\�\��..N.�G�\��(�\�෪z�UMqm\�\0�|\�{\� Y�\�[��:\����Z�\"�\�\��a?\�XKO�f\�C�\�(-|�\�y@\�j\�\�70x�dpDJj���\�\�D����\�lWf������(�	�[��	}S\�2i�`-Sh\�}p�F5�\����Y>d���\�N\�\�x�-��\'ZO\n\�)oP+˕\���]\����Z\�󢸁\�\�%2�1���G��z;��ZUꄲЫ�r�a\\-�ELf\�\�\�����G?�\�K]q\�hJ\��C�4ש\�~\�tw8�6f�5_�@㞱[\'\�4���\�(��d�\��\0z3u�\�g��]`1\�V{\��>3�64?�5\��W�a\�\�~\rĐ��l\�\n)\�;N1�`�\�Mcܣ�7;��&� ��\�\����\�R\'a���4�m\�,�?��y\0�J^\�\�5�|q\�n�U��O�tQ�\�x�[\�_�Jd0╺o�,���\�G\�\0�9c&\�\�z�1�\��$�mAv�\n-Upj��&=��c&M�\�\�\�6V\���T\�\�c�\�f�\�����+,\���UR��G�n`�(�\�\�-��}{l��2�_\��@dU4?\�>K���[�5�̩N�Q�\�\�\'�.�siº��ogR2e�\�O�޼�\�a�)\�\�J׽P]\Z\�LMp�j�kpxPR�\��\�\�#پ\�qĜ듻/Q�g\�?\�\�}�	��,\�W?7.�\0{>\�À\�|񽢺\�Y\�\�Q�\�\�\�K!5䅋\�\�L]��6C\�\�&Z\�R\�Z��\�e�}�\��X��>�M\�\�&Y4��ݼ\�5\�7੸]��\�\��\�;�1\�\�\�U\�Z��\�g��S͐\��\��\ZRj��-\�s�[\�\�\�n�$�F�\�i�z\�)\'G�\�\�͛�׬\'᪨\r9y9���Tْ$[LJ@N�\�\�]\\,U\�\�_.[\�\�I&\�J���؛���N\��ǖp�\�\�R�\Z�\� \��\�	=s%�\��@�aG�\"�sج��v�Q�?��T��J\�\�}ņN\�jJ��i�`\�^��E\�̆��D��d\�IK�q�I��?F�\�\�Ҝ!.\'B�Pz�%Mn� \�>��Fԍg�����@��{���\�\ZIs\��4K\�0�I\�CGa��.���\��׷oj����\�~nmu\�\�m\��I49F8�z�Q*h\�\�=V?`!\Z(j/�� �_�\ZX:ՙy\��XH�h\��-h�IZ�\�z����\�\r�#dy$�2_e��\�|\�\�$n���\��ټ��*c��]�W��M�\��Z\�(�>\\�.\�\�z+��q\�_��6\�v\'��L�-�`�^�\�_�<H\�(,\�C\��\�W?ov��7^�X�\�\�.�{\�n\�>�`\�կc7>��n�Xu��\�4Ѩ�\�kKWL��Y4}��q�YT�\�\�\�\�~EN�\�\�l\r��\�\�ώ#iM\�gz��uz^��E�ĩ���\�f\�\�.ێ��L:_\�9�\�Z\�\�H\�0����~�13;}��{��\�\�%�|+�r\0h%�VkGD3\"(��ZR!ؓ\��8O�\�\�f`\�A#/]\�tDժ�߂wY�\�~\�I7��\Z,�\�FD-F\�A\�����ӝQ\�\"��Q\�\�u�4�\�摖�w\�\�l�qzB�^�x�=\�1�0r~�ME��\�\�\�\�m��\�a$4$��� �ɜHŗ��+�]0A=\"���{a�\��Ң�/7\�\��׊,\�bߺI�#\�\06\�N�K��ϰuq\�\�{�����Uޙ�V�\Z\�;�1�	�Q�\'@\0/�\Z9�����(����\�N���S<��\�b�ٶ\�ჺr7o\�6�o(��j\�0Qc\��\�i�G1�RSB��U?��_\n\�	˓ǴO>v~+I=Mď�R�^���\�	�I����lp^�9�\�PbY|�\�#�҂a\�L\��\�<4T\�#?��<4��\�)ʊ���\�\��gr�*c\��Ɇ8y\�j��\rW\�\\�\�F\�g׃�\' �z=N�xH\��s�`�g�\�%\�\�/\�8�\�a.?��\�7�\� �| ��<��/O�/�C\\[k:I�\��\�@m�\��\�ę\�\�P\�\��R\npfy�zb[\���ņ��<��c�w\�D�\r\�[\��K1+ĸ\�\'���\"V����\�3����X��\�r��ZY^C�B\�s\��gr\�'),(2,'ilias.rouchdi21@gmail.com',NULL,'Rochdi',_binary '\0',_binary '\0','2024-03-15 15:22:37.000000','2024-03-15 21:23:59.000000','ilias','$2a$10$re49u6BlEFqualsU0W62neH0pFzHHRdZG2irwOGNhu0UDMlt.x33u',NULL,NULL,NULL),(3,'mohamed.elamrani@gmail.com',NULL,'Mohamed',_binary '',_binary '\0','2024-03-17 18:52:12.004129','2024-03-17 17:13:01.052687','El Amrani','$2a$10$S/pmRCsasuLe8kISg8TbFeY7UtxzWwf648qeu25jYJ3ojFk6fo1GK',NULL,NULL,NULL),(4,'ahmed.besbrahim@gmail.com',NULL,'ahmed',_binary '',_binary '\0','2024-03-19 01:30:44.795603','2024-03-18 12:16:01.063237','BENBRAHIM','$2a$10$RxcT1V2A19qz9xWHj5K/9eD.8Pe7a.oive90oA3bQd1K9JZBpBdrq',NULL,NULL,NULL),(5,'fatima.elkhattabi@gmail.com',NULL,'Fatima',_binary '\0',_binary '\0','2024-03-15 15:22:37.000000','2024-03-15 21:29:45.000000','El Khattabi','$2a$10$VbTuJ/bZzLcJBy.G53ciVu5ea3M8cMDbyVW1/LOI8tJsNIUKNHSfO',NULL,NULL,NULL);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_role`
--

DROP TABLE IF EXISTS `admin_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_role` (
  `admin_id` int NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKox1vbfj0x7ta1nk14np291n9k` (`role_id`),
  KEY `FKcxtbmnff43w12d3v2r8fwufaf` (`admin_id`),
  CONSTRAINT `FKcxtbmnff43w12d3v2r8fwufaf` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `FKox1vbfj0x7ta1nk14np291n9k` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_role`
--

LOCK TABLES `admin_role` WRITE;
/*!40000 ALTER TABLE `admin_role` DISABLE KEYS */;
INSERT INTO `admin_role` VALUES (1,1),(2,1),(3,2),(4,2),(5,2);
/*!40000 ALTER TABLE `admin_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business`
--

DROP TABLE IF EXISTS `business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `business_name` varchar(255) DEFAULT NULL,
  `cover_image_url` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `facebook_link` varchar(255) DEFAULT NULL,
  `google_link` varchar(255) DEFAULT NULL,
  `instagram_link` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd3ny1lcneok00x0d848b5ufhi` (`type_id`),
  KEY `FKjkjjimeu5p0gv4orhue734xni` (`user_id`),
  CONSTRAINT `FKd3ny1lcneok00x0d848b5ufhi` FOREIGN KEY (`type_id`) REFERENCES `business_type` (`id`),
  CONSTRAINT `FKjkjjimeu5p0gv4orhue734xni` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business`
--

LOCK TABLES `business` WRITE;
/*!40000 ALTER TABLE `business` DISABLE KEYS */;
INSERT INTO `business` VALUES (1,'123 Rue Mohammed V, Casablanca','La Pâtisserie du Maroc','patisseriedumaroc_cover.jpg','2024-03-15 15:08:54.000000','patisseriemaroc@gmail.com','https://www.facebook.com/patisseriedumaroc','https://maps.google.com/patisseriedumaroc','https://www.instagram.com/patisseriedumaroc','+212612345678',1,1),(2,'456 Avenue Hassan II, Marrakech','Le Café de Marrakech','cafemarrakech_cover.jpg','2024-03-15 15:08:54.000000','cafemarrakech@gmail.com','https://www.facebook.com/cafemarrakech','https://maps.google.com/cafemarrakech','https://www.instagram.com/cafemarrakech','+212645678901',2,2),(3,'789 Rue Mohammed VI, Rabat','Le Restaurant du Coin','restaurantducoin_cover.jpg','2024-03-15 15:08:54.000000','restaurantducoin@gmail.com','https://www.facebook.com/restaurantducoin','https://maps.google.com/restaurantducoin','https://www.instagram.com/restaurantducoin','+212678901234',3,3),(4,'1010 Boulevard Mohamed, Fès','Glace du Soleil','glacedusoleil_cover.jpg','2024-03-15 15:08:54.000000','glacedusoleil@gmail.com','https://www.facebook.com/glacedusoleil','https://maps.google.com/glacedusoleil','https://www.instagram.com/glacedusoleil','+212609876543',4,4),(5,'555 Avenue Hassan II, Marrakech','La Pâtisserie des Roses','patisserieroses_cover.jpg','2024-03-15 15:08:54.000000','patisserieroses@gmail.com','https://www.facebook.com/patisserieroses','https://maps.google.com/patisserieroses','https://www.instagram.com/patisserieroses','+212621234567',1,5),(6,'777 Rue Mohammed V, Casablanca','Café de la Médina','cafedelamedina_cover.jpg','2024-03-15 15:08:54.000000','cafedelamedina@gmail.com','https://www.facebook.com/cafedelamedina','https://maps.google.com/cafedelamedina','https://www.instagram.com/cafedelamedina','+212654321098',2,6),(7,'888 Avenue Mohammed VI, Tanger','Restaurant de la Tour','restaurantlatour_cover.jpg','2024-03-15 15:08:54.000000','restaurantlatour@gmail.com','https://www.facebook.com/restaurantlatour','https://maps.google.com/restaurantlatour','https://www.instagram.com/restaurantlatour','+212687654321',3,7),(8,'1212 Boulevard Hassan II, Agadir','Glace de la Lune','glacedelalune_cover.jpg','2024-03-15 15:08:54.000000','glacedelalune@gmail.com','https://www.facebook.com/glacedelalune','https://maps.google.com/glacedelalune','https://www.instagram.com/glacedelalune','+212690123456',4,8),(9,'333 Avenue Mohammed V, Rabat','La Pâtisserie Orientale','patisserieorientale_cover.jpg','2024-03-15 15:08:54.000000','patisserieorientale@gmail.com','https://www.facebook.com/patisserieorientale','https://maps.google.com/patisserieorientale','https://www.instagram.com/patisserieorientale','+212601234567',1,9),(10,'444 Rue Hassan II, Tanger','Café du Soir','cafedusoir_cover.jpg','2024-03-15 15:08:54.000000','cafedusoir@gmail.com','https://www.facebook.com/cafedusoir','https://maps.google.com/cafedusoir','https://www.instagram.com/cafedusoir','+212643210987',2,10),(11,'999 Boulevard Mohammed VI, Essaouira','Restaurant de la Plage','restaurantplage_cover.jpg','2024-03-15 15:08:54.000000','restaurantplage@gmail.com','https://www.facebook.com/restaurantplage','https://maps.google.com/restaurantplage','https://www.instagram.com/restaurantplage','+212676543210',3,11),(12,'1313 Rue Mohammed V, Marrakech','Glace de l\'Oasis','glacedeloasis_cover.jpg','2024-03-15 15:08:54.000000','glacedeloasis@gmail.com','https://www.facebook.com/glacedeloasis','https://maps.google.com/glacedeloasis','https://www.instagram.com/glacedeloasis','+212609876543',4,12),(13,'7777 Avenue Hassan II, Fès','La Pâtisserie du Jardin','patisseriejardin_cover.jpg','2024-03-15 15:08:54.000000','patisseriejardin@gmail.com','https://www.facebook.com/patisseriejardin','https://maps.google.com/patisseriejardin','https://www.instagram.com/patisseriejardin','+212621345678',1,13),(14,'8888 Rue Mohammed VI, Casablanca','Café de la Place','cafedelaplace_cover.jpg','2024-03-15 15:08:54.000000','cafedelaplace@gmail.com','https://www.facebook.com/cafedelaplace','https://maps.google.com/cafedelaplace','https://www.instagram.com/cafedelaplace','+212654567890',2,14),(15,'9999 Boulevard Mohammed V, Rabat','Restaurant de la Cascade','restaurantcascade_cover.jpg','2024-03-15 15:08:54.000000','restaurantcascade@gmail.com','https://www.facebook.com/restaurantcascade','https://maps.google.com/restaurantcascade','https://www.instagram.com/restaurantcascade','+212687654321',3,15),(16,'10101 Avenue Mohammed VI, Marrakech','Glace du Sahara','glacedusahara_cover.jpg','2024-03-15 15:08:54.000000','glacedusahara@gmail.com','https://www.facebook.com/glacedusahara','https://maps.google.com/glacedusahara','https://www.instagram.com/glacedusahara','+212690123456',4,16),(17,'111 Rue Mohammed V, Agadir','La Pâtisserie des Amis','patisseriefriends_cover.jpg','2024-03-15 15:08:54.000000','patisseriefriends@gmail.com','https://www.facebook.com/patisseriefriends','https://maps.google.com/patisseriefriends','https://www.instagram.com/patisseriefriends','+212601234567',1,17),(18,'222 Avenue Hassan II, Tanger','Café du Coin','cafeducoin_cover.jpg','2024-03-15 15:08:54.000000','cafeducoin@gmail.com','https://www.facebook.com/cafeducoin','https://maps.google.com/cafeducoin','https://www.instagram.com/cafeducoin','+212643210987',2,18),(19,'333 Boulevard Mohammed VI, Essaouira','Restaurant de l\'Étoile','restaurantetoile_cover.jpg','2024-03-15 15:08:54.000000','restaurantetoile@gmail.com','https://www.facebook.com/restaurantetoile','https://maps.google.com/restaurantetoile','https://www.instagram.com/restaurantetoile','+212676543210',3,19),(20,'444 Rue Mohammed V, Marrakech','Glace de l\'Hiver','glacedelhiver_cover.jpg','2024-03-15 15:08:54.000000','glacedelhiver@gmail.com','https://www.facebook.com/glacedelhiver','https://maps.google.com/glacedelhiver','https://www.instagram.com/glacedelhiver','+212609876543',4,20);
/*!40000 ALTER TABLE `business` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_category`
--

DROP TABLE IF EXISTS `business_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_category`
--

LOCK TABLES `business_category` WRITE;
/*!40000 ALTER TABLE `business_category` DISABLE KEYS */;
INSERT INTO `business_category` VALUES (1,'Gastronomie'),(2,'Food'),(3,'Groceries'),(4,'Prêt-à-Porter'),(5,'Hôtellerie'),(6,'Divertissement'),(7,'Services'),(8,'Magasins'),(9,'Santé');
/*!40000 ALTER TABLE `business_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_type`
--

DROP TABLE IF EXISTS `business_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK60au3yaep8rv0ai9akdp98fov` (`category_id`),
  CONSTRAINT `FK60au3yaep8rv0ai9akdp98fov` FOREIGN KEY (`category_id`) REFERENCES `business_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_type`
--

LOCK TABLES `business_type` WRITE;
/*!40000 ALTER TABLE `business_type` DISABLE KEYS */;
INSERT INTO `business_type` VALUES (1,'Pâtisserie',1),(2,'Café',1),(3,'Restaurant',1),(4,'Glacier',1);
/*!40000 ALTER TABLE `business_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaigns`
--

DROP TABLE IF EXISTS `campaigns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaigns` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `business_id` int NOT NULL,
  `template_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKolyfguemdr6f2n66vefx1i4c9` (`business_id`),
  KEY `FKcocq7sej5nu3ui0t24sn1r5im` (`template_id`),
  CONSTRAINT `FKcocq7sej5nu3ui0t24sn1r5im` FOREIGN KEY (`template_id`) REFERENCES `templates` (`id`),
  CONSTRAINT `FKolyfguemdr6f2n66vefx1i4c9` FOREIGN KEY (`business_id`) REFERENCES `business` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaigns`
--

LOCK TABLES `campaigns` WRITE;
/*!40000 ALTER TABLE `campaigns` DISABLE KEYS */;
INSERT INTO `campaigns` VALUES (1,1,1),(2,2,2),(3,3,3),(4,4,4),(5,5,1),(6,6,2),(7,7,3),(8,8,4),(9,9,1),(10,10,2),(11,11,3),(12,12,4),(13,13,1),(14,14,2),(15,15,3),(16,16,4),(17,17,1),(18,18,2),(19,19,3),(20,20,4);
/*!40000 ALTER TABLE `campaigns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compagne_language`
--

DROP TABLE IF EXISTS `compagne_language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compagne_language` (
  `compagne_id` bigint NOT NULL,
  `language_id` bigint NOT NULL,
  PRIMARY KEY (`compagne_id`,`language_id`),
  KEY `FK6bn5dkgkonjvs0bslyoqbfukd` (`language_id`),
  CONSTRAINT `FK6bn5dkgkonjvs0bslyoqbfukd` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `FKmgqhp7ey3rwkm6guchvolt10i` FOREIGN KEY (`compagne_id`) REFERENCES `campaigns` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compagne_language`
--

LOCK TABLES `compagne_language` WRITE;
/*!40000 ALTER TABLE `compagne_language` DISABLE KEYS */;
INSERT INTO `compagne_language` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(1,2),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(10,2),(11,2),(12,2),(13,2),(14,2),(15,2),(16,2),(17,2),(18,2),(19,2),(20,2),(1,3),(2,3),(3,3),(4,3),(5,3),(6,3),(7,3),(8,3),(9,3),(10,3),(11,3),(12,3),(13,3),(14,3),(15,3),(16,3),(17,3),(18,3),(19,3),(20,3),(1,4),(2,4),(3,4),(4,4),(5,4),(6,4),(7,4),(8,4),(9,4),(10,4),(11,4),(12,4),(13,4),(14,4),(15,4),(16,4),(17,4),(18,4),(19,4),(20,4);
/*!40000 ALTER TABLE `compagne_language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `age` int NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,25,'mohammed@gmail.com','Male','Mohammed','+212600000001'),(2,30,'fatima@gmail.com','Female','Fatima','+212600000002'),(3,28,'ahmed@gmail.com','Male','Ahmed','+212600000003'),(4,32,'amina@gmail.com','Female','Amina','+212600000004'),(5,27,'youssef@gmail.com','Male','Youssef','+212600000005'),(6,35,'nadia@gmail.com','Female','Nadia','+212600000006'),(7,29,'omar@gmail.com','Male','Omar','+212600000007'),(8,31,'sara@gmail.com','Female','Sara','+212600000008'),(9,26,'ali@gmail.com','Male','Ali','+212600000009'),(10,33,'laila@gmail.com','Female','Laila','+212600000010'),(11,34,'hassan@gmail.com','Male','Hassan','+212600000011'),(12,29,'khadija@gmail.com','Female','Khadija','+212600000012'),(13,27,'yassin@gmail.com','Male','Yassin','+212600000013'),(14,32,'zahra@gmail.com','Female','Zahra','+212600000014'),(15,30,'rachid@gmail.com','Male','Rachid','+212600000015'),(16,28,'samira@gmail.com','Female','Samira','+212600000016'),(17,31,'abdellah@gmail.com','Male','Abdellah','+212600000017'),(18,33,'nawal@gmail.com','Female','Nawal','+212600000018'),(19,26,'mehdi@gmail.com','Male','Mehdi','+212600000019'),(20,35,'fatiha@gmail.com','Female','Fatiha','+212600000020'),(21,29,'mustapha@gmail.com','Male','Mustapha','+212600000021'),(22,31,'latifa@gmail.com','Female','Latifa','+212600000022'),(23,28,'hamza@gmail.com','Male','Hamza','+212600000023'),(24,30,'salma@gmail.com','Female','Salma','+212600000024'),(25,27,'karim@gmail.com','Male','Karim','+212600000025'),(26,33,'leila@gmail.com','Female','Leila','+212600000026'),(27,26,'mohammed2@gmail.com','Male','Mohammed','+212600000027'),(28,32,'khadija2@gmail.com','Female','Khadija','+212600000028'),(29,29,'hicham@gmail.com','Male','Hicham','+212600000029'),(30,34,'nadia2@gmail.com','Female','Nadia','+212600000030'),(31,28,'youssef2@gmail.com','Male','Youssef','+212600000031'),(32,35,'samira2@gmail.com','Female','Samira','+212600000032'),(33,27,'omar2@gmail.com','Male','Omar','+212600000033'),(34,30,'fatima2@gmail.com','Female','Fatima','+212600000034'),(35,31,'ahmed2@gmail.com','Male','Ahmed','+212600000035'),(36,26,'loubna@gmail.com','Female','Loubna','+212600000036'),(37,32,'abderrahim@gmail.com','Male','Abderrahim','+212600000037'),(38,29,'aicha@gmail.com','Female','Aicha','+212600000038'),(39,33,'khalid@gmail.com','Male','Khalid','+212600000039'),(40,28,'hafsa@gmail.com','Female','Hafsa','+212600000040');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `language` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES (1,'Arabic'),(2,'French'),(3,'English'),(4,'Spanish');
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loyalty_programme`
--

DROP TABLE IF EXISTS `loyalty_programme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loyalty_programme` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `age` int NOT NULL,
  `coupon_code` varchar(255) DEFAULT NULL,
  `coupon_value` double NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `mb_price_condition` varchar(255) DEFAULT NULL,
  `mystery_box_prize` varchar(255) DEFAULT NULL,
  `nps_value` int NOT NULL,
  `compagne_id` bigint NOT NULL,
  `loyalty_programme_type_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqkbfsurl89wtwj9cjhuulkqjh` (`compagne_id`),
  KEY `FKjypvbe3db6sgg4nkihwr6akpe` (`loyalty_programme_type_id`),
  CONSTRAINT `FKjypvbe3db6sgg4nkihwr6akpe` FOREIGN KEY (`loyalty_programme_type_id`) REFERENCES `loyalty_programme_type` (`id`),
  CONSTRAINT `FKqkbfsurl89wtwj9cjhuulkqjh` FOREIGN KEY (`compagne_id`) REFERENCES `campaigns` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loyalty_programme`
--

LOCK TABLES `loyalty_programme` WRITE;
/*!40000 ALTER TABLE `loyalty_programme` DISABLE KEYS */;
INSERT INTO `loyalty_programme` VALUES (1,30,'SUMMER25',10,'Male','Purchase over $50','Gift Card',8,1,1),(2,25,'FALL20',15,'Female','Purchase over $40','Discount Voucher',9,2,2),(3,40,'WINTER30',20,'Male','Purchase over $60','Free Item',7,3,3),(4,35,'SPRING15',25,'Female','Purchase over $70','Exclusive Access',8,4,4),(5,28,'SUMMER30',30,'Male','Purchase over $55','Bonus Points',9,5,1),(6,32,'FALL25',35,'Female','Purchase over $45','Special Prize',7,6,2),(7,45,'WINTER35',40,'Male','Purchase over $65','Free Shipping',8,7,3),(8,27,'SPRING20',45,'Female','Purchase over $75','Discount Coupon',9,8,4),(9,33,'SUMMER40',50,'Male','Purchase over $60','Bonus Rewards',7,9,1),(10,38,'FALL30',55,'Female','Purchase over $50','Exclusive Gift',8,10,2);
/*!40000 ALTER TABLE `loyalty_programme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loyalty_programme_type`
--

DROP TABLE IF EXISTS `loyalty_programme_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loyalty_programme_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loyalty_programme_type`
--

LOCK TABLES `loyalty_programme_type` WRITE;
/*!40000 ALTER TABLE `loyalty_programme_type` DISABLE KEYS */;
INSERT INTO `loyalty_programme_type` VALUES (1,'Client Ambassador'),(2,'Mystery Box'),(3,'Coupon Code'),(4,'Raffle');
/*!40000 ALTER TABLE `loyalty_programme_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privilege`
--

DROP TABLE IF EXISTS `privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privilege` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilege`
--

LOCK TABLES `privilege` WRITE;
/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
INSERT INTO `privilege` VALUES (1,'READ_PRIVILEGE'),(2,'WRITE_PRIVILEGE');
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_SUPER_ADMIN'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_privileges`
--

DROP TABLE IF EXISTS `roles_privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_privileges` (
  `role_id` bigint NOT NULL,
  `privilege_id` bigint NOT NULL,
  KEY `FK5yjwxw2gvfyu76j3rgqwo685u` (`privilege_id`),
  KEY `FK9h2vewsqh8luhfq71xokh4who` (`role_id`),
  CONSTRAINT `FK5yjwxw2gvfyu76j3rgqwo685u` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`),
  CONSTRAINT `FK9h2vewsqh8luhfq71xokh4who` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_privileges`
--

LOCK TABLES `roles_privileges` WRITE;
/*!40000 ALTER TABLE `roles_privileges` DISABLE KEYS */;
INSERT INTO `roles_privileges` VALUES (1,1),(1,2),(2,1);
/*!40000 ALTER TABLE `roles_privileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_area`
--

DROP TABLE IF EXISTS `service_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_area` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_private` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `service_category_id` bigint DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6q5xsijyfjki33iy2xg4kh3pr` (`service_category_id`),
  KEY `FK46tqojsyn360oafmdx5bfvhs5` (`user_id`),
  CONSTRAINT `FK46tqojsyn360oafmdx5bfvhs5` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK6q5xsijyfjki33iy2xg4kh3pr` FOREIGN KEY (`service_category_id`) REFERENCES `service_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_area`
--

LOCK TABLES `service_area` WRITE;
/*!40000 ALTER TABLE `service_area` DISABLE KEYS */;
INSERT INTO `service_area` VALUES (1,_binary '\0','Général',1,1),(2,_binary '\0','Agréabilité',1,1),(3,_binary '\0','Personnel',1,1),(4,_binary '\0','Service',2,1),(5,_binary '\0','Boissons',2,1),(6,_binary '\0','Additions',2,1),(7,_binary '\0','Qualité de prix',2,1),(8,_binary '\0','À table',2,1),(9,_binary '\0','Serving',2,1),(10,_binary '\0','Sourire',3,1),(11,_binary '\0','Température',3,1),(12,_binary '\0','Espace',3,1),(13,_binary '\0','Luminosité',3,1),(14,_binary '\0','Taille du restaurant',3,1),(15,_binary '\0','Général',3,1);
/*!40000 ALTER TABLE `service_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_category`
--

DROP TABLE IF EXISTS `service_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_category`
--

LOCK TABLES `service_category` WRITE;
/*!40000 ALTER TABLE `service_category` DISABLE KEYS */;
INSERT INTO `service_category` VALUES (1,'Catégorie représentant les aspects de l\'accueil.','Accueil'),(2,'Catégorie représentant les aspects de rapidité du service.','Rapidité'),(3,'Catégorie représentant les aspects de confort dans le service.','Confort');
/*!40000 ALTER TABLE `service_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `templates`
--

DROP TABLE IF EXISTS `templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `templates` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `css_file_path` varchar(255) DEFAULT NULL,
  `html_file_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `templates`
--

LOCK TABLES `templates` WRITE;
/*!40000 ALTER TABLE `templates` DISABLE KEYS */;
INSERT INTO `templates` VALUES (1,'style1.css','template1.html'),(2,'style2.css','template2.html'),(3,'style3.css','template3.html'),(4,'style4.css','template4.html');
/*!40000 ALTER TABLE `templates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `expired_time_email` datetime(6) DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `is_used_token_email` bit(1) DEFAULT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `last_logout` datetime(6) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token_email` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'soukaina.elamrani@gmail.com',NULL,'Soukaina El Amrani',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:15:11.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'soukaina25','+212680697157','2023-08-11 16:43:58'),(2,'mohammed.tazi@gmail.com',NULL,'Mohammed Tazi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:05:20.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'mohammed26','+212691486452','2023-07-10 16:43:58'),(3,'nadia.berrada@gmail.com',NULL,'Nadia Berrada',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:23:24.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'nadia27','+212615340793','2023-07-30 16:43:58'),(4,'fatima.zouhairi@gmail.com',NULL,'Fatima Zouhairi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:23:20.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'fatima28','+212622446141','2024-02-12 17:43:58'),(5,'youssef.elkadiri@gmail.com',NULL,'Youssef El Kadiri',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:28:49.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'youssef29','+212665200693','2023-08-23 16:43:58'),(6,'sanaa.chakir@gmail.com',NULL,'Sanaa Chakir',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:56:22.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'sanaa30','+212619269625','2023-08-27 16:43:58'),(7,'amina.bensaid@gmail.com',NULL,'Amina Bensaid',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:57:44.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'amina31','+212607460528','2024-02-21 17:43:58'),(8,'mohamed.elbouyahyaoui@gmail.com',NULL,'Mohamed El Bouyahyaoui',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:41:53.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'mohamed32','+212645921389','2023-07-14 16:43:58'),(9,'najwa.sidi@gmail.com',NULL,'Najwa Sidi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:18:32.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'najwa33','+212627368793','2024-01-15 17:43:58'),(10,'younes.azami@gmail.com',NULL,'Younes Azami',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:09:22.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'younes34','+212699079801','2023-05-25 16:43:58'),(11,'nadia.mansouri@gmail.com',NULL,'Nadia Mansouri',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:33:31.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'nadia35','+212613292631','2023-08-29 16:43:58'),(12,'omar.lahjouji@gmail.com',NULL,'Omar Lahjouji',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:01:52.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'omar36','+212669223757','2023-11-24 17:43:58'),(13,'sara.bouamama@gmail.com',NULL,'Sara Bouamama',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:11:04.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'sara37','+212662408924','2023-04-23 16:43:58'),(14,'abdelkader.echcharq@gmail.com',NULL,'Abdelkader Echcharq',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:32:05.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'abdelkader38','+212623533193','2023-08-25 16:43:58'),(15,'selma.zerouali@gmail.com',NULL,'Selma Zerouali',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:49:34.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'selma39','+212698943295','2024-02-06 17:43:58'),(16,'nabil.elhachimi@gmail.com',NULL,'Nabil El Hachimi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:13:51.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'nabil40','+212624116899','2023-05-09 16:43:58'),(17,'khadija.agoumi@gmail.com',NULL,'Khadija Agoumi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:22:54.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'khadija41','+212623754614','2023-04-03 16:43:58'),(18,'said.laaroussi@gmail.com',NULL,'Saïd Laaroussi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:55:18.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'said42','+212646422375','2024-01-03 17:43:58'),(19,'najat.amrani@gmail.com',NULL,'Najat Amrani',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:10:07.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'najat43','+212660848036','2024-01-26 17:43:58'),(20,'brahim.lahlou@gmail.com',NULL,'Brahim Lahlou',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:47:02.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'brahim44','+212664973061','2024-02-13 17:43:58'),(21,'houda.bennani@gmail.com',NULL,'Houda Bennani',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:07:07.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'houda45','+212642321201','2024-03-09 17:43:58'),(22,'omar.zaidi@gmail.com',NULL,'Omar Zaidi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:56:50.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2',NULL,'omar46','+212616686823','2023-05-18 16:43:58'),(23,'fatima.elamrani@gmail.com',NULL,'Fatima Zahra El Amrani',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:05:07.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'fatima47','+212656470517','2024-02-09 17:43:58'),(24,'brahim.benjelloun@gmail.com',NULL,'Brahim Benjelloun',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:17:23.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'brahim48','+212632292120','2023-03-18 17:43:58'),(25,'sara.elouardi@gmail.com',NULL,'Sara El Ouardi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:53:55.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'sara49','+212692049051','2023-07-08 16:43:58'),(26,'ahmed.kabbaj@gmail.com',NULL,'Ahmed Kabbaj',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:19:47.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'ahmed50','+212663368898','2023-09-30 16:43:58'),(27,'zineb.lahmidi@gmail.com',NULL,'Zineb Lahmidi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:39:28.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'zineb51','+212640697343','2023-12-22 17:43:58'),(28,'karim.elfahri@gmail.com',NULL,'Karim El Fahri',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:00:18.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'karim52','+212613380022','2023-06-08 16:43:58'),(29,'hajar.elhajjaji@gmail.com',NULL,'Hajar El Hajjaji',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:45:27.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'hajar53','+212644808087','2024-01-13 17:43:58'),(30,'mohamed.tazi@gmail.com',NULL,'Mohamed Tazi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:28:42.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'mohamed54','+212683900372','2023-09-01 16:43:58'),(31,'fatiha.belhaj@gmail.com',NULL,'Fatiha Belhaj',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:49:25.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'fatiha55','+212685077601','2024-01-12 17:43:58'),(32,'ali.belkadi@gmail.com',NULL,'Ali Belkadi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:23:19.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'ali56','+212673686895','2023-12-14 17:43:58'),(33,'khadija.berrada@gmail.com',NULL,'Khadija Berrada',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:10:42.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'khadija57','+212613201676','2023-06-18 16:43:58'),(34,'oussama.essaadi@gmail.com',NULL,'Oussama Essaadi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:25:49.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'oussama58','+212644947698','2023-04-01 16:43:58'),(35,'naima.daoudi@gmail.com',NULL,'Naïma Daoudi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:19:22.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'naima59','+212685133465','2023-08-23 16:43:58'),(36,'omar.sabri@gmail.com',NULL,'Omar Sabri',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:01:40.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'omar60','+212690824235','2023-04-09 16:43:58'),(37,'sara.elmahfoudi@gmail.com',NULL,'Sara El Mahfoudi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:52:33.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'sara61','+212698720785','2023-03-21 17:43:58'),(38,'oumaima.benalla@gmail.com',NULL,'Oumaima Benalla',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:00:03.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'oumaima62','+212621131222','2024-01-25 17:43:58'),(39,'ayoub.oudghiri@gmail.com',NULL,'Ayoub Oudghiri',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:04:58.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'ayoub63','+212694937615','2023-06-23 16:43:58'),(40,'fatima.zohra.bouslamti@gmail.com',NULL,'Fatima Zohra Bouslamti',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:07:01.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'fatima64','+212684075141','2023-12-20 17:43:58'),(41,'aymane.lachgar@gmail.com',NULL,'Aymane Lachgar',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:02:31.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'ayman65','+212691894426','2023-03-21 17:43:58');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-21  1:00:17
