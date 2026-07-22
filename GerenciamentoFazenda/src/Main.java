import Animais.*;
import Pessoas.*;
import model.Fazenda;
import model.Comercializavel;
import excecoes.DadoInvalidoException;
import excecoes.AnimalNaoComercializavelException;
import excecoes.AnimalNaoEncontradoException;
import excecoes.IdDuplicadoException;
import utils.GerenciadorArquivo;
import Cultivos.*;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Fazenda fazenda;

    public static void main(String[] args) {
        System.out.println("=== FAZENDA VIRTUAL ===");
        System.out.println("Carregando dados...");
        fazenda = GerenciadorArquivo.carregar();

        // Cadastro inicial se vazio
        if (fazenda.getAnimais().isEmpty() && fazenda.getFuncionarios().isEmpty()) {
            try {
                System.out.println("Nenhum dado encontrado. Realizando cadastro inicial...");
                // Funcionários
                fazenda.adicionarFuncionario(new Peao("João", "123.456.789-00", "01/01/1980", "(99) 99999-9999", 2000.0, "Setor A"));
                fazenda.adicionarFuncionario(new Veterinario("Dra. Ana", "987.654.321-00", "10/05/1975", "(88) 88888-8888", 5000.0, "CRMV-123"));
                // Animais
                fazenda.adicionarAnimal(new Bovino("BOV-01", 350.0, 28, 31));
                fazenda.adicionarAnimal(new Bovino("BOV-02", 328.0, 26, 28));
                fazenda.adicionarAnimal(new Ovino("OV-01", 55.0, 12, 3.2));
                fazenda.adicionarAnimal(new Ovino("OV-02", 75.0, 13, 2.9));
                fazenda.adicionarAnimal(new Suino("SUI-01", 160, 8));
                fazenda.adicionarAnimal(new Ave("AVE-01", 2.0, 4, 1));
                // Cultivos
                fazenda.adicionarCultivo(new Cereal("CEREAL-01", 10.5, "15/03/2026", "Milho"));
                fazenda.adicionarCultivo(new Hortalica("HORT-01", 2.0, "01/04/2026", true));
                System.out.println("Cadastro inicial concluído.\n");
            } catch (DadoInvalidoException e) {
                System.err.println("ERRO: " + e.getMessage());
                return;
            }
        } else {
            System.out.println("Dados carregados: " + fazenda.getAnimais().size() + " animais, "
                    + fazenda.getFuncionarios().size() + " funcionários, "
                    + fazenda.getCultivos().size() + " cultivos.\n");
        }

        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha: ");
            scanner.nextLine(); // limpa buffer

            try {
                switch (opcao) {
                    case 1 -> System.out.println(fazenda.getRelatorioCompleto());
                    case 2 -> adicionarAnimal();
                    case 3 -> adicionarFuncionario();
                    case 4 -> adicionarCultivo();
                    case 5 -> System.out.println(fazenda.executarRotinaDiaria());
                    case 6 -> venderAnimal();
                    case 7 -> System.out.println("=== RESUMO FINANCEIRO ===\n" +
                            "Receita total com vendas: R$ " +
                            String.format("%.2f", fazenda.getCentroDeProducao().getTotalArrecadadoVendas()) +
                            "\nProdução acumulada:\n  Leite: " +
                            fazenda.getCentroDeProducao().getTotalLeiteAcumulado() + " L\n  Lã: " +
                            fazenda.getCentroDeProducao().getTotalLaAcumulado() + " kg\n  Ovos: " +
                            fazenda.getCentroDeProducao().getTotalOvosAcumulados() + " un");
                    case 0 -> System.out.println("Salvando e saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (AnimalNaoEncontradoException e) {
                System.err.println("ERRO: " + e.getMessage());
            } catch (AnimalNaoComercializavelException e) {
                System.err.println("ERRO DE COMERCIALIZAÇÃO: " + e.getMessage());
            } catch (IdDuplicadoException e) {
                System.err.println("ERRO DE CADASTRO: " + e.getMessage());
            } catch (DadoInvalidoException e) {
                System.err.println("ERRO DE VALIDAÇÃO: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("ERRO INESPERADO: " + e.getMessage());
                e.printStackTrace();
            }
        } while (opcao != 0);

        GerenciadorArquivo.salvar(fazenda);
        scanner.close();
        System.out.println("Sistema finalizado.");
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private static void exibirMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MENU");
        System.out.println("=".repeat(50));
        System.out.println("1 - Relatório completo");
        System.out.println("2 - Cadastrar animal");
        System.out.println("3 - Cadastrar funcionário");
        System.out.println("4 - Cadastrar cultivo");
        System.out.println("5 - Executar rotina diária");
        System.out.println("6 - Vender animal");
        System.out.println("7 - Resumo financeiro");
        System.out.println("0 - Salvar e sair");
        System.out.println("=".repeat(50));
    }

    private static int lerInteiro(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Digite um número inteiro: ");
            }
        }
    }

    private static double lerDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Digite um número: ");
            }
        }
    }

    private static String lerString(String msg, boolean obrigatorio) throws DadoInvalidoException {
        System.out.print(msg);
        String valor = scanner.nextLine().trim();
        if (obrigatorio && valor.isEmpty()) {
            throw new DadoInvalidoException("Campo obrigatório não pode ser vazio.");
        }
        return valor;
    }

    private static boolean lerBoolean(String msg) {
        System.out.print(msg + " (S/N): ");
        String resp = scanner.nextLine().trim().toUpperCase();
        return resp.equals("S") || resp.equals("SIM");
    }

    // ==================== OPÇÕES DE CADASTRO ====================

    private static void adicionarAnimal() throws DadoInvalidoException {
        System.out.println("\n--- NOVO ANIMAL ---");
        String id = lerString("ID: ", true);
        double peso = lerDouble("Peso (kg): ");
        int idade = (int) lerDouble("Idade (meses): ");
        System.out.println("Tipo: [1] Bovino [2] Ovino [3] Suino [4] Ave");
        int tipo = lerInteiro("Escolha: ");

        Animal animal = switch (tipo) {
            case 1 -> new Bovino(id, peso, idade, lerDouble("Produção leite (L/dia): "));
            case 2 -> new Ovino(id, peso, idade, lerDouble("Lã disponível (kg): "));
            case 3 -> new Suino(id, peso, idade);
            case 4 -> new Ave(id, peso, idade, (int) lerDouble("Ovos/dia: "));
            default -> throw new DadoInvalidoException("Tipo inválido.");
        };
        fazenda.adicionarAnimal(animal);
        System.out.println("Animal cadastrado!");
    }

    private static void adicionarFuncionario() throws DadoInvalidoException {
        System.out.println("\n--- NOVO FUNCIONÁRIO ---");
        String nome = lerString("Nome: ", true);
        String cpf = lerString("CPF: ", true);
        String data = lerString("Data nascimento (dd/mm/aaaa): ", true);
        String tel = lerString("Telefone: ", true);
        double salario = lerDouble("Salário: ");
        System.out.println("Tipo: [1] Peão [2] Veterinário [3] Gerente");
        int tipo = lerInteiro("Escolha: ");

        Pessoa pessoa = switch (tipo) {
            case 1 -> new Peao(nome, cpf, data, tel, salario, lerString("Setor: ", true));
            case 2 -> new Veterinario(nome, cpf, data, tel, salario, lerString("CRMV: ", true));
            case 3 -> new Gerente(nome, cpf, data, tel, salario, lerString("Nível acesso: ", true));
            default -> throw new DadoInvalidoException("Tipo inválido.");
        };
        fazenda.adicionarFuncionario(pessoa);
        System.out.println("Funcionário cadastrado!");
    }

    private static void adicionarCultivo() throws DadoInvalidoException {
        System.out.println("\n--- NOVO CULTIVO ---");
        String id = lerString("ID: ", true);
        double area = lerDouble("Área (ha): ");
        String data = lerString("Data plantio (dd/mm/aaaa): ", true);
        System.out.println("Tipo: [1] Cereal [2] Forragem [3] Fruta [4] Hortalica [5] Legume");
        int tipo = lerInteiro("Escolha: ");

        Cultivo cultivo = switch (tipo) {
            case 1 -> new Cereal(id, area, data, lerString("Tipo grão: ", true));
            case 2 -> new Forragem(id, area, data, lerString("Destino animal: ", true), (int) lerDouble("Ciclos/ano: "));
            case 3 -> new Fruta(id, area, data, lerBoolean("Perene?"));
            case 4 -> new Hortalica(id, area, data, lerBoolean("Orgânico?"));
            case 5 -> new Legume(id, area, data, lerString("Tipo legume: ", true), lerBoolean("Exige tutoramento?"));
            default -> throw new DadoInvalidoException("Tipo inválido.");
        };
        fazenda.adicionarCultivo(cultivo);
        System.out.println("Cultivo cadastrado!");
    }

    private static void venderAnimal() throws DadoInvalidoException {
        System.out.println("\n--- VENDER ANIMAL ---");
        if (fazenda.getAnimais().isEmpty()) {
            System.out.println("Não há animais.");
            return;
        }
        // Lista apenas os comercializáveis
        System.out.println("Animais disponíveis para venda:");
        for (Animal a : fazenda.getAnimais()) {
            if (a instanceof Comercializavel) {
                System.out.println("  - " + a.getId() + " (peso: " + a.getPeso() + "kg)");
            }
        }
        String id = lerString("ID do animal: ", true);
        boolean abatido = lerBoolean("Vender abatido? (S=sim)");
        double valor = fazenda.venderAnimal(id, abatido);
        System.out.println("Venda realizada! Valor: R$ " + String.format("%.2f", valor));
    }
}