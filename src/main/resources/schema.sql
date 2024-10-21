
CREATE TABLE IF NOT EXISTS `ciudades` (
  `ciu_cod` int(6) NOT NULL AUTO_INCREMENT,
  `ciu_des` varchar(50) NOT NULL,
  PRIMARY KEY (`ciu_cod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;


CREATE TABLE IF NOT EXISTS `clientes` (
  `cli_cod` int(12) NOT NULL AUTO_INCREMENT,
  `cli_nombres` varchar(80) NOT NULL,
  `cli_documento` varchar(45) NOT NULL,
  `ciu_cod` int(6) NOT NULL,
  `cli_direccion` varchar(200) NOT NULL,
  `cli_telefono` varchar(45) NOT NULL,
  `cli_email` varchar(45) NOT NULL,
  PRIMARY KEY (`cli_cod`),
  KEY `fk_clientes_ciudades_idx` (`ciu_cod`),
  CONSTRAINT `fk_clientes_ciudades` FOREIGN KEY (`ciu_cod`) REFERENCES `ciudades` (`ciu_cod`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

CREATE TABLE IF NOT EXISTS `productos` (
  `prod_cod` varchar(20) NOT NULL,
  `prod_nombre` varchar(80) NOT NULL,
  `prod_descrip` varchar(200) NOT NULL,
  `prod_precioven` decimal(12,2) NOT NULL,
  `prod_stock` decimal(12,3) NOT NULL,
  PRIMARY KEY (`prod_cod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

CREATE TABLE IF NOT EXISTS `pedidos` (
  `ped_cod` int(20) NOT NULL AUTO_INCREMENT,
  `ped_nrocontrol` INT(12) NULL,
  `cli_cod` int(12) NOT NULL,
  `ped_fechahs` timestamp NULL DEFAULT current_timestamp(),
  `ped_estado` enum('P','E','A','C','V') DEFAULT NULL COMMENT 'P=pendiente\nE= en proceso\nA= anulado\nC= Concluido\nV= Vencido',
  PRIMARY KEY (`ped_cod`),
  KEY `fk_pedidos_clientes1_idx` (`cli_cod`),
  CONSTRAINT `fk_pedidos_clientes1` FOREIGN KEY (`cli_cod`) REFERENCES `clientes` (`cli_cod`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

CREATE TABLE IF NOT EXISTS `pedidos_det` (
  `prod_cod` varchar(20) NOT NULL,
  `ped_cod` int(20) NOT NULL,
  `pdet_item` int(6) NOT NULL,
  `pdet_can` decimal(12,3) DEFAULT NULL,
  `pdet_preciouni` decimal(12,2) DEFAULT NULL,
  `pdet_subtotal` DECIMAL(12,2) NULL,
  PRIMARY KEY (`prod_cod`,`ped_cod`,`pdet_item`),
  KEY `fk_productos_has_pedidos_pedidos1_idx` (`ped_cod`),
  KEY `fk_productos_has_pedidos_productos1_idx` (`prod_cod`),
  CONSTRAINT `fk_productos_has_pedidos_pedidos1` FOREIGN KEY (`ped_cod`) REFERENCES `pedidos` (`ped_cod`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_productos_has_pedidos_productos1` FOREIGN KEY (`prod_cod`) REFERENCES `productos` (`prod_cod`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;


