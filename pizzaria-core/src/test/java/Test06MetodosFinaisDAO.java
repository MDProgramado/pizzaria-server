import com.br.pizzaria.core.domain.exceptions.PersistenceException;
import com.br.pizzaria.core.domain.models.Ingredients;
import com.br.pizzaria.core.infrastructure.database.ConnectionFactory;
import com.br.pizzaria.core.infrastructure.database.TransactionManager;
import com.br.pizzaria.core.infrastructure.persistence.JdbcIngredientDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Test06MetodosFinaisDAO {

    public static void main(String[] args) {
        System.out.println("🚀 Iniciando Validação Final do IngredientDAO (Deleção, Busca e Débito)\n");

        ConnectionFactory factory = new ConnectionFactory();
        TransactionManager tx = new TransactionManager(factory);
        JdbcIngredientDAO dao = new JdbcIngredientDAO();

        UUID tenantMario = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID tenantLuigi = UUID.fromString("22222222-2222-2222-2222-222222222222");

        // Preparação: Criando ingredientes base
        Ingredients tomateMario = criaIngrediente(tenantMario, "Tomate Mario", "kg", "10.0", "2.0");
        Ingredients queijoMario = criaIngrediente(tenantMario, "Queijo Mario", "kg", "10.0", "2.0");
        Ingredients cebolaMario = criaIngrediente(tenantMario, "Cebola Mario", "kg", "5.0", "1.0");
        Ingredients alhoLuigi = criaIngrediente(tenantLuigi, "Alho Luigi", "kg", "5.0", "1.0");

        Ingredients finalTomateMario = tomateMario;
        tomateMario = tx.executeInTransaction(tenantMario, c -> dao.save(c, finalTomateMario));
        Ingredients finalQueijoMario = queijoMario;
        queijoMario = tx.executeInTransaction(tenantMario, c -> dao.save(c, finalQueijoMario));
        Ingredients finalCebolaMario = cebolaMario;
        cebolaMario = tx.executeInTransaction(tenantMario, c -> dao.save(c, finalCebolaMario));
        Ingredients finalAlhoLuigi = alhoLuigi;
        alhoLuigi = tx.executeInTransaction(tenantLuigi, c -> dao.save(c, finalAlhoLuigi));

        // ----------------------------------------------------------------------
        System.out.println("CENÁRIO 1: Deleção Segura (Soft Delete)");
        UUID idTomate = tomateMario.getId();

        boolean deletou1 = tx.executeInTransaction(tenantMario, c -> dao.deleteById(c, idTomate));
        Optional<Ingredients> buscaTomate = tx.executeInTransaction(tenantMario, c -> dao.findById(c, idTomate));
        boolean deletou2 = tx.executeInTransaction(tenantMario, c -> dao.deleteById(c, idTomate));

        System.out.println(deletou1 && buscaTomate.isEmpty() && !deletou2
                ? "✅ Sucesso: Primeira deleção retornou true, busca retornou vazio e segunda deleção retornou false."
                : "❌ Falha no fluxo de deleção.");

        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 2: Isolamento no findAll");
        List<Ingredients> listaMario = tx.executeInTransaction(tenantMario, dao::findAll);

        boolean temAlhoLuigi = listaMario.stream().anyMatch(i -> i.getName().equals("Alho Luigi"));
        System.out.println(!temAlhoLuigi && listaMario.size() > 0
                ? "✅ Sucesso: O findAll do Mario trouxe " + listaMario.size() + " ingredientes, e NENHUM do Luigi."
                : "❌ Falha: Ocorreu vazamento de dados no findAll.");

        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 3: Débito de Estoque (Caminho Feliz)");
        UUID idQueijo = queijoMario.getId();

        boolean debitou = tx.executeInTransaction(tenantMario, c -> dao.debitBalance(c, idQueijo, new BigDecimal("2.0")));
        Ingredients queijoAtualizado = tx.executeInTransaction(tenantMario, c -> dao.findById(c, idQueijo)).get();

        System.out.println(debitou && queijoAtualizado.getCurrent_balance().compareTo(new BigDecimal("8.0")) == 0
                ? "✅ Sucesso: Débito realizado. Saldo caiu de 10.0 para 8.0."
                : "❌ Falha na matemática do débito.");

        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 4: Débito além do saldo (Teste do CHECK Constraint e Rollback)");
        try {
            tx.executeInTransaction(tenantMario, c -> dao.debitBalance(c, idQueijo, new BigDecimal("10.0")));
            System.out.println("❌ Falha Crítica: O banco permitiu saldo negativo!");
        } catch (PersistenceException e) {
            Ingredients queijoIntacto = tx.executeInTransaction(tenantMario, c -> dao.findById(c, idQueijo)).get();
            System.out.println(queijoIntacto.getCurrent_balance().compareTo(new BigDecimal("8.0")) == 0
                    ? "✅ Sucesso: Erro capturado e Rollback executado. O saldo continuou intacto em 8.0."
                    : "❌ Falha: Ocorreu o erro, mas o saldo foi alterado.");
        }

        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 5: Débito em ingrediente inexistente");
        boolean debitoFantasma = tx.executeInTransaction(tenantMario, c -> dao.debitBalance(c, UUID.randomUUID(), new BigDecimal("1.0")));
        System.out.println(!debitoFantasma
                ? "✅ Sucesso: Retornou false ao tentar debitar UUID inexistente."
                : "❌ Falha: Retornou true para UUID fantasma.");

        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 6: Segurança RLS (Luigi tenta deletar Cebola do Mario)");
        UUID idCebola = cebolaMario.getId();

        boolean luigiDeletaMario = tx.executeInTransaction(tenantLuigi, c -> dao.deleteById(c, idCebola));
        Optional<Ingredients> cebolaIntacta = tx.executeInTransaction(tenantMario, c -> dao.findById(c, idCebola));

        System.out.println(!luigiDeletaMario && cebolaIntacta.isPresent()
                ? "✅ Sucesso RLS: Luigi retornou false ao tentar deletar, e a Cebola do Mario continua intacta."
                : "❌ Falha Crítica RLS no deleteById.");

        // ----------------------------------------------------------------------
        System.out.println("\nCENÁRIO 7: Segurança RLS (Luigi tenta debitar Cebola do Mario)");
        boolean luigiDebitaMario = tx.executeInTransaction(tenantLuigi, c -> dao.debitBalance(c, idCebola, new BigDecimal("1.0")));
        Ingredients cebolaPosDebito = tx.executeInTransaction(tenantMario, c -> dao.findById(c, idCebola)).get();

        System.out.println(!luigiDebitaMario && cebolaPosDebito.getCurrent_balance().compareTo(new BigDecimal("5.0")) == 0
                ? "✅ Sucesso RLS: Luigi retornou false ao tentar debitar, e o saldo da Cebola do Mario continua em 5.0."
                : "❌ Falha Crítica RLS no debitBalance.");
    }

    // Método auxiliar para não poluir o código principal
    private static Ingredients criaIngrediente(UUID tenant, String nome, String unidade, String saldo, String min) {
        Ingredients i = new Ingredients();
        i.setTenant_id(tenant);
        i.setName(nome);
        i.setUnit_measure(unidade);
        i.setCurrent_balance(new BigDecimal(saldo));
        i.setMin_threshold(new BigDecimal(min));
        return i;
    }
}