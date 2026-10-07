-- Añade una categoría temática a cada FAQ, para poder agruparlas en la página
-- de preguntas frecuentes (IT y seguridad, Beneficios, Ausencias y vacaciones, etc.)
ALTER TABLE faq ADD COLUMN tema VARCHAR(100);

UPDATE faq SET tema = 'General' WHERE tema IS NULL;
