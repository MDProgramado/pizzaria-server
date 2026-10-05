--Alterações feitas 05/10/2026 - Por razão do do banco dedos ter permitido operações negativas, e fazendo alterando unique do customers_email_key;


SELECT current_user;
SELECT current_user;
ALTER TABLE products_recipes DROP CONSTRAINT chk_ingredients_balance_non_negative;
ALTER TABLE products_recipes ADD CONSTRAINT chk_products_recipes_quantity_positive CHECK (quantity_required > 0);
ALTER TABLE products_recipes ADD CONSTRAINT chk_products_recipes_quantity_positive CHECK (quantity_required > 0);
ALTER TABLE customers DROP CONSTRAINT customers_email_key;