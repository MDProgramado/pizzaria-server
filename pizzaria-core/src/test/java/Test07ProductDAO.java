import com.br.pizzaria.core.domain.models.Products;
import com.br.pizzaria.core.infrastructure.database.ConnectionFactory;
import com.br.pizzaria.core.infrastructure.database.TransactionManager;
import com.br.pizzaria.core.infrastructure.persistence.JdbcProductDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Test07ProductDAO {

    public static void main(String[] args) {
        System.out.println("🚀 Iniciando Validação Final do ProductDAO\n");

        ConnectionFactory factory = new ConnectionFactory();
        TransactionManager tx = new TransactionManager(factory);
        JdbcProductDAO dao = new JdbcProductDAO();

        UUID tenantMario = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID tenantLuigi = UUID.fromString("22222222-2222-2222-2222-222222222222");

        // ----------------------------------------------------------------------
        System.out.println("CENÁRIO 1 & 2: Gravação do is_active = false e exatidão do BigDecimal");

        Products pizzaInativa = new Products();
        pizzaInativa.setTenant_id(tenantMario);
        pizzaInativa.setName("Pizza de Anchovas (Pausada)");
        pizzaInativa.setBase_price(new BigDecimal("45.50"));
        pizzaInativa.setIs_active(false); // Forçando o inativo no Java

        Products salvaMario = tx.executeInTransaction(tenantMario, c -> dao.save(c, pizzaInativa));
        Products buscaInativa = tx.executeInTransaction(tenantMario, c -> dao.findById(c, salvaMario.getId())).get();

        boolean isActivePassou = !buscaInativa.isIs_active();
        boolean precoPassou = buscaInativa.getBase_price().compareTo(new BigDecimal("45.50")) == 0;

        System.out.println(isActivePassou
                ? "✅ is_active respeitado: Produto foi salvo e recuperado como false."
                : "❌ Falha: O is_active foi ignorado pelo banco.");
        System.out.println(precoPassou
                ? "✅ Preço respeitado: O base_price voltou com o valor exato."
                : "❌ Falha na precisão do base_price.");

        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 3: Isolamento no findAll e filtro de Inativos");

        // Criando um produto para o Luigi para testar vazamento
        Products pizzaLuigi = new Products();
        pizzaLuigi.setTenant_id(tenantLuigi);
        pizzaLuigi.setName("Pizza do Luigi");
        pizzaLuigi.setBase_price(new BigDecimal("50.00"));
        pizzaLuigi.setIs_active(true);
        tx.executeInTransaction(tenantLuigi, c -> dao.save(c, pizzaLuigi));

        List<Products> listaMario = tx.executeInTransaction(tenantMario, dao::findAll);

        boolean temPizzaInativa = listaMario.stream().anyMatch(p -> p.getName().contains("Anchovas"));
        boolean temPizzaLuigi = listaMario.stream().anyMatch(p -> p.getName().contains("Luigi"));

        System.out.println(temPizzaInativa && !temPizzaLuigi
                ? "✅ Sucesso: O findAll trouxe a pizza inativa do Mario e NENHUMA do Luigi."
                : "❌ Falha no findAll: Faltou produto inativo ou vazou dados do Luigi.");

        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 4: Segurança RLS (Luigi tenta apagar produto do Mario)");

        UUID idPizzaMario = salvaMario.getId();
        boolean luigiApagou = tx.executeInTransaction(tenantLuigi, c -> dao.deleteById(c, idPizzaMario));

        System.out.println(!luigiApagou
                ? "✅ Sucesso RLS: Luigi retornou false ao tentar deletar produto do Mario."
                : "❌ Falha Crítica RLS: Luigi conseguiu apagar a pizza do Mario!");

        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 5: Soft Delete Idempotente");

        boolean deletou1 = tx.executeInTransaction(tenantMario, c -> dao.deleteById(c, idPizzaMario));
        boolean deletou2 = tx.executeInTransaction(tenantMario, c -> dao.deleteById(c, idPizzaMario));
        Optional<Products> buscaPosDelete = tx.executeInTransaction(tenantMario, c -> dao.findById(c, idPizzaMario));

        System.out.println(deletou1 && !deletou2 && buscaPosDelete.isEmpty()
                ? "✅ Sucesso: Primeira deleção retornou true, segunda retornou false, e a busca não acha mais o produto."
                : "❌ Falha na lógica de soft delete.");
    }
}