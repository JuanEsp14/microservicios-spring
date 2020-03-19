INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('juan', '12345', 1, 'Juan', 'Espinoza', 'juanmesp@hotmail.com'); 
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('admin', '12345', 1, 'Jhon', 'Doe', 'jhon.doe@admin.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, rol_id) VALUES (1,1);
INSERT INTO `usuarios_roles` (usuario_id, rol_id) VALUES (2,1);
INSERT INTO `usuarios_roles` (usuario_id, rol_id) VALUES (2,2);