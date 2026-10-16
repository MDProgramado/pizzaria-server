//Confirmar que está como admin
SELECT current_user;

//Adicionar a coluna tenant_id
ALTER TABLE products_recipes ADD COLUMN tenant_id UUID;

//Preencher as linhas existentes
UPDATE products_recipes pr SET tenant_id = p.tenant_id FROM products p WHERE p.id = pr.product_id;

//Tornar obrigatória e ligar à tabela de tenants
ALTER TABLE products_recipes ALTER COLUMN tenant_id SET NOT NULL;
ALTER TABLE products_recipes ADD CONSTRAINT products_recipes_tenant_id_fkey FOREIGN KEY (tenant_id) REFERENCES tenants(id);

//Ligar o RLS e criar a policy
ALTER TABLE products_recipes ENABLE ROW LEVEL SECURITY;
CREATE POLICY tenant_isolation_policy ON products_recipes USING (tenant_id = current_setting('app.current_tenant')::uuid);

//Colocar NOT NULL em products.is_active
UPDATE products SET is_active = TRUE WHERE is_active IS NULL;
ALTER TABLE products ALTER COLUMN is_active SET NOT NULL; // Assim o getBoolean do Java nunca transforma um nulo em false sem avisar.