# @author Adrian Gonzalez Pardo
create user hugo@localhost identified by 'contraseña-del-usuario-hugo';
grant all on servicio_web.* to hugo@localhost;
