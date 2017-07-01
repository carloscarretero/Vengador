-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-12-2016 a las 10:57:32
-- Versión del servidor: 5.7.14
-- Versión de PHP: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `vengador`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `refugio`
--

CREATE TABLE `refugio` (
  `id` int(11) NOT NULL,
  `id_tipoRefugio` int(11) NOT NULL,
  `capacidadMax` int(11) NOT NULL,
  `ocupacionActual` int(11) NOT NULL,
  `localizacion` varchar(128) NOT NULL,
  `valoracion` int(11) NOT NULL,
  `distanciaBase` int(11) NOT NULL,
  `tiempoEvacuacion` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `refugio`
--

INSERT INTO `refugio` (`id`, `id_tipoRefugio`, `capacidadMax`, `ocupacionActual`, `localizacion`, `valoracion`, `distanciaBase`, `tiempoEvacuacion`) VALUES
(1, 3, 10, 10, '-7,8.0', 2, 100, 8),
(2, 3, 10, 10, '-7,8.0', 2, 100, 8),
(3, 3, 10, 10, '-7,8.0', 2, 100, 8),
(4, 3, 10, 10, '-7,8.0', 2, 100, 8),
(5, 3, 10, 10, '-7,8.0', 2, 100, 8),
(6, 3, 10, 10, '-7,8.0', 3, 100, 8),
(7, 2, 100, 10, '0,0', 1, 500, 100),
(8, 1, 15, 10, '10', 3, 50, 4);

--
-- Disparadores `refugio`
--
DELIMITER $$
CREATE TRIGGER `refugio_BI` BEFORE INSERT ON `refugio` FOR EACH ROW if NEW.capacidadMax < 0 THEN
  signal sqlstate '45000'
    set message_text = 'Capacidad maxima no puede ser negativa';
elseif (NEW.ocupacionActual < 0 or NEW.ocupacionActual > NEW.capacidadMax) then
  signal sqlstate '45001'
    set message_text = 'Ocupacion actual incorrecta: negativa o mayor que la capacidad maxima';
elseif NEW.localizacion = '' then 
  signal sqlstate '45002'
    set message_text = 'Se debe especificar una localización';
elseif NEW.valoracion < 1 or NEW.valoracion > 5 then
  signal sqlstate '45003'
    set message_text = 'Valoracion incorrecta: debe ser entre 1 y 5';
elseif NEW.distanciaBase < 0 then
  signal sqlstate '45004'
    set message_text = 'La distancia a la base no puede ser negativa';
elseif NEW.tiempoEvacuacion < 0 then 
  signal sqlstate '45005'
    set message_text = 'El tiempo de evacuacion no puede ser negativo';
end if
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `refugio_BU` BEFORE UPDATE ON `refugio` FOR EACH ROW if NEW.capacidadMax < 0 THEN
  signal sqlstate '45000'
    set message_text = 'Capacidad maxima no puede ser negativa';
elseif (NEW.ocupacionActual < 0 or NEW.ocupacionActual > NEW.capacidadMax) then
  signal sqlstate '45001'
    set message_text = 'Ocupacion actual incorrecta: negativa o mayor que la capacidad maxima';
elseif NEW.localizacion = '' then 
  signal sqlstate '45002'
    set message_text = 'Se debe especificar una localización';
elseif NEW.valoracion < 1 or NEW.valoracion > 5 then
  signal sqlstate '45003'
    set message_text = 'Valoracion incorrecta: debe ser entre 1 y 5';
elseif NEW.distanciaBase < 0 then
  signal sqlstate '45004'
    set message_text = 'La distancia a la base no puede ser negativa';
elseif NEW.tiempoEvacuacion < 0 then 
  signal sqlstate '45005'
    set message_text = 'El tiempo de evacuacion no puede ser negativo';
end if
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiporefugio`
--

CREATE TABLE `tiporefugio` (
  `id` int(11) NOT NULL,
  `nombre` varchar(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tiporefugio`
--

INSERT INTO `tiporefugio` (`id`, `nombre`) VALUES
(1, 'Disponible'),
(2, 'Ocupado'),
(3, 'Lleno'),
(4, 'Destruido');

--
-- Disparadores `tiporefugio`
--
DELIMITER $$
CREATE TRIGGER `tipoRefugio_BI` BEFORE INSERT ON `tiporefugio` FOR EACH ROW if NEW.nombre = '' then
  signal sqlstate '45000'
    set message_text = 'El nombre del tipo de refugio no puede estar vacío.';
end if
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `tipoRefugio_BU` BEFORE UPDATE ON `tiporefugio` FOR EACH ROW if NEW.Nombre = '' then
  signal sqlstate '45000'
    set message_text = 'El nombre del tipo de refugio no puede estar vacío.';
end if
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `refugio`
--
ALTER TABLE `refugio`
  ADD PRIMARY KEY (`id`),
  ADD KEY `capacidadMax` (`capacidadMax`,`valoracion`,`distanciaBase`),
  ADD CONSTRAINT `fk_refugio_tipoRefugio` FOREIGN KEY (`id_tipoRefugio`) REFERENCES tiporefugio(`id`)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT;

--
-- Indices de la tabla `tiporefugio`
--
ALTER TABLE `tiporefugio`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nombre` (`nombre`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `refugio`
--
ALTER TABLE `refugio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT de la tabla `tiporefugio`
--
ALTER TABLE `tiporefugio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
