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
INSERT INTO `admin` VALUES (1,'tanafaat.rca.16@gmail.com','2024-03-20 23:38:25.380183','tanafaat',_binary '',_binary '','2024-03-21 00:41:28.558553','2024-03-20 23:35:31.098518','ABDESSAMAD','$2a$10$AWOgId6XTSow.bLimPqJTOJb5IfjbCuo2yWGcpcGA8P5a4Mj4EpcS','WrtxTGfBBRGE-2oM-kOeHQa58W22rQWXexFCakkot9FVwTZ6SlVLcdBgKsf8EsL1LJrieBxfaJce94K3ueLewg','/avatars/tanafaat_ABDESSAMAD_avatar.png',_binary 'xÚ¥X÷7Œ÷\ÖW\ç«h\é°b—Ö¨š-%}\í½WQE\í»F\í#öj³ö‘\Ú;*ö\Ş#‚B\ìo?\Ã÷s¸÷9\çó<?\Üó\Ü­¥¡Hù/Ã¿ddd”\ÊJr:dd·\âÿ—÷\Éÿv\Z-z®\É\Èşi\ÓRÓ“¿¹¹¹º¾œ\Æ\r·\Ì¥ö{G´[‚\Z•œjÿs¬:\×\Éx7kFw\Øf}ÿ½T±´?1‚‚¿!t\Ã~›WM¦¯\à§\Ï.OÛ—+#Û­z\\‡7w,Wù#ôb:¿a{¦pƒ„…ó«³_0\ç:\é–ù\Âu\Â|\Ø\ï\Ï\Çg\È\ÅÒ‰¾ú™ó{£n\r\n»Ä­£³·zù\ä>¯\ê©\Ì?Û¸\ã\Í\ë›k?„¬\Ô8›7…8$\á\êşB\Z\å‡´-–Ï\"Û¿ÀGc\Ö›‡‹^M\ê\à.ûª©\Ìv÷ş	6µß§a6on\ï´Š2#]œ”ŒAû\×Ø£U\Ï&µ¤^÷\ÊÉ´qlşdgvw„x~\èR/û½\Í$o$¢c¹z0÷½\Í\Ú\í”\Ü\çYŒ ·\Úf^]_\Í\ïc\Ê\Æw\×|[´\ëgrw‰\Ğnçº™\ÜÉ\äB\éÁ)Î¿U?k(0}\Ğ?ü·\ÅñÁµ^½ùû\çHD\Ïj½W“\Ú\ÅÕ¹G£JÚ€O³f\ÅdJ\\—\Ã\ĞF[\ã\\¾o‹V\Æ`À\Ì.ú´Ÿ9˜1ø­|\ĞjXğ\'\Zµø«i® p4ö\äü(ª\Ãzb§?e\îŞ 0¸,ŸH\îYm\Ø>ZvoP„t;ş@‡mwŸœ___g\ï·¿!ôz\×\Z	§{°>…}Ló\\A.:txµz0szA<»$\rl´lu®\ÌVL¤^^]x7k\ÔM\çTOe”ŒÅlu\àOq¥c	MªYCAù¢BP¦±vy#‘‰½n]+µµ\ÓÙ¸\ãõ\"L\\ÿzs ò\ãù%)}À¯u¡dao,iÜ¹R³u¸\ß\íÜ¾T¹u¸Œ=^]ŞŸ\Ü>Z\Ù;Á\Î\ì·-şB\Ì5\ÏÁıZ´Nw\Å)ı^‹{\ãÁmŸ0\Û]¥\ã	i¾ñ=.I½ ÀVÃœ\á\à\îÕºqlo\ÍtvH›i\ßZ\Ó2~\ê\×x\ÒOtx\Ítj©|³r4ŸŒŒ¦HYNZ\Ï\'ko\á,\ß`Œ\á\Ş­\çl‘2\\d^/\Æõ€²@V¾§XÍ=<&¬\"R$n’IPIlJU[Ğ‰,g\Ø\î_	\ì‹gƒ\Ü\Ñ3#³x\ãc‡¹m¹\æ\Èo{\ÈøQ\Û7¼­”¦3\Ùÿ\'|´6Ò¬\Ê+™\á\Å?†\ëqJš’j½å©›:0ı\Ò]L-µz\'-ƒr¹Buz\ì#XaP’)„}X‘y“ñ6\É,\ÖR\ßDI‘ı>¿f¢Y‡q6 –,c\ìG?P:« ÁŒÉ¨K4=\Ül ‚5“Í¾§\í8Á­\ØK²=H¯	1\ã)©=l“’\İk€oø	 €ƒ#ßŠ\ÛW6J‹Æ¸¾7•£iQR”\ê§ÇŒ*›O	\ÃCs\Öù°xGl\Z7[\ç‰g~‘‚“\Ã\Z@9ƒÍ˜\Ç*†\äXŸ­ñ’TX\ÏV/BL€4@\àù°\0xhù\Ú?\Ø7ğ-\ìG–PTwœ\n©+F¨(ò¼ /\à(wø\áQ€T’@\Ï\Z›r\Êô>ñl\ê¬’#\n1\ÚbnO\nÒ¨Ğ©T5\Ñ\n˜•C—(\Ø\áO\ì\0¥\×ÉªŸ?ù\á›3Ö˜\Çb\Í]—-2m›0Hº\á-\Ç÷oôõ%l€\á©\â¥+<\Ø\â3ñ\rv\Ùı@,¨\"\Â?\âŠm:’‘¨Q;£„slE£²8*mé½’•Œ[½q9¢‡…wóÿdñ?\Ú>\ÉA?ŠK€dS\ç÷œ?˜l\âQ\ÔlK\ÉÕ•k¼\ß3œi‚+=s\çnT\æ\Ù\àÊ‰T±yX\êÊ \é-‘Ä³47?‡¼]\êµ+\é\í#®©‡S›\"\Ğ¾_\ã\ÎØµeü+ƒ¦zû=\Ó[U{\ë#®\ëa¸\Ãv´\ã, !–ı.97¬-\ÂNü\á;\î‘J7\\€½Â›¡ŸŠª¾ÿ\n\\X\ãV8\çFLô\ç]\èPe($Ã¿>ó\ÓD\Õ\æ\Í\ä\ëPe~h;™Ã¤‡ƒ$³ùRİ§[›—Ãœ\Ù5·p\æJ\ãV\ÎÁGğ¢\ÊûğV\ÖQòW÷Ğ´™z\İ\Ê\Ô\èÌ·¶m;¨ş¾\Ë;¶Ÿ\ê|\nº\'\ä’ó¼\Æh®e	\â\Ã\ÔAP\ÈN¡OŒC\r«ªß™F`ßª\át\Íx\Ş\æ¨\åşnjj?\Ã\'\éŠ\Òö\ÉO›\äkM†Oä¡¤8}\æ\rt\0niG #<¾ºc¤·O\ë\é.PJ\Ã)Dg\Ó>\r$\ê\"T\ê\Ñ‡Û¢Ôıš0\Ì\'huÏ’ƒ	Rò–ı O\æÍµ13<½ ƒ—\àx\ërã¨‰ŸZ?\î‡q\0‰oğ5f)60¹=çŠ» \n™k\ã\r”8\Ú\ÂGg*\"¹ôã¤øô\Îô=\Ñ\çøÏ‡ ¡hº\Æÿ`\á5\æc¿u$\İEÁf\È#üSjFts‹Z*\Ü7Zx\é}\í?>$\ïXT(]ˆ¾¶\ä™‘Iwdº\ß\ÌZ;U\Z{ót<a¦FD\0ds\ã\ŞD\Zt_ zšo|²4\ÄüZú\æ\ĞÁ²M²aY¸3S\Ì||«G\ÔF4 Ú£÷¶$Áym·p§$\á ªßŒóü5=”¶\ÜY1 iL›…j,jA\çl(\Ö$F\êb\ã¢\Ò7˜Z\ä¤\æ3»¶ˆ,¯O´©ı3\Î<F\í\ÓJ\ËCºn\ä:_x‰¾¡½xŠ	Y–Á&ŒÁŒ\Ã\åa\Êßˆ\Õ\äŠ\Ó!Y\Òs½›O•LWæ›©RN€0¤P‘ø¼\ïi\ÌùK…JW\ÆWƒ‘µ‹vm_XòE(\ÅnJù´öÿ˜Kt\ífŸ?³6¤A›÷\íô@V\ày¨‡%´ÿj[\à\ÜeU+™±¢\Zõ­EÀw”\Û\İ\â~‚\'o!e»Işz‚z˜E¯£\Ö_Ocj]™…mŠù†oÍ”8*rº”`¸¼wÔ¶76\Ü\í*÷z@™6)LOWş»tp\Ì7\0º\"\Ê>ˆ\Ø\ÛñO%ùz,\è:\Í\Ü]D\Ã*d\ì?ştK\É\Ú\êc¥´o£÷½€\È>€\Æt—\çK`‚¦x\âh2%÷Vij¿jC`|\Û.÷ÀûY,\r0ÀX©¬„pô.©3‹-^¶\äûwšk\Ñ)\ß|m>òhš{dƒ®­£_´¢t\ã6kwL\Â”8^¥!X—\rˆÅšòhhr,0¡n”b\é[¬’E¡—wu¿\îò¹\ç¸\ê\\!tÉ³\Ë\ÜXƒ\î==c=z\ÚSi–²É®€:Ÿ+	\æÂ­K>{-:P,\Öoz\à¿\Ì\é&Rt—\İ}a\ï„.º:1’L\ë\èM\Ü:y\Ã%w½¹P\æY\Ü_)|Œ6n¯ŠÄ¸º\Û÷Eúug9z­0\ËTP¯\Ğed\'|¶@=\íTA¿\ÈY¾>­\rD¿\\QS\ìOU~ñ…;\ç\'õ³E\ZRô—±‚Áa\Ş˜\Ñ5\ã…\Ès*¿…ñ½\è\Âı7\Ûeıd¡9Q4= K)×\ÙÁ°?\Ø\ÕU6..6T#¢/šÁ$\ÒÙ¥o¶{Q\ÈH±~†\Ş5N‰0´Œh‘s~\á\ÔÈª7\è19õù\ç£œigF eA’uPv£EP(|{\Ú\Ü\å­×¸`\ïüM\n…»„\ÛÁ­E‘<)P\ì\ï\å_¾\ë´Dôp\Ö]z‡ö\Ş[[uce2Ì£J+‹}^\ÔP*\ß:´\Ş—…I\Äwùıûd4ˆ\ÎzI\Ï*-”L:T¾\Ìwg\İ%ø}»’\Ş\Ëwz¾n¼º\rz7öò°t.jV<\á=EFx{¦\ŞÜŸ\×G`;OWF]\ŞvÿfG0”\Øv=\Ú@¡\ãú2Pwk1õª4\\ 	´­\ÊYnZ\Ô\äùXm•¾U¾ñ4™\\Î¦¿§N¡\ÜÁš3I!\ãnô$€j³D ö—\ÆO\èÑ‡;nú:	ù¹«I¯	\ÉsM5õ6#\ßIéµ“¬\Ğ>?\é¨Ë˜\äÁ\\…’ÿBîŠªq¨Wq\äõ\Ê\ßQ{\ÂÏ¢ùDDb‡tö\×r£ø;–]qw|Á\ï$Íš4\Ìé”Ÿ	“$ıŒpwJ¤\ç\Í\æD%İ¾\ZMº\Æ\\\ÃŞ‰\ç§\îFšôi\å%¦¨w.²Ò¨:?||!£–X_şü]‚\ÎÀ·NLK¥\n@[L™1ğ¹&:\×y\Ûò\àm‘¡à·tˆRD®µ\ÛU\n…j\0¨\ZD¶EA#C9\ã\ÂWq³OrÕ¥¤*³0Àvš­Æô­CDfÔ¾+\éö-Ø™„|ºE\âšÜª\Ît¨-\Ç\Øx/Šnx·\ßş3apñ8tšÆ¼Ã€\ÒOfû\Ïyy7‘v\âU\ÜşE	 *%¤ˆ\ÙÙ½\Ô\àö·-\äo\çvÕ•Ÿ\Ô\Ì%Po\Ò]Km˜–uó\Ş¸«ñLş\ÙcòÂ¸\Ò\Ï?„•Rº‰¨ú¥Ö¾b:z·k\í÷C…Z‹€\ì_“º‰Aš±¶\r\Ä\Z™d\Ùp$mE\Êz©AüË°¥\Ö\ËuK=\ØJe\Ú:¨9:Ó\ïÒ¬l•g+s¡\ìy¸”\Ó\Z˜J\Üv\Ö9z\éğ‘¬f-ûÂ€ü¬G\'ûoh{ÀL,•¹V\ÆÀùüõSs\×o¤ª\Æ\ÖI\Ø !`\0½4lösr\Äk\"È¿ux~^\Z4\Ş \Ênz³Xºd)H¾\Z~ÿ½÷Ÿùo]3\åû´*E\Û\ÏÁô|¬=V\Şy\"\núŸ\ë|\à\Ìò\çú”J>›G\è\Ëñ..N.¿G¾\î†­(ı\äà·ªzµUMqm\Ù\0¡|\É{\Ä Y“\Ì[²¯:\Şı±¤Zˆ\"ü\Ê\á¢ÿa?\ÜXKOŠf\ÏCğ\Ã(-|š\ãy@\æ´j\Ö\Å70xÀdpDJjŠ¥ƒ\è\êDŸ¾Ÿ\ŞlWf±˜´¤¬¹(„	–[š€	}S\Ö2iü`-Sh\í}pƒF5¤\Û÷’ªY>dö´ÿ\ë’N\È\ìxŠ-ñş\'ZO\n\Ø)oP+Ë•\Âôı]\ßñüˆZ\Òó¢¸\é\Ñ%2¦1«Š­GŒ«z;¯ZUê„²Ğ«¯rƒa\\-‰ELf\é\ì\à’’‰†G?˜\äK]q\ÌhJ\ÉšC¯4×©\ì©~\Ùtw8ˆ6fŒ5_ö@ã±[\'\ì4”ñş\ä(¤ó‚d™\Å˜\0z3u­\Ôgº]`1\ßV{\Çğ>3ª64?ø5\â÷W¢a\È\å~\rÄ¾¼l\ß\n)\èƒ;N1‡`¹\ïšMcÜ£’7;±“&³ ¼¥\Ï\Öòø½\ÌR\'aŒ±ø4‡m\Ğ,‹?ªšy\0ûJ^\í\Â5¯|q\Şn«U¾¾O’tQ¦\êxŸ[\Ñ_†Jd0â•ºoŠ,’»¡\íG\ä\0ò9c&\ç¦\Üzó1²\íö$­mAv‹\n-Upj§Š&=òğ¾c&M·\Ê\ã\Ã6V\ÆüöT\ã\àcö\İfö\Ğ¬»½ğ+,\á†ıURŸ¹G‚n` (ˆ\í¶\Õ-™}{lŠ2_\Ø‹@dU4?\Ã>Kúƒ˜[ø5…Ì©N·Q\ê\Ã\'.ısiÂº“šogR2e‘\ÂO¶Ş¼ª\Òa)\á\æ“J×½P]\Z\ÎLMp•j´kpxPRş\Ûø\Ä\Í#Ù¾\ëqÄœë“»/Qg\Ş?\ä„\ì}ò¼‚	›½,\ÈW?7.ù\0{>\ÈÃ€\Ü|ñ½¢º\ìY\â\ÕQŒ\Ñ\Ş\ìK!5ä…‹\Ä\é™L]°£6C\Î\Û&Z\ÇR\æZŠ¢\Şeó}ø\Ü³XŠ§>›M\î\ï&Y4¤İ¼\ã5\ß7à©¸]÷\Ğ\âñ\Ö;†1\Ñ\ïŸ\ë¡U\áZù\âg™’SÍ\Ì»\Êù\ZRj³«-\Ãs®[\Å\å\ÑnŒÂ•$¨F®\Ói³z\á)\'G‹\Ó\ÃÍ›¼×¬\'áª¨\r9y9§µšTÙ’$[LJ@Nş\æ\â]\\,U\Ø\à_.[\Í\ãI&\ÙJúŠ‹Ø›ö¬¿N\éöÇ–p—\ß\ãRÁ\Zô\Ó \ßı\Û	=s%–\Æ„@aGı\"ûsØ¬²®v¶Qö?™T•ğJ\Û\Ú}Å†N\çjJ­iö`\è^öüE\ëÌ†°©D¹½d\ËIK„q¯Iòı?F¿\Ç\ëÒœ!.\'B…Pz%Mn— \ï>õ‹FÔgöğ¬òö˜@­µ{¦öò\äŒ\ZIs\Éğ4K\Ú0¶I\ÃCGaùº.ı°¢\à›×·oj’½ü\Ç~nmu\Õ\Óm\ÌòI49F8«z¦Q*h\ê\æ˜=ÂV?`!\Z(j/ó ‹_Á\ZX:Õ™y\áñXH‹h\ëñ-h•IZ²\Îz¼õ­÷\æ\r°#dy$©2_e ş\ê|\Ü\ï$nş„‘\íøÙ¼§®*c‘Á]°W¾†M–\ÜùZ\Ş(¸>\\¬.\â\æz+ª÷q\æ_š»6\Şv\'­œLô-œ`€^£\ä_À<H\è(,\çC\Ôõ\ÃW?ov7^ğX‡\É\×.¹{\ín\Ù>¨`\êÕ¯c7>ü¢n«Xuˆ«\Ê4Ñ¨÷\ÅkKWL¥½Y4}——qYTò\â\è\è\Ó~EN¹\Ê\èl\r À\ã\ÅÏ#iM\îgzuz^‰E¶Ä©Œû¹\åf\á\Ë.Û¼¶L:_\î9\ÈZ\ç\îH\İ0ù¬Š•~·13;}–š{ıô\ã\Ê%Œ|+®r\0h%VkGD3\"(ªZR!Ø“\Õı8O”\é\Îf`\èA#/]\ä…tDÕªƒß‚wYª\ï~\ßI7Áÿ\Z,«\ÄFD-F\ï¢A\Øş—‡¤ÓQ\Ä\"›³Q\Æ\çuô4¸\ëæ‘–©w\í\Şl¼qzB¿^÷x¨=\Ç10r~ªMEô\Â\æ\é\Úmı£\ëa$4$ „ ®ÉœHÅ—Š+ø]0A=\"˜‡ó{a‘\Û÷Ò¢ø/7\Ö\Øª×Š,\ØbßºI‰#\É\06\ÏNK‘‡Ï°uq\×\Ã{¨Á¤øõUŞ™­V¡\Z\Û;Á1¼	öQ¦\'@\0/¦\Z9­„¬Àñ(Œ´˜´\çN¹£‚S<¤¦\íb¥Ù¶\Ùáƒºr7o\ã6©o(¡ój\Å0Qc\êğ¿¿\ài˜G1ñRSB£ûU?…‹_\n\Ş	Ë“Ç´O>v~+I=MÄRş^şòó\Í	üI¼•½ğµlp^‚9¼\ŞPbY|¨\Ï#šÒ‚a\ÓL\Ùû\Ë<4T\ã‹#?€ù<4‡ó\è¯)ÊŠ¦üª\å\Òógr»*c\ÇöÉ†8y\åjş‰\rW\Õ\\ó\ÙF\åg×ƒ¬\' §z=NşxH\àüsŒ`‚g™\Ç%\ï\à±/\Ó8¿\ç–a.?™\å7¦\ì µ| £˜<»ø/Oº/¯C\\[k:I‘\Û•\Ç@m¹\×ú\àÄ™\æ\ÚP\è\æûR\npfyòºzb[\ä³ü‹Å†©<»òc–w\ÏD¸\r\Û[\æŠ®K1+Ä¸\ã\'¤¥¹\"Vœüÿó\Ò3¾¹÷œXÀ¢\ëršğ¿ZY^C®B\æs\Èÿgr\î'),(2,'ilias.rouchdi21@gmail.com',NULL,'Rochdi',_binary '\0',_binary '\0','2024-03-15 15:22:37.000000','2024-03-15 21:23:59.000000','ilias','$2a$10$re49u6BlEFqualsU0W62neH0pFzHHRdZG2irwOGNhu0UDMlt.x33u',NULL,NULL,NULL),(3,'mohamed.elamrani@gmail.com',NULL,'Mohamed',_binary '',_binary '\0','2024-03-17 18:52:12.004129','2024-03-17 17:13:01.052687','El Amrani','$2a$10$S/pmRCsasuLe8kISg8TbFeY7UtxzWwf648qeu25jYJ3ojFk6fo1GK',NULL,NULL,NULL),(4,'ahmed.besbrahim@gmail.com',NULL,'ahmed',_binary '',_binary '\0','2024-03-19 01:30:44.795603','2024-03-18 12:16:01.063237','BENBRAHIM','$2a$10$RxcT1V2A19qz9xWHj5K/9eD.8Pe7a.oive90oA3bQd1K9JZBpBdrq',NULL,NULL,NULL),(5,'fatima.elkhattabi@gmail.com',NULL,'Fatima',_binary '\0',_binary '\0','2024-03-15 15:22:37.000000','2024-03-15 21:29:45.000000','El Khattabi','$2a$10$VbTuJ/bZzLcJBy.G53ciVu5ea3M8cMDbyVW1/LOI8tJsNIUKNHSfO',NULL,NULL,NULL);
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
INSERT INTO `business` VALUES (1,'123 Rue Mohammed V, Casablanca','La PÃ¢tisserie du Maroc','patisseriedumaroc_cover.jpg','2024-03-15 15:08:54.000000','patisseriemaroc@gmail.com','https://www.facebook.com/patisseriedumaroc','https://maps.google.com/patisseriedumaroc','https://www.instagram.com/patisseriedumaroc','+212612345678',1,1),(2,'456 Avenue Hassan II, Marrakech','Le CafÃ© de Marrakech','cafemarrakech_cover.jpg','2024-03-15 15:08:54.000000','cafemarrakech@gmail.com','https://www.facebook.com/cafemarrakech','https://maps.google.com/cafemarrakech','https://www.instagram.com/cafemarrakech','+212645678901',2,2),(3,'789 Rue Mohammed VI, Rabat','Le Restaurant du Coin','restaurantducoin_cover.jpg','2024-03-15 15:08:54.000000','restaurantducoin@gmail.com','https://www.facebook.com/restaurantducoin','https://maps.google.com/restaurantducoin','https://www.instagram.com/restaurantducoin','+212678901234',3,3),(4,'1010 Boulevard Mohamed, FÃ¨s','Glace du Soleil','glacedusoleil_cover.jpg','2024-03-15 15:08:54.000000','glacedusoleil@gmail.com','https://www.facebook.com/glacedusoleil','https://maps.google.com/glacedusoleil','https://www.instagram.com/glacedusoleil','+212609876543',4,4),(5,'555 Avenue Hassan II, Marrakech','La PÃ¢tisserie des Roses','patisserieroses_cover.jpg','2024-03-15 15:08:54.000000','patisserieroses@gmail.com','https://www.facebook.com/patisserieroses','https://maps.google.com/patisserieroses','https://www.instagram.com/patisserieroses','+212621234567',1,5),(6,'777 Rue Mohammed V, Casablanca','CafÃ© de la MÃ©dina','cafedelamedina_cover.jpg','2024-03-15 15:08:54.000000','cafedelamedina@gmail.com','https://www.facebook.com/cafedelamedina','https://maps.google.com/cafedelamedina','https://www.instagram.com/cafedelamedina','+212654321098',2,6),(7,'888 Avenue Mohammed VI, Tanger','Restaurant de la Tour','restaurantlatour_cover.jpg','2024-03-15 15:08:54.000000','restaurantlatour@gmail.com','https://www.facebook.com/restaurantlatour','https://maps.google.com/restaurantlatour','https://www.instagram.com/restaurantlatour','+212687654321',3,7),(8,'1212 Boulevard Hassan II, Agadir','Glace de la Lune','glacedelalune_cover.jpg','2024-03-15 15:08:54.000000','glacedelalune@gmail.com','https://www.facebook.com/glacedelalune','https://maps.google.com/glacedelalune','https://www.instagram.com/glacedelalune','+212690123456',4,8),(9,'333 Avenue Mohammed V, Rabat','La PÃ¢tisserie Orientale','patisserieorientale_cover.jpg','2024-03-15 15:08:54.000000','patisserieorientale@gmail.com','https://www.facebook.com/patisserieorientale','https://maps.google.com/patisserieorientale','https://www.instagram.com/patisserieorientale','+212601234567',1,9),(10,'444 Rue Hassan II, Tanger','CafÃ© du Soir','cafedusoir_cover.jpg','2024-03-15 15:08:54.000000','cafedusoir@gmail.com','https://www.facebook.com/cafedusoir','https://maps.google.com/cafedusoir','https://www.instagram.com/cafedusoir','+212643210987',2,10),(11,'999 Boulevard Mohammed VI, Essaouira','Restaurant de la Plage','restaurantplage_cover.jpg','2024-03-15 15:08:54.000000','restaurantplage@gmail.com','https://www.facebook.com/restaurantplage','https://maps.google.com/restaurantplage','https://www.instagram.com/restaurantplage','+212676543210',3,11),(12,'1313 Rue Mohammed V, Marrakech','Glace de l\'Oasis','glacedeloasis_cover.jpg','2024-03-15 15:08:54.000000','glacedeloasis@gmail.com','https://www.facebook.com/glacedeloasis','https://maps.google.com/glacedeloasis','https://www.instagram.com/glacedeloasis','+212609876543',4,12),(13,'7777 Avenue Hassan II, FÃ¨s','La PÃ¢tisserie du Jardin','patisseriejardin_cover.jpg','2024-03-15 15:08:54.000000','patisseriejardin@gmail.com','https://www.facebook.com/patisseriejardin','https://maps.google.com/patisseriejardin','https://www.instagram.com/patisseriejardin','+212621345678',1,13),(14,'8888 Rue Mohammed VI, Casablanca','CafÃ© de la Place','cafedelaplace_cover.jpg','2024-03-15 15:08:54.000000','cafedelaplace@gmail.com','https://www.facebook.com/cafedelaplace','https://maps.google.com/cafedelaplace','https://www.instagram.com/cafedelaplace','+212654567890',2,14),(15,'9999 Boulevard Mohammed V, Rabat','Restaurant de la Cascade','restaurantcascade_cover.jpg','2024-03-15 15:08:54.000000','restaurantcascade@gmail.com','https://www.facebook.com/restaurantcascade','https://maps.google.com/restaurantcascade','https://www.instagram.com/restaurantcascade','+212687654321',3,15),(16,'10101 Avenue Mohammed VI, Marrakech','Glace du Sahara','glacedusahara_cover.jpg','2024-03-15 15:08:54.000000','glacedusahara@gmail.com','https://www.facebook.com/glacedusahara','https://maps.google.com/glacedusahara','https://www.instagram.com/glacedusahara','+212690123456',4,16),(17,'111 Rue Mohammed V, Agadir','La PÃ¢tisserie des Amis','patisseriefriends_cover.jpg','2024-03-15 15:08:54.000000','patisseriefriends@gmail.com','https://www.facebook.com/patisseriefriends','https://maps.google.com/patisseriefriends','https://www.instagram.com/patisseriefriends','+212601234567',1,17),(18,'222 Avenue Hassan II, Tanger','CafÃ© du Coin','cafeducoin_cover.jpg','2024-03-15 15:08:54.000000','cafeducoin@gmail.com','https://www.facebook.com/cafeducoin','https://maps.google.com/cafeducoin','https://www.instagram.com/cafeducoin','+212643210987',2,18),(19,'333 Boulevard Mohammed VI, Essaouira','Restaurant de l\'Ã‰toile','restaurantetoile_cover.jpg','2024-03-15 15:08:54.000000','restaurantetoile@gmail.com','https://www.facebook.com/restaurantetoile','https://maps.google.com/restaurantetoile','https://www.instagram.com/restaurantetoile','+212676543210',3,19),(20,'444 Rue Mohammed V, Marrakech','Glace de l\'Hiver','glacedelhiver_cover.jpg','2024-03-15 15:08:54.000000','glacedelhiver@gmail.com','https://www.facebook.com/glacedelhiver','https://maps.google.com/glacedelhiver','https://www.instagram.com/glacedelhiver','+212609876543',4,20);
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
INSERT INTO `business_category` VALUES (1,'Gastronomie'),(2,'Food'),(3,'Groceries'),(4,'PrÃªt-Ã -Porter'),(5,'HÃ´tellerie'),(6,'Divertissement'),(7,'Services'),(8,'Magasins'),(9,'SantÃ©');
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
INSERT INTO `business_type` VALUES (1,'PÃ¢tisserie',1),(2,'CafÃ©',1),(3,'Restaurant',1),(4,'Glacier',1);
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
INSERT INTO `service_area` VALUES (1,_binary '\0','GÃ©nÃ©ral',1,1),(2,_binary '\0','AgrÃ©abilitÃ©',1,1),(3,_binary '\0','Personnel',1,1),(4,_binary '\0','Service',2,1),(5,_binary '\0','Boissons',2,1),(6,_binary '\0','Additions',2,1),(7,_binary '\0','QualitÃ© de prix',2,1),(8,_binary '\0','Ã€ table',2,1),(9,_binary '\0','Serving',2,1),(10,_binary '\0','Sourire',3,1),(11,_binary '\0','TempÃ©rature',3,1),(12,_binary '\0','Espace',3,1),(13,_binary '\0','LuminositÃ©',3,1),(14,_binary '\0','Taille du restaurant',3,1),(15,_binary '\0','GÃ©nÃ©ral',3,1);
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
INSERT INTO `service_category` VALUES (1,'CatÃ©gorie reprÃ©sentant les aspects de l\'accueil.','Accueil'),(2,'CatÃ©gorie reprÃ©sentant les aspects de rapiditÃ© du service.','RapiditÃ©'),(3,'CatÃ©gorie reprÃ©sentant les aspects de confort dans le service.','Confort');
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
INSERT INTO `user` VALUES (1,'soukaina.elamrani@gmail.com',NULL,'Soukaina El Amrani',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:15:11.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'soukaina25','+212680697157','2023-08-11 16:43:58'),(2,'mohammed.tazi@gmail.com',NULL,'Mohammed Tazi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:05:20.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'mohammed26','+212691486452','2023-07-10 16:43:58'),(3,'nadia.berrada@gmail.com',NULL,'Nadia Berrada',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:23:24.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'nadia27','+212615340793','2023-07-30 16:43:58'),(4,'fatima.zouhairi@gmail.com',NULL,'Fatima Zouhairi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:23:20.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'fatima28','+212622446141','2024-02-12 17:43:58'),(5,'youssef.elkadiri@gmail.com',NULL,'Youssef El Kadiri',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:28:49.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'youssef29','+212665200693','2023-08-23 16:43:58'),(6,'sanaa.chakir@gmail.com',NULL,'Sanaa Chakir',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:56:22.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'sanaa30','+212619269625','2023-08-27 16:43:58'),(7,'amina.bensaid@gmail.com',NULL,'Amina Bensaid',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:57:44.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'amina31','+212607460528','2024-02-21 17:43:58'),(8,'mohamed.elbouyahyaoui@gmail.com',NULL,'Mohamed El Bouyahyaoui',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:41:53.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'mohamed32','+212645921389','2023-07-14 16:43:58'),(9,'najwa.sidi@gmail.com',NULL,'Najwa Sidi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:18:32.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'najwa33','+212627368793','2024-01-15 17:43:58'),(10,'younes.azami@gmail.com',NULL,'Younes Azami',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:09:22.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'younes34','+212699079801','2023-05-25 16:43:58'),(11,'nadia.mansouri@gmail.com',NULL,'Nadia Mansouri',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:33:31.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'nadia35','+212613292631','2023-08-29 16:43:58'),(12,'omar.lahjouji@gmail.com',NULL,'Omar Lahjouji',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:01:52.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'omar36','+212669223757','2023-11-24 17:43:58'),(13,'sara.bouamama@gmail.com',NULL,'Sara Bouamama',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:11:04.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'sara37','+212662408924','2023-04-23 16:43:58'),(14,'abdelkader.echcharq@gmail.com',NULL,'Abdelkader Echcharq',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:32:05.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'abdelkader38','+212623533193','2023-08-25 16:43:58'),(15,'selma.zerouali@gmail.com',NULL,'Selma Zerouali',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:49:34.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'selma39','+212698943295','2024-02-06 17:43:58'),(16,'nabil.elhachimi@gmail.com',NULL,'Nabil El Hachimi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:13:51.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'nabil40','+212624116899','2023-05-09 16:43:58'),(17,'khadija.agoumi@gmail.com',NULL,'Khadija Agoumi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:22:54.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'khadija41','+212623754614','2023-04-03 16:43:58'),(18,'said.laaroussi@gmail.com',NULL,'SaÃ¯d Laaroussi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:55:18.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'said42','+212646422375','2024-01-03 17:43:58'),(19,'najat.amrani@gmail.com',NULL,'Najat Amrani',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:10:07.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'najat43','+212660848036','2024-01-26 17:43:58'),(20,'brahim.lahlou@gmail.com',NULL,'Brahim Lahlou',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:47:02.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'brahim44','+212664973061','2024-02-13 17:43:58'),(21,'houda.bennani@gmail.com',NULL,'Houda Bennani',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:07:07.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'houda45','+212642321201','2024-03-09 17:43:58'),(22,'omar.zaidi@gmail.com',NULL,'Omar Zaidi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:56:50.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2',NULL,'omar46','+212616686823','2023-05-18 16:43:58'),(23,'fatima.elamrani@gmail.com',NULL,'Fatima Zahra El Amrani',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:05:07.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'fatima47','+212656470517','2024-02-09 17:43:58'),(24,'brahim.benjelloun@gmail.com',NULL,'Brahim Benjelloun',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:17:23.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'brahim48','+212632292120','2023-03-18 17:43:58'),(25,'sara.elouardi@gmail.com',NULL,'Sara El Ouardi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:53:55.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'sara49','+212692049051','2023-07-08 16:43:58'),(26,'ahmed.kabbaj@gmail.com',NULL,'Ahmed Kabbaj',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:19:47.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'ahmed50','+212663368898','2023-09-30 16:43:58'),(27,'zineb.lahmidi@gmail.com',NULL,'Zineb Lahmidi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:39:28.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'zineb51','+212640697343','2023-12-22 17:43:58'),(28,'karim.elfahri@gmail.com',NULL,'Karim El Fahri',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:00:18.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'karim52','+212613380022','2023-06-08 16:43:58'),(29,'hajar.elhajjaji@gmail.com',NULL,'Hajar El Hajjaji',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:45:27.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'hajar53','+212644808087','2024-01-13 17:43:58'),(30,'mohamed.tazi@gmail.com',NULL,'Mohamed Tazi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:28:42.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'mohamed54','+212683900372','2023-09-01 16:43:58'),(31,'fatiha.belhaj@gmail.com',NULL,'Fatiha Belhaj',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:49:25.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'fatiha55','+212685077601','2024-01-12 17:43:58'),(32,'ali.belkadi@gmail.com',NULL,'Ali Belkadi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:23:19.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'ali56','+212673686895','2023-12-14 17:43:58'),(33,'khadija.berrada@gmail.com',NULL,'Khadija Berrada',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:10:42.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'khadija57','+212613201676','2023-06-18 16:43:58'),(34,'oussama.essaadi@gmail.com',NULL,'Oussama Essaadi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:25:49.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'oussama58','+212644947698','2023-04-01 16:43:58'),(35,'naima.daoudi@gmail.com',NULL,'NaÃ¯ma Daoudi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:19:22.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'naima59','+212685133465','2023-08-23 16:43:58'),(36,'omar.sabri@gmail.com',NULL,'Omar Sabri',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:01:40.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'omar60','+212690824235','2023-04-09 16:43:58'),(37,'sara.elmahfoudi@gmail.com',NULL,'Sara El Mahfoudi',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 21:52:33.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'sara61','+212698720785','2023-03-21 17:43:58'),(38,'oumaima.benalla@gmail.com',NULL,'Oumaima Benalla',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:00:03.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'oumaima62','+212621131222','2024-01-25 17:43:58'),(39,'ayoub.oudghiri@gmail.com',NULL,'Ayoub Oudghiri',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 23:04:58.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'ayoub63','+212694937615','2023-06-23 16:43:58'),(40,'fatima.zohra.bouslamti@gmail.com',NULL,'Fatima Zohra Bouslamti',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:07:01.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'fatima64','+212684075141','2023-12-20 17:43:58'),(41,'aymane.lachgar@gmail.com',NULL,'Aymane Lachgar',_binary '',_binary '\0','2024-03-15 15:16:31.000000','2024-03-15 22:02:31.000000','$2a$10$Z.yYLwjb7W7m8CkU4/L9Tut5gU1Iu7eyBVQ6K6yZjBZ2XmLvpqZPG',NULL,'ayman65','+212691894426','2023-03-21 17:43:58');
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
