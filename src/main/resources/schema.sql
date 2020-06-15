drop database if exists tiendaOnline;
create database tiendaOnline;
use tiendaOnline;

drop table if exists Cliente;
create table Cliente (
	idCliente bigint NOT NULL AUTO_INCREMENT,
	nombre varchar(225) NOT NULL,
	apellido varchar(225) NOT NULL,
	fnacimiento varchar(225) NOT NULL,
	direccion varchar(225) NOT NULL,
	email varchar(225) NOT NULL unique,
	password varchar(225) NOT NULL, 
	PRIMARY KEY(idCliente)
);

Drop table if exists Rol;
CREATE TABLE Rol
(
   idRol bigint NOT NULL AUTO_INCREMENT,
   nombreRol varchar (40) NOT NULL,
   PRIMARY KEY (idRol)
);

Drop table if exists Clientes_rol;
CREATE TABLE Clientes_Rol
(
   idCliente bigint NOT NULL,
   idRol bigint NOT NULL,
   PRIMARY KEY (idCliente, idRol ),
   FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente) ON DELETE CASCADE,
   FOREIGN KEY (idRol) REFERENCES Rol(idRol)
);


drop table if exists Banco;
create table Banco (

	idBanco bigint NOT NULL AUTO_INCREMENT,
	nombre varchar(225) NOT NULL,
	numTarjeta bigint NOT NULL,
	titular varchar(225) NOT NULL,
	codSeguridad int(3) NOT NULL,
	dirFactura varchar(225) NOT NULL,
	idCliente bigint NULL,
	PRIMARY KEY(idBanco),
	FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)
);


drop table if exists Producto_Categoria;
drop table if exists ImagenProducto;



drop table if exists Productos;
create table Productos ( 
	idProducto bigint NOT NULL AUTO_INCREMENT,
	codProducto bigint NOT NULL unique, 
	titulo varchar(255) NOT NULL, 
	descripcion varchar(255) NOT NULL,
	precio float NOT NULL, 
	descuento float NOT NULL, 
	stock bigint NOT NULL,
	PRIMARY KEY (idProducto)
);


drop table if exists Categoria;
create table Categoria (
	id_Categoria bigint NOT NULL AUTO_INCREMENT,
	nombre_Categoria varchar(225) NOT NULL,
	PRIMARY KEY (id_Categoria)
);

drop table if exists Producto_Categoria;
create table Producto_Categoria (
	id_Categoria bigint NOT NULL AUTO_INCREMENT,
	idProducto bigint NOT NULL,
	PRIMARY KEY (id_Categoria, idProducto),
    CONSTRAINT FK_Producto_Categoria_1 FOREIGN KEY (id_Categoria) REFERENCES Categoria (id_Categoria) On delete cascade,
    FOREIGN KEY (idProducto) REFERENCES Productos(idProducto)
);

drop table if exists ImagenProducto;
create table ImagenProducto (
	idImagen bigint NOT NULL AUTO_INCREMENT,
	urlImagen varchar(225) NOT NULL,
	idProducto bigint,
   	imagen LONGBLOB NOT NULL,
	PRIMARY KEY (idImagen),
	FOREIGN KEY (idProducto) REFERENCES Productos(idProducto)
	
);	



drop table if exists Compra;
create table Compra (
	idCompra bigint NOT NULL AUTO_INCREMENT,
	idCliente bigint,
	fechaCompra Date,
	PRIMARY KEY(idCompra) ,
	FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente)
);

drop table if exists LineaCompra;
create table LineaCompra (
	idLineaCompra bigint NOT NULL AUTO_INCREMENT,
	idCompra bigint,
	idProducto bigint,
	cantidad bigint,
	precioTotal float,
	PRIMARY KEY(idLineaCompra),
	FOREIGN KEY (idCompra) REFERENCES Compra(idCompra),
	FOREIGN KEY (idProducto) REFERENCES Productos(idProducto) ON DELETE NO ACTION
);

drop table if exists estadoPedido;
CREATE TABLE estadoPedido
(
   idEstado bigint NOT NULL AUTO_INCREMENT,
   estado varchar (40) NOT NULL,
   PRIMARY KEY (idEstado)
);

drop table if exists estado_LineaCompra;
CREATE TABLE estado_LineaCompra
(
   idLineaCompra bigint NOT NULL AUTO_INCREMENT,
   idEstado bigint NOT NULL,
   PRIMARY KEY (idLineaCompra, idEstado ),
   FOREIGN KEY (idLineaCompra) REFERENCES LineaCompra(idLineaCompra) ON DELETE CASCADE,
   FOREIGN KEY (idEstado) REFERENCES estadoPedido(idEstado)
);


	

	