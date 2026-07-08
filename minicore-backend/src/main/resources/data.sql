-- ============================================================
-- SEED DATA — MiniCore Logística
-- ============================================================

-- Zonas
INSERT INTO zona (id_zona, nombre_zona, tarifa_por_kg) VALUES
  (1, 'Norte', 1.50),
  (2, 'Sur',   2.00),
  (3, 'Centro',1.75);

-- Repartidores
INSERT INTO repartidor (id_repartidor, nombre, email) VALUES
  (1, 'Andrés',  'andres@logistica.com'),
  (2, 'Camila',  'camila@logistica.com'),
  (3, 'Luis',    'luis@logistica.com');

-- Envios de Mayo 2025 (dentro del rango de ejemplo)
INSERT INTO envio (id_envio, id_repartidor, id_zona, peso_kg, fecha_envio) VALUES
  (1,  1, 1, 5.0,  '2025-05-02'),
  (2,  1, 1, 8.0,  '2025-05-08'),
  (3,  1, 1, 6.5,  '2025-05-14'),
  (4,  1, 1, 7.0,  '2025-05-20'),
  (5,  1, 1, 5.5,  '2025-05-27'),  -- Andrés: 5 envíos, 32 kg Norte → $48.00
  (6,  2, 2, 5.0,  '2025-05-03'),
  (7,  2, 2, 7.0,  '2025-05-15'),
  (8,  2, 2, 6.0,  '2025-05-25'),  -- Camila: 3 envíos, 18 kg Sur → $36.00
  -- Luis no tiene envíos en mayo 2025
  -- Envíos fuera del rango para demostrar el filtro
  (9,  1, 1, 10.0, '2025-04-10'),
  (10, 2, 3, 4.0,  '2025-06-05'),
  (11, 3, 2, 9.0,  '2025-06-12');
