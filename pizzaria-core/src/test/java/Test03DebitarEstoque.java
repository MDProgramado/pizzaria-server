import java.sql.*;

public class Test03DebitarEstoque {
    public static void main(String[] args) {
         final String URL = "jdbc:postgresql://localhost:5435/pizzaria_db";
        final String USUARIO = "app_pizzaria";
        final String SENHA = System.getenv("DB_PASSWORD");

        if (SENHA == null){
            System.err.println("❌ Erro: A variável de ambiente DB_PASSWORD não foi encontrada.");
            return;
        }
        System.out.println("⏳ Tentando conectar ao banco de dados...");

        String sql = "SELECT current_user";
        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement declaracao = conexao.prepareStatement(sql);
             ResultSet resultado = declaracao.executeQuery()){

            if (resultado.next()){
                String usuarioLogado = resultado.getString(1);
                System.out.println("✅ Conectado com sucesso!");
                System.out.println("👤 Usuário conectado no banco: " + usuarioLogado);

            }

        } catch (SQLException e) {
            System.err.println("❌ Falha crítica ao conectar no banco de dados.");
            // Imprime o motivo real do erro (ex: senha errada, porta recusada)
            System.err.println("Motivo: " + e.getMessage());
        }


    }
}
