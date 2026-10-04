import com.br.pizzaria.core.domain.exceptions.PersistenceException;
import com.br.pizzaria.core.domain.models.Ingredients;
import com.br.pizzaria.core.infrastructure.database.ConnectionFactory;
import com.br.pizzaria.core.infrastructure.database.TransactionManager;
import com.br.pizzaria.core.infrastructure.persistence.JdbcIngredientDAO;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class Test05IntegracaoDAO {

    public static void main(String[] args) {
        System.out.println("🚀 Iniciando Testes de Integração da Arquitetura (DAO + TM + RLS)\n");

        ConnectionFactory connectionFactory = new ConnectionFactory();
        TransactionManager txManager = new TransactionManager(connectionFactory);
        JdbcIngredientDAO dao = new JdbcIngredientDAO();

        // IDs fixos usados no nosso script SQL
        UUID tenantMario = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID tenantLuigi = UUID.fromString("22222222-2222-2222-2222-222222222222");

        // ----------------------------------------------------------------------
        System.out.println("CENÁRIO 1: Salvar e Buscar (Tenant Mario)");

        Ingredients ingredienteMario = new Ingredients();
        ingredienteMario.setTenant_id(tenantMario); // Não passamos o ID, o banco gera
        ingredienteMario.setName("Mussarela Especial");
        ingredienteMario.setUnit_measure("kg");
        ingredienteMario.setCurrent_balance(new BigDecimal("10.5"));
        ingredienteMario.setMin_threshold(new BigDecimal("2.0"));

        Ingredients salvo = txManager.executeInTransaction(tenantMario, connection -> {
            return dao.save(connection, ingredienteMario);
        });
        System.out.println("✅ Salvo com sucesso! ID retornado do PostgreSQL: " + salvo.getId());

        Optional<Ingredients> buscaMario = txManager.executeInTransaction(tenantMario, connection -> {
            return dao.findById(connection, salvo.getId());
        });
        System.out.println(buscaMario.isPresent()
                ? "✅ Encontrado com sucesso pelo Mario: " + buscaMario.get().getName()
                : "❌ Falha: O ingrediente deveria ter sido encontrado.");


        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 2: Isolamento (Tenant Luigi tenta buscar ingrediente do Mario)");

        Optional<Ingredients> buscaLuigi = txManager.executeInTransaction(tenantLuigi, connection -> {
            return dao.findById(connection, salvo.getId());
        });
        System.out.println(buscaLuigi.isEmpty()
                ? "✅ RLS FUNCIONANDO! Luigi não consegue enxergar a mussarela do Mario."
                : "❌ Falha de RLS: O registro vazou para outro tenant!");


        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 3: Tenant errado no insert (Violando a RLS na gravação)");

        Ingredients ingredienteInvasor = new Ingredients();
        ingredienteInvasor.setTenant_id(tenantLuigi); // Configura para o Luigi
        ingredienteInvasor.setName("Tomate do Invasor");
        ingredienteInvasor.setUnit_measure("kg");
        ingredienteInvasor.setCurrent_balance(new BigDecimal("5.0"));
        ingredienteInvasor.setMin_threshold(new BigDecimal("1.0"));

        try {
            // Tenta inserir logado como Mario, mas passando os dados do Luigi
            txManager.executeInTransaction(tenantMario, connection -> {
                return dao.save(connection, ingredienteInvasor);
            });
            System.out.println("❌ Falha Crítica: O RLS permitiu gravar um registro para outro tenant!");
        } catch (PersistenceException e) {
            System.out.println("✅ Segurança RLS confirmada! Inserção bloqueada.");
            System.out.println("   -> Retorno do Banco: " + e.getMessage());
        }


        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 4: O Rollback na Falha (Validação do bug do commit parcial)");

        Ingredients massaFantasma = new Ingredients();
        massaFantasma.setTenant_id(tenantMario);
        massaFantasma.setName("Massa Fantasma");
        massaFantasma.setUnit_measure("kg");
        massaFantasma.setCurrent_balance(new BigDecimal("20.0"));
        massaFantasma.setMin_threshold(new BigDecimal("5.0"));

        try {
            txManager.executeInTransaction(tenantMario, connection -> {
                // Passo A: Grava com sucesso no banco (mas a transação ainda está aberta)
                dao.save(connection, massaFantasma);
                System.out.println("   -> Ingrediente salvo temporariamente com o ID: " + massaFantasma.getId());

                // Passo B: Acontece um erro inesperado no meio do processo
                System.out.println("   -> Simulando uma falha grave (RuntimeException)...");
                throw new RuntimeException("Falha catastrófica simulada na regra de negócio!");
            });
        } catch (RuntimeException e) {
            System.out.println("   -> Erro capturado pelo sistema principal.");
        }

        // Passo C: Tenta encontrar o registro no banco
        Optional<Ingredients> buscaFantasma = txManager.executeInTransaction(tenantMario, connection -> {
            return dao.findById(connection, massaFantasma.getId());
        });

        System.out.println(buscaFantasma.isEmpty()
                ? "✅ Rollback FUNCIONOU PERFEITAMENTE! A Massa Fantasma não foi efetivada no banco."
                : "❌ Falha Crítica: Ocorreu o commit parcial!");
    }
}