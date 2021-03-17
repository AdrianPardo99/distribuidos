# @author Adrian Gonzalez Pardo
create database if not exists servicio_web;
use servicio_web;
create table if not exists usuarios(id_usuario integer auto_increment primary key, email varchar(256) not null, nombre varchar(100) not null, apellido_paterno varchar(100) not null, apellido_materno varchar(100), fecha_nacimiento date not null, telefono varchar(20), genero char(1));
create table if not exists fotos_usuarios( id_foto integer auto_increment primary key, foto longblob, id_usuario integer not null);
alter table fotos_usuarios add foreign key (id_usuario) references usuarios(id_usuario);
create unique index usuarios_1 on usuarios(email);
