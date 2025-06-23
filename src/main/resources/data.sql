-- Usuario admin
INSERT INTO usuario (id, username, password, email, rol) VALUES
  (1, 'admin', '$2a$12$VzAdD3x8QbzPIVxpoekCXuVv4GLvlYS.JbBH6aav9TAIO2FrZngqi', 'admin@email.com', 'ADMIN');

-- Productos magistrales
INSERT INTO producto_magistral (id, nombre, descripcion, precio, stock, imagen_url) VALUES
  (1, 'Crema Podológica', 'Crema hidratante para pies', 25.50, 100, '/public/preparados-magistrales/6ad296f2-abba-4711-b51a-cd837449725c-usuario-avatar.jpg'),
  (2, 'Talco Antihongos', 'Talco para prevenir hongos en los pies', 15.00, 50, '/public/preparados-magistrales/16e2f868-14f1-4745-a394-dd2823d3b086-laser.jpg');

-- Establecer el próximo valor de id para usuario en 2
ALTER TABLE usuario ALTER COLUMN id RESTART WITH 2;

-- Establecer el próximo valor de id para producto_magistral en 3
ALTER TABLE producto_magistral ALTER COLUMN id RESTART WITH 3;