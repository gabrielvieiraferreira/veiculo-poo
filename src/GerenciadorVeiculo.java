import java.sql.*;
import java.util.Scanner;

public class GerenciadorVeiculo {
    private static final String URL = "jdbc:mysql://localhost:3306/veiculos";
    private static final String USUARIO = "root";
    private static final String SENHA = "335658";

    private Connection conexao;

    public GerenciadorVeiculo() throws SQLException {
        conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        try {
            String query = "INSERT INTO veiculos (marca, modelo, tipo, detalhe) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());

            if (veiculo instanceof Carro) {
                stmt.setString(3, "Carro");
                stmt.setInt(4, ((Carro) veiculo).getQuantidadePortas());
            } else if (veiculo instanceof Moto) {
                stmt.setString(3, "Moto");
                stmt.setInt(4, ((Moto) veiculo).getCilindradas());
            }

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    veiculo.setId(id);
                    System.out.println("Veículo adicionado com ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar veículo: " + e.getMessage());
        }
    }

    public void obterVeiculo(int id) {
        try {
            String query = "SELECT * FROM veiculos WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String tipo = rs.getString("tipo");
                int detalhe = rs.getInt("detalhe");

                if (tipo.equals("Carro")) {
                    Carro carro = new Carro(id, marca, modelo, detalhe);
                    System.out.println(carro);
                } else if (tipo.equals("Moto")) {
                    Moto moto = new Moto(id, marca, modelo, detalhe);
                    System.out.println(moto);
                }
            } else {
                System.out.println("Veículo não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter veículo: " + e.getMessage());
        }
    }

    public void atualizarVeiculo(int id, String marca, String modelo, int detalhe) {
        try {
            String query = "UPDATE veiculos SET marca = ?, modelo = ?, detalhe = ? WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(query);
            stmt.setString(1, marca);
            stmt.setString(2, modelo);
            stmt.setInt(3, detalhe);
            stmt.setInt(4, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Veículo atualizado com sucesso.");
            } else {
                System.out.println("Veículo não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    public void deletarVeiculo(int id) {
        try {
            String query = "DELETE FROM veiculos WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(query);
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Veículo deletado com sucesso.");
            } else {
                System.out.println("Veículo não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar veículo: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            GerenciadorVeiculo gerenciador = new GerenciadorVeiculo();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n1- Cadastrar veículo");
                System.out.println("2- Consultar veículo");
                System.out.println("3- Atualizar veículo");
                System.out.println("4- Deletar veículo");
                System.out.println("5- Sair");
                System.out.print("Escolha uma opção: ");
                int escolha = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                switch (escolha) {
                    case 1:
                        System.out.println("Tipo de veículo (1- Carro, 2- Moto): ");
                        int tipo = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha
                        System.out.println("Marca: ");
                        String marca = scanner.nextLine();
                        System.out.println("Modelo: ");
                        String modelo = scanner.nextLine();
                        System.out.println(tipo == 1 ? "Quantidade de Portas: " : "Cilindradas: ");
                        int detalhe = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha

                        if (tipo == 1) {
                            Carro carro = new Carro(0, marca, modelo, detalhe); 
                            gerenciador.adicionarVeiculo(carro);
                        } else {
                            Moto moto = new Moto(0, marca, modelo, detalhe);
                            gerenciador.adicionarVeiculo(moto);
                        }
                        break;
                    case 2:
                        System.out.println("ID do Veículo: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha
                        gerenciador.obterVeiculo(id);
                        break;
                    case 3:
                        System.out.println("ID do Veículo: ");
                        id = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha
                        System.out.println("Nova marca: ");
                        marca = scanner.nextLine();
                        System.out.println("Novo modelo: ");
                        modelo = scanner.nextLine();
                        System.out.println("Novo detalhe (Quantidade de Portas ou Cilindradas): ");
                        detalhe = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha
                        gerenciador.atualizarVeiculo(id, marca, modelo, detalhe);
                        break;
                    case 4:
                        System.out.println("ID do Veículo: ");
                        id = scanner.nextInt();
                        scanner.nextLine(); 
                        gerenciador.deletarVeiculo(id);
                        break;
                    case 5:
                        System.out.println("Encerrando o programa.");
                        gerenciador.conexao.close();
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro de conexão com o banco de dados: " + e.getMessage());
        }
    }
}
