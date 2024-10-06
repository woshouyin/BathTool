CREATE TABLE `Poi` (
                       `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                       `parent` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `distance` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `pcode` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `adcode` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `pname` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `cityname` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `type` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `typecode` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `adname` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `citycode` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `location` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       `market_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                       `market_center` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;