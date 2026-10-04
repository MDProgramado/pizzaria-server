import com.br.pizzaria.core.infrastructure.database.ConnectionFactory;

import java.sql.*;

public class Test03DebitarEstoque {
    public static void main(String[] args) {
        System.out.println("⏳ Inicializando a ConnectionFactory...");

        // Se alguma variável estiver faltando, o programa quebra aqui mesmo com IllegalStateException
        ConnectionFactory factory = new ConnectionFactory();

        // O try-with-resources garante o fechamento da Connection, PreparedStatement e ResultSet
        try (Connection conexao = factory.getConnection();
             PreparedStatement declaracao = conexao.prepareStatement("SELECT current_user");
             ResultSet resultado = declaracao.executeQuery()) {

            if (resultado.next()) {
                String usuarioLogado = resultado.getString(1);
                System.out.println("✅ Conexão estabelecida com sucesso!");
                System.out.println("👤 Usuário logado no PostgreSQL: " + usuarioLogado);
            }

        } catch (SQLException e) {
            System.err.println("❌ Falha na conexão com o banco de dados.");
            e.printStackTrace();
        }
    }

    }

