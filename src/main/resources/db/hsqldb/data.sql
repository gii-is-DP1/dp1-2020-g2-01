-- One admin user, named admin1 with passwor 4dm1n and authority admin
--INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
--INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
--INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
--INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
--INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
--INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');


/*INSERT INTO clientes(username,password,enabled) VALUES ('fraborcar','prueba',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'fraborcar','owner');
INSERT INTO clientes(username,password,enabled) VALUES ('serarirud','prueba',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'serarirud','owner');
INSERT INTO clientes(username,password,enabled) VALUES ('alemorgar4','prueba',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'alemorgar4','owner');
INSERT INTO clientes(username,password,enabled) VALUES ('antfunmej','prueba',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'antfunmej','owner');
INSERT INTO clientes(username,password,enabled) VALUES ('jesvarzam','prueba',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'jesvarzam','owner');*/


/*INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');
INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);
INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');*/

--INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
--INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
--INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
--INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
--INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
--INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
--INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
--INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
--INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
--INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

--INSERT INTO owners VALUES (11, 'Francisco', 'Borrego', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
--INSERT INTO owners VALUES (12, 'Sergio', 'Arias Ruda', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
--INSERT INTO owners VALUES (13, 'Alejandro', 'Morales', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
--INSERT INTO owners VALUES (14, 'Antonio', 'Funes', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
--INSERT INTO owners VALUES (15, 'Jesus', 'Vargas', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
/*INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Matias', '2012-06-08', 1, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Mathew', '2012-07-08', 1, 12);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Hallo', '2012-07-08', 1, 13);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17, 'Guapo', '2012-06-08', 1, 14);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (18, 'EteSech', '2012-06-08', 1, 15);
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');*/

--INSERT INTO clientes VALUES (1, 'Vargas Ruda', '12345678L', '2019-03-18', 'Francisco José','647194727', 'serarirud');
--INSERT INTO vehiculos(id, matricula, num_bastidor, modelo) VALUES (100, '1234ABC', 'VSSZZZ6KZ1R149943', 'Ford Kuga');


--CONTRASEÑA = 'Prueba123'
SET @CONTRASENA = '$2a$10$JX6kTLbKV/FXUc11PwCl.e7nn6W9USLzZ1TKgIMXSdmTWGouRoaR6';


INSERT INTO tipovehiculo(id,tipo) VALUES (1, 'COCHE');
INSERT INTO tipovehiculo(id,tipo) VALUES (2, 'MOTO');
INSERT INTO tipovehiculo(id,tipo) VALUES (3, 'TODOTERRENO');
INSERT INTO tipovehiculo(id,tipo) VALUES (4, 'QUAD');
INSERT INTO tipovehiculo(id,tipo) VALUES (5, 'CAMION');
INSERT INTO tipovehiculo(id,tipo) VALUES (6, 'FURGONETA');

INSERT INTO tipocita(id,tipo,icono) VALUES (1, 'REVISIÓN', 'revision.png');
INSERT INTO tipocita(id,tipo,icono) VALUES (2, 'DISTRIBUCIÓN', 'distribucion.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (3, 'AIRE ACONDICIONADO', 'aire-acondicionado.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (4, 'FRENOS', 'frenos.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (5, 'NEUMÁTICOS', 'neumaticos.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (6, 'ENCENDIDO', 'encendido.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (7, 'REVISIÓN PRE-ITV', 'pre-itv.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (8, 'LIMPIAPARABRISAS', 'limpiaparabrisas.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (9, 'BATERÍA', 'bateria.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (10, 'AMORTIGUADORES', 'amortiguador.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (11, 'CARROCERÍA', 'carroceria.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (12, 'ROTURA DE CRISTALES', 'rotura.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (13, 'LLAVES', 'llaves.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (14, 'AVISO DEL FABRICANTE', 'fabricante.png');
INSERT INTO tipoCita(id,tipo,icono) VALUES (15, 'OTROS', 'caret-abajo.png');


--INSERT INTO empleados(id, apellidos, dni, fecha_nacimiento, nombre, telefono, correo, fecha_fin_contrato, 
				--	fecha_ini_contrato, num_seg_social, sueldo, username) VALUES (1, 'Sech', '11111111A', 
					--'2021-11-16', 'Ete', 666666666, 'correo@correo.com', '2021-11-16', '2021-11-16', '11111111111', 700.50, 1);

INSERT INTO talleres(id, name, correo, telefono, ubicacion) VALUES (1, 'Taller Sevilla Customs', 'prueba@gmail.com', 
					'666655555', 'Calle Prueba, numero 2');
INSERT INTO talleres(id, name, correo, telefono, ubicacion) VALUES (2, 'Taller Sevilla Customs 2', 'prueba2@gmail.com', 
					'666666677', 'Calle Prueba, numero 3');

INSERT INTO proveedores(id, name, nif, telefono, email) VALUES (200, 'Gumersindo', '12345678A', '665112233', 'gumersindo@gmail.com');

INSERT INTO users(username, password, enabled) VALUES ('jesfunrud', @CONTRASENA, TRUE);
INSERT INTO authorities(username, authority) VALUES ('jesfunrud', 'cliente');
INSERT INTO clientes(id, dni, nombre, apellidos, fecha_nacimiento, telefono, email, username) VALUES (1, '11223344M', 'Jesus', 'Funes Ruda', '2000-02-20', '666339933', 'tallersevillacustoms@gmail.com', 'jesfunrud');

INSERT INTO vehiculos(id, matricula, num_bastidor, modelo, tipo_vehiculo_id, cliente_id) VALUES (100, '1234ABC', 'VSSZZZ6KZ1R149943', 'Ford Kuga', 1,1);
INSERT INTO vehiculos(id, matricula, num_bastidor, modelo, tipo_vehiculo_id, cliente_id) VALUES (101, '1234FBC', 'VSSZZZ6KZ1R149941', 'Kawasaki Ninja ZX-6R', 2,1);
INSERT INTO vehiculos(id, matricula, num_bastidor, modelo, tipo_vehiculo_id, cliente_id) VALUES (102, '5678ABC', 'VSSZZZ6KZ1R149945', 'Citroen C3', 1, 1);
INSERT INTO vehiculos(id, matricula, num_bastidor, modelo, tipo_vehiculo_id, cliente_id) VALUES (103, '5679ABC', 'VSSZZZ6KZ1R149946', 'Citroen C4', 1, 1);
INSERT INTO vehiculos(id, matricula, num_bastidor, modelo, tipo_vehiculo_id, cliente_id) VALUES (104, '5688ABC', 'VSSZZZ6KZ1R149947', 'Pegaso Tecno', 5, 1);


INSERT INTO citas(id, fecha, hora, vehiculo_id, taller_id, asistido) VALUES (100,CURDATE(), 19, 100, 1, true);
INSERT INTO citas_tipocita VALUES (100, 1);
INSERT INTO citas_tipocita VALUES (100, 2);
INSERT INTO citas(id, vehiculo_id, fecha, hora, taller_id, asistido) VALUES (101, 100, CURDATE(), 18, 1, true);
INSERT INTO citas_tipocita VALUES (101, 1);
INSERT INTO citas(id, vehiculo_id, fecha, hora, taller_id, asistido) VALUES (102, 100, CURDATE(), 20, 2, true);
INSERT INTO citas_tipocita VALUES (102, 1);
INSERT INTO citas(id, vehiculo_id, fecha, hora, taller_id, asistido) VALUES (103, 100, DATEADD(day, 14, CURDATE()), 17, 2, true);
INSERT INTO citas_tipocita VALUES (103, 1);

--
INSERT INTO users(username,password,enabled) VALUES ('clienteEjemplo',@CONTRASENA ,true);
INSERT INTO authorities(username,authority) VALUES ('clienteEjemplo','cliente');
INSERT INTO clientes(id, dni, fecha_nacimiento, nombre, apellidos, telefono, email, username) VALUES (99, '12345678A','2000-02-20', 'Cliente', 'Ejemplo', '646123456', 'tallersevillacustoms@gmail.com', 'clienteEjemplo');


INSERT INTO users(username,password,enabled) VALUES ('empleado', @CONTRASENA,true);
INSERT INTO authorities(username,authority) VALUES ('empleado','empleado');
INSERT INTO empleados(id, apellidos, dni, fecha_nacimiento, nombre, telefono, email, fecha_fin_contrato, 
				fecha_ini_contrato, num_seg_social, sueldo, username, taller_id) VALUES (1, 'Páez García', '77838103F', 
					'1982-09-13', 'Candela', 638184619, 'correodeempleado@gmail.com', '2024-01-09', '2018-11-16', '111111111118', 1050, 'empleado', 1);

INSERT INTO vehiculos(id, matricula, num_bastidor, modelo, tipo_vehiculo_id, cliente_id) VALUES (201, '2111AAB', 'VSSZZZ6KZ1R549944', 'Renault Clio', 1, 99);
INSERT INTO vehiculos(id, matricula, num_bastidor, modelo, tipo_vehiculo_id, cliente_id) VALUES (202, '2123AAB', 'VSSZZZ6KZ1R349946', 'Yamaha YZF', 2, 99);


INSERT INTO citas(id, vehiculo_id, fecha, hora, taller_id, asistido) VALUES (104, 201, CURDATE()-3, 17, 2, false);
INSERT INTO citas_tipocita VALUES (104, 1);
INSERT INTO citas(id, vehiculo_id, fecha, hora, taller_id, asistido) VALUES (105, 201, CURDATE()-2, 17, 2, false);
INSERT INTO citas_tipocita VALUES (105, 1);
INSERT INTO citas(id, vehiculo_id, fecha, hora, taller_id, asistido) VALUES (106, 201, CURDATE()-1, 17, 2, false);
INSERT INTO citas_tipocita VALUES (106, 1);



					
INSERT INTO users(username,password,enabled) VALUES ('admin', @CONTRASENA,true);
INSERT INTO authorities(username,authority) VALUES ('admin','admin');
INSERT INTO administradores(id, apellidos, dni, fecha_nacimiento, nombre, telefono, email, num_seg_social, username) VALUES (1, 'Delgado Gutiérrez', '87361534W', '1974-04-12', 'Javier', 645183746, 'correodeadministrador@gmail.com','123456789009', 'admin');

INSERT INTO users(username,password,enabled) VALUES ('fraborcar', @CONTRASENA,true);
INSERT INTO authorities(username,authority) VALUES ('fraborcar','cliente');
INSERT INTO clientes(id, dni, nombre, apellidos, telefono, fecha_nacimiento, email, username) VALUES (100, '34567890A','Francisco', 'Borrego', '646123456', '2000-02-20', 'tallersevillacustoms@gmail.com', 'fraborcar');


-- Pruebas con recambios

INSERT INTO proveedores(id, name, nif, telefono, email) VALUES (201, 'Pablo', '12345678H', '665112233', 'gumersindo@gmail.com');


INSERT INTO recambios(id, name, tipo_vehiculo_id, cantidad_actual) VALUES (100, 'Neumático Michelin PILOT SPORT 225/45', 1, 45);
INSERT INTO recambios(id, name, tipo_vehiculo_id, cantidad_actual) VALUES (101, 'Tubo de escape AKRAPOVIC S-PI4SO3-HRSSBL', 2, 10);
INSERT INTO recambios(id, name, tipo_vehiculo_id, cantidad_actual) VALUES (102, 'Batería para coche Tudor Technica TB740 12V 74Ah', 1, 67);
INSERT INTO recambios(id, name, tipo_vehiculo_id, cantidad_actual) VALUES (103, 'Neumáticos YOKOHAMA GEOLANDER M/T G003', 3, 46);
INSERT INTO recambios(id, name, tipo_vehiculo_id, cantidad_actual) VALUES (104, 'Correa de distribución CONTITECH dentada ', 4, 78);
INSERT INTO recambios(id, name, tipo_vehiculo_id, cantidad_actual) VALUES (105, 'Faros HalogenH3 12/24V', 5, 58);
INSERT INTO recambios(id, name, tipo_vehiculo_id, cantidad_actual) VALUES (106, 'Cerradura de seguridad CF-19 FURGOFAC-1 CIERRE', 6, 173);
INSERT INTO recambios(id, name, tipo_vehiculo_id, cantidad_actual) VALUES (107, 'Espejo Retrovisor Craftride LS4', 2, 46);
INSERT INTO recambios(id, name, tipo_vehiculo_id, cantidad_actual) VALUES (108, 'Asiento espuma de vinilo negro Quad', 4, 28);

INSERT INTO proveedores_recambios(proveedor_id, recambios_id) VALUES (201, 100);
INSERT INTO proveedores_recambios(proveedor_id, recambios_id) VALUES (200, 101);
INSERT INTO proveedores_recambios(proveedor_id, recambios_id) VALUES (201, 102);
INSERT INTO proveedores_recambios(proveedor_id, recambios_id) VALUES (200, 103);
INSERT INTO proveedores_recambios(proveedor_id, recambios_id) VALUES (201, 104);
INSERT INTO proveedores_recambios(proveedor_id, recambios_id) VALUES (200, 105);
INSERT INTO proveedores_recambios(proveedor_id, recambios_id) VALUES (201, 106);
INSERT INTO proveedores_recambios(proveedor_id, recambios_id) VALUES (200, 107);
INSERT INTO proveedores_recambios(proveedor_id, recambios_id) VALUES (201, 108);


--


INSERT INTO vehiculos(id, matricula, num_bastidor, modelo, tipo_vehiculo_id, cliente_id) VALUES (200, '1111AAC', 'VSSZZZ6KZ1R129943', 'Opel Corsa', 1, 100);
INSERT INTO vehiculos(id, matricula, num_bastidor, modelo, tipo_vehiculo_id, cliente_id) VALUES (203, '1111ABB', 'VSSZZZ6KZ1R142943', 'Quad Hero', 4, 100);
INSERT INTO vehiculos(id, matricula, num_bastidor, modelo, tipo_vehiculo_id, cliente_id) VALUES (204, '1111AAB', 'VSSZZZ6KZ1R141943', 'Furgoneta Volkswagen', 6, 100);


INSERT INTO users(username,password,enabled) VALUES ('empleado1', @CONTRASENA,true);
INSERT INTO authorities(username,authority) VALUES ('empleado1','empleado');
INSERT INTO empleados(id, apellidos, dni, fecha_nacimiento, nombre, telefono, email, fecha_fin_contrato, 
				fecha_ini_contrato, num_seg_social, sueldo, username, taller_id) VALUES (100, 'Sech', '11111111A', 
					'2000-11-16', 'Ete', 666666666, 'correo@correo.com', '2021-11-16', '2020-11-16', '11111111114', 950, 'empleado1', 1);
					
INSERT INTO users(username,password,enabled) VALUES ('empleado2', @CONTRASENA,true);
INSERT INTO authorities(username,authority) VALUES ('empleado2','empleado');					
INSERT INTO empleados(id, apellidos, dni, fecha_nacimiento, nombre, telefono, email, fecha_fin_contrato, 
				fecha_ini_contrato, num_seg_social, sueldo, username, taller_id) VALUES (101, 'Aguayo', '11111112A', 
					'2000-11-16', 'Javi', 666666666, 'correo@correo.com', '2021-11-16', '2020-11-16', '11111111211', 950, 'empleado2', 2);

INSERT INTO citas(id, fecha, hora, vehiculo_id, taller_id, asistido, tiene_reparacion) VALUES (200,CURDATE()-1, 20, 200, 1, true, true);
INSERT INTO citas_tipocita VALUES (200, 1);
INSERT INTO citas(id, fecha, hora, vehiculo_id, taller_id, asistido, tiene_reparacion) VALUES (201,CURDATE()-2, 20, 200, 2, true, true);
INSERT INTO citas_tipocita VALUES (201, 1);

INSERT INTO citas(id, fecha, hora, vehiculo_id, taller_id, asistido, tiene_reparacion) VALUES (202,CURDATE()+2, 20, 200, 1, true, false);
INSERT INTO citas_tipocita VALUES (202, 1);
INSERT INTO citas_empleados VALUES (202, 100);

INSERT INTO reparaciones(id, descripcion, tiempo_estimado, fecha_finalizacion, fecha_entrega, fecha_recogida, cita_id) 
		VALUES (1,'Descripción', CURDATE()-1, null, CURDATE()-3, null, 200);

INSERT INTO horas_trabajadas(id, trabajo_realizado, precio_hora, horas_trabajadas, empleado) VALUES (8, 'Cambio de rueda', 10.5, 3, 100);
INSERT INTO horas_trabajadas(id, trabajo_realizado, precio_hora, horas_trabajadas, empleado) VALUES (9, 'Cambio de frenos', 10.5, 4, 101);

INSERT INTO horas_reparacion VALUES (1, 8);
INSERT INTO horas_reparacion VALUES (1, 9);
		
INSERT INTO reparaciones(id, descripcion, tiempo_estimado, fecha_finalizacion, fecha_entrega, fecha_recogida, cita_id) 
		VALUES (2,'Descripción', '2020-10-24', '2020-10-22', '2020-10-22', '2020-10-23', 201);
INSERT INTO facturas(id, fecha_Pago) VALUES (1, '2021-02-27');
INSERT INTO linea_factura(precio_base, descuento, reparacion, descripcion, recambio_id, cantidad,factura) VALUES (90, 15, 2, 'Cambio 4 ruedas', 100, 4,1);

INSERT INTO horas_trabajadas(id, trabajo_realizado, precio_hora, horas_trabajadas, empleado) VALUES (10, 'Cambio de rueda', 10.5, 3, 100);
INSERT INTO horas_trabajadas(id, trabajo_realizado, precio_hora, horas_trabajadas, empleado) VALUES (11, 'Cambio de frenos', 10.5, 4, 101);

INSERT INTO horas_reparacion VALUES (2, 10);
INSERT INTO horas_reparacion VALUES (2, 11);

INSERT INTO citas(id, fecha, hora, vehiculo_id, taller_id, asistido, tiene_reparacion) VALUES (203,CURDATE()-20, 20, 200, 1, true, true);
INSERT INTO citas_tipocita VALUES (203, 1);
INSERT INTO reparaciones(id, descripcion, tiempo_estimado, fecha_finalizacion, fecha_entrega, fecha_recogida, cita_id) 
		VALUES (3,'Reparacion con descuento, por cada 10 dias que pasen de la fecha estimada se va añadiendo un 10% hasta 50%', CURDATE()-15, null, CURDATE()-20, null, 203);
		
INSERT INTO horas_trabajadas(id, trabajo_realizado, precio_hora, horas_trabajadas, empleado) VALUES (12, 'Cambio de rueda', 10.5, 3, 100);
INSERT INTO linea_factura(precio_base, descuento, reparacion, descripcion, recambio_id, cantidad) VALUES (90, 15, 3, 'Cambio 4 ruedas', 100, 4);

INSERT INTO citas(id, fecha, hora, vehiculo_id, taller_id, asistido, tiene_reparacion) VALUES (204,CURDATE()-20, 19, 200, 1, true, true);
INSERT INTO citas_tipocita VALUES (204, 1);
INSERT INTO reparaciones(id, descripcion, tiempo_estimado, fecha_finalizacion, fecha_entrega, fecha_recogida, cita_id) 
		VALUES (4,'Reparacion con cobro extra, por cada 10 dias que pasen de la fecha de finalizacion sin que el cliente recoja su vehiculo', CURDATE()-15, CURDATE()-15, CURDATE()-20, null, 204);
		
INSERT INTO horas_trabajadas(id, trabajo_realizado, precio_hora, horas_trabajadas, empleado) VALUES (13, 'Cambio de rueda', 10.5, 4, 100);
INSERT INTO linea_factura(precio_base, descuento, reparacion, descripcion, recambio_id, cantidad) VALUES (90, 15, 4, 'Cambio 4 ruedas', 100, 4);


INSERT INTO reparacionescomunes(id, nombre, descripcion) VALUES (1, 'Reparación espejo retrovisor izquierdo', 'Po lo hase así y asá');
INSERT INTO reparacionescomunes(id, nombre, descripcion) VALUES (2, 'Reparación luneta térmica', 'Po lo hase así y con la mano asá');




INSERT INTO pedidos(id, cantidad, precio_unidad, recibido, proveedor_id, recambio) VALUES (1, 3, 214.32, false, 201, 100);
INSERT INTO pedidos(id, cantidad, precio_unidad, recibido, proveedor_id, recambio) VALUES (2, 3, 214.32, true, 201, 101);



-- Pruebas con solicitudes
INSERT INTO solicitudes(terminada, cantidad, recambio_id, empleado_id, reparacion_id) VALUES (false, 5, 100, 101, 1);


-- 
INSERT INTO facturas_recambios(id, fecha_pago, pedido_recambio) VALUES (1, '2021-02-12', 1);
INSERT INTO facturas_recambios(id, fecha_pago, pedido_recambio) VALUES (2, '2021-02-23', 2);

