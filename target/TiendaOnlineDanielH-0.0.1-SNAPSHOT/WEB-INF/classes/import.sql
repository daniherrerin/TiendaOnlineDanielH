

insert into Rol (idRol, nombreRol ) values (1, 'ROL_Usuario');
insert into ROL (idRol, nombreRol ) values (2,'ROL_ADMIN');

insert into estadoPedido (idEstado, estado ) values (1, 'Entregado');
insert into estadoPedido (idEstado, estado ) values (2, 'Devolucion');

INSERT INTO Cliente (nombre,apellido,fnacimiento,direccion,email,password) VALUES('admin','admin','01-01-1991','Direccion', 'admin@admin.com','$2a$10$x81Gc/y2Abgous23qb/6DOAWHm9UlzZl35wRo4emOUAIglRce/Pzu');
INSERT INTO Cliente (nombre,apellido,fnacimiento,direccion,email,password) VALUES('user','user','01-01-1991','Direccion', 'user@user.com','$2a$10$x81Gc/y2Abgous23qb/6DOAWHm9UlzZl35wRo4emOUAIglRce/Pzu');

INSERT INTO Clientes_rol VALUES  (1,2);
INSERT INTO Clientes_rol VALUES  (2,1);

INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(1,'>--descripción--<',10,200,50,'Violín');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(2,'>--descripción--<',20,200,50,'Guitarra');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(3,'>--descripción--<',20,200,50,'Bajo');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(4,'>--descripción--<',40,200,50,'Batería');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(5,'>--descripción--<',50,200,80,'Piano');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(6,'>--descripción--<',20,200,60,'Violonchelo');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(7,'>--descripción--<',60,200,30,'Flauta');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(8,'>--descripción--<',70,200,40,'Tambor');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(9,'>--descripción--<',80,20,60,'Ukelele');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(10,'>--descripción--<',90,200,30,'Contrabajo');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(11,'>--descripción--<',20,200,80,'Armónica');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(12,'>--descripción--<',10,200,30,'Trompeta');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(13,'>--descripción--<',10,200,20,'Acordeón');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(14,'>--descripción--<',10,200,10,'Arpa');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(15,'>--descripción--<',10,200,90,'Timbales');
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo) VALUES(16,'>--descripción--<',10,200,80,'Cajón');

INSERT INTO Categoria (nombre_Categoria) VALUES('Cuerda');
INSERT INTO Categoria (nombre_Categoria) VALUES('Viento');
INSERT INTO Categoria (nombre_Categoria) VALUES('Percusión');

INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,1);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,2);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,3);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,5);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,6);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,9);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,10);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,14);

INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,7);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,11);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,12);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,13);


INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (3,4);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (3,8);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (3,15);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (3,16);









