# Proyecto: [Backend - Apirest]

## Descripción

[Prueba técnica para la Empresa DATAPAR]

## Requisitos

Entorno de desarrollo
- [JDK]: java 17
- [Framework]: spring boot 3.3.4
- [IDE]: IntelliJ IDEA 2023.2.5 (Community Edition)
- [DB]: MySQL database

## Instalación

Sigue estos pasos para configurar y ejecutar el proyecto localmente:


### 1. Configurar application.properties
- Configurar application.properties para los diferentes perfiles
```properties
# application.properties
spring.application.name=datapart
server.port=8080
spring.datasource.username = user
spring.datasource.password = password
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicTypeRegistry=TRACE

```
- Configurar application-dev.properties para desarrollador, por unica vez para crear instacias de base de datos e insertar datos de prueba
```properties
spring.datasource.url = url:puerto/db?createDatabaseIfNotExist=true
spring.datasource.initialization-mode=always
spring.sql.init.mode = always
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicTypeRegistry=TRACE
```
- Configurar application-prod.properties para pruebas
```properties
spring.application.name=datapart-prod
# MariaDB configuration.
spring.datasource.url = url:puerto/db
```


### 2. Ejecución del proyecto
- Modo desarrollador perfil dev (por unica vez para crear instacias de base de datos e insertar datos de prueba)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
- Modo desarrollador de prueba (una vez creado toda las instacias de la base de datos proceder a ejecutar el proyecto con el perfil de pruebas)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Script de creación de la base de datos y tablas
3. Creación Manual DDL y DML de la base datos
```sql

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
  PRIMARY KEY (`prod_cod`,`ped_cod`,`pdet_item`),
  KEY `fk_productos_has_pedidos_pedidos1_idx` (`ped_cod`),
  KEY `fk_productos_has_pedidos_productos1_idx` (`prod_cod`),
  CONSTRAINT `fk_productos_has_pedidos_pedidos1` FOREIGN KEY (`ped_cod`) REFERENCES `pedidos` (`ped_cod`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_productos_has_pedidos_productos1` FOREIGN KEY (`prod_cod`) REFERENCES `productos` (`prod_cod`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
```
4. Inserción de datos de prueba 
```sql
INSERT INTO `ciudades` (`ciu_des`) VALUES
('Asunción'),
('Ciudad del Este'),
('San Lorenzo'),
('Luque'),
('Fernando de la Mora'),
('Encarnación'),
('Capiatá'),
('Pedro Juan Caballero'),
('Concepción'),
('Itauguá'),
('Caaguazú'),
('San Pedro del Ycuamandiyú'),
('Villarrica'),
('Curuguaty'),
('Paraguarí'),
('San Juan Bautista'),
('Pilar'),
('Filadelfia'),
('Ayolas'),
('Nueva Italia'),
('Areguá'),
('San Bernardino'),
('Mbuyapey'),
('Alto Paraná'),
('Bella Vista Norte'),
('Ybycuí'),
('Eusebio Ayala'),
('Caazapá');


INSERT INTO `clientes` (`cli_nombres`, `cli_documento`, `ciu_cod`, `cli_direccion`, `cli_telefono`, `cli_email`) VALUES
('Juan Pérez', '12345678', 1, 'Av. España 123', '0981-123-456', 'juan.perez@example.com'),
('María López', '87654321', 2, 'Calle 14 de Mayo 456', '0982-654-321', 'maria.lopez@example.com'),
('Carlos González', '11223344', 3, 'Ruta 6 Km 20', '0983-987-654', 'carlos.gonzalez@example.com'),
('Ana Torres', '44332211', 4, 'Calle 3 de Febrero 789', '0984-321-098', 'ana.torres@example.com'),
('Luis Martínez', '55667788', 5, 'Av. Defensores del Chaco 101', '0985-654-321', 'luis.martinez@example.com'),
('Patricia Fernández', '66778899', 6, 'Calle Palma 203', '0986-789-012', 'patricia.fernandez@example.com'),
('Jorge Ruiz', '77889900', 7, 'Av. Mariscal López 999', '0987-890-123', 'jorge.ruiz@example.com'),
('Laura Díaz', '88990011', 8, 'Calle Paraguarí 456', '0988-901-234', 'laura.diaz@example.com'),
('Fernando Benítez', '99001122', 9, 'Calle 21 de Julio 654', '0989-012-345', 'fernando.benitez@example.com'),
('Sofía Romero', '10111213', 10, 'Av. Ñu Guasu 789', '0990-123-456', 'sofia.romero@example.com');


INSERT INTO `productos` (`prod_cod`, `prod_nombre`, `prod_descrip`, `prod_precioven`, `prod_stock`) VALUES
('E001', 'Refrigerador Samsung', 'Refrigerador de 500 litros, con dispensador de agua.', 4500.00, 10),
('E002', 'Lavadora LG', 'Lavadora de carga frontal, 10 kg, con tecnología inverter.', 3500.00, 15),
('E003', 'Horno Microondas Panasonic', 'Microondas de 23 litros con función grill.', 1200.00, 20),
('E004', 'Televisor LG OLED', 'Televisor OLED de 55 pulgadas, 4K UHD.', 2500.00, 8),
('E005', 'Aire Acondicionado Whirlpool', 'Aire acondicionado split, 12000 BTU.', 2800.00, 12),
('E006', 'Cafetera Philips', 'Cafetera de goteo, 1.2 litros.', 600.00, 25),
('E007', 'Plancha Tefal', 'Plancha de vapor con tecnología antiadherente.', 900.00, 30),
('E008', 'Batidora Moulinex', 'Batidora de vaso, 600W, 1.5 litros.', 500.00, 22),
('E009', 'Freidora sin aceite', 'Freidora de aire de 5 litros, tecnología rápida.', 1500.00, 18),
('E010', 'Aspiradora Rowenta', 'Aspiradora ciclónica, 1600W.', 1300.00, 14),
('I001', 'Laptop HP', 'Laptop HP con procesador i5, 8GB RAM, 256GB SSD.', 6000.00, 10),
('I002', 'PC Gamer ASUS', 'PC gamer con GPU RTX 3060, 16GB RAM, 1TB HDD.', 12000.00, 5),
('I003', 'Monitor Dell', 'Monitor LED de 24 pulgadas, Full HD.', 1200.00, 15),
('I004', 'Teclado mecánico Logitech', 'Teclado mecánico retroiluminado, RGB.', 1200.00, 20),
('I005', 'Mouse Razer', 'Mouse gaming ergonómico, sensor óptico.', 800.00, 30),
('I006', 'Router TP-Link', 'Router WiFi AC1200, doble banda.', 1000.00, 25),
('I007', 'Webcam Logitech', 'Webcam HD 1080p, micrófono incorporado.', 900.00, 18),
('I008', 'SSD Crucial', 'SSD de 500GB, SATA III, 2.5".', 1500.00, 20),
('I009', 'Memoria RAM Corsair', 'Memoria RAM de 16GB, DDR4.', 2000.00, 10),
('I010', 'Cargador portátil Anker', 'Cargador portátil de 20000 mAh.', 800.00, 30),
('E011', 'Microondas LG', 'Microondas de 20 litros, fácil limpieza.', 800.00, 22),
('E012', 'Cafetera Espresso DeLonghi', 'Cafetera Espresso, fácil de usar.', 1500.00, 18),
('E013', 'Refrigerador Hisense', 'Refrigerador de 450 litros, clase A.', 4200.00, 14),
('E014', 'Secadora de ropa Samsung', 'Secadora con bomba de calor, 8 kg.', 4000.00, 12),
('E015', 'Lavavajillas Bosch', 'Lavavajillas de 12 cubiertos, clase A++.', 3000.00, 8),
('E016', 'Barra de sonido JBL', 'Barra de sonido con Bluetooth, 200W.', 2000.00, 10),
('E017', 'Proyector Epson', 'Proyector portátil, 3000 lúmenes.', 4500.00, 6),
('E018', 'Estufa a gas', 'Estufa a gas de 5 quemadores, con horno.', 2500.00, 15),
('E019', 'Reproductor de DVD', 'Reproductor de DVD portátil, con pantalla.', 700.00, 20),
('E020', 'Ventilador de pie', 'Ventilador de pie con 3 velocidades.', 400.00, 25);
```
## Observaciones generales
### Cors implementado a nivel del controlador
- [CORS] :  http://localhost:4200

### End points
- [Ciudad] :  "/app/web/rest/ciudades"
- [Cliente] :  "/app/web/rest/clientes"
- [Producto] : "/app/web/rest/productos"
- [Pedidos] : "/app/web/rest/pedidos"
