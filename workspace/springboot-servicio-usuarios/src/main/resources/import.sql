INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('juan', '$2a$10$Xop37FdRGYRY1T.A4h/gzubcICQEzZxjD9iV8cCsx9IUEUXsbRBXe', true, 'Juan', 'Espinoza', 'juanmesp@hotmail.com'); 
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('admin', '$2a$10$g.MhXQ1COXqhyZXIS1t3lezezicJRlSzkzpXcpuks7F0ABcZbl3ci', true, 'Jhon', 'Doe', 'jhon.doe@admin.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (1,1);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (2,1);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (2,2);