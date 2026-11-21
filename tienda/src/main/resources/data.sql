-- ===================================================================
--  DATOS INICIALES · se cargan en cada arranque.
--  Los id salen 1, 2, 3... en el orden de los INSERT.
-- ===================================================================

INSERT INTO estudios (nombre, pais) VALUES
  ('Nintendo',       'Japón'),     -- id 1
  ('FromSoftware',   'Japón'),     -- id 2
  ('Mercury Steam',  'España'),    -- id 3
  ('CD Projekt Red', 'Polonia');   -- id 4

-- ===================================================================
--  VIDEOJUEGOS · DESCOMENTA este bloque cuando termines la TAREA 1.
--  Si los 8 INSERT entran sin error al arrancar, tu entidad esta bien.
-- ===================================================================
INSERT INTO videojuegos (titulo, codigo, precio, unidades, edad_minima,
                         estudio_id, is_deleted, created_at, updated_at) VALUES
  ('The Legend of Zelda: Breath of the Wild', 'NIN-2017-Z', 59.99,  6, 12, 1, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Mario Kart 8 Deluxe',                     'NIN-2017-M', 49.99, 10,  3, 1, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Animal Crossing: New Horizons',           'NIN-2020-A', 54.90,  0,  3, 1, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Elden Ring',                              'FRS-2022-E', 69.99,  4, 16, 2, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Dark Souls III',                          'FRS-2016-D', 29.95,  0, 16, 2, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Metroid Dread',                           'MSS-2021-M', 39.99,  3, 12, 3, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Castlevania: Lords of Shadow',            'MSS-2010-C', 19.99,  2, 18, 3, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('The Witcher 3: Wild Hunt',                'CDP-2015-W', 24.99,  8, 18, 4, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
