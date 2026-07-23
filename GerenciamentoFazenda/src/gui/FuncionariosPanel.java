package gui;

import Pessoas.Gerente;
import Pessoas.Peao;
import Pessoas.Pessoa;
import Pessoas.Veterinario;
import excecoes.DadoInvalidoException;
import model.Fazenda;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;

public class FuncionariosPanel extends JPanel {

    private final Fazenda fazenda;
    private final Runnable aoAlterar;

    private final DefaultTableModel modeloTabela;
    private final JTable tabela;

    private final JTextField campoNome = new JTextField();
    private final JTextField campoCpf = new JTextField();
    private final JTextField campoDataNascimento = new JTextField();
    private final JTextField campoTelefone = new JTextField();
    private final JTextField campoSalario = new JTextField();
    private final JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Peao", "Veterinario", "Gerente"});

    private final CardLayout cardLayoutExtra = new CardLayout();
    private final JPanel painelExtra = new JPanel(cardLayoutExtra);
    private final JTextField campoExtraPeao = new JTextField();
    private final JTextField campoExtraVeterinario = new JTextField();
    private final JTextField campoExtraGerente = new JTextField();

    public FuncionariosPanel(Fazenda fazenda, Runnable aoAlterar) {
        this.fazenda = fazenda;
        this.aoAlterar = aoAlterar;

        setLayout(new BorderLayout(10, 10));

        modeloTabela = new DefaultTableModel(
                new Object[]{"Nome", "CPF", "Salário", "Tipo", "Função"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        add(criarPainelFormulario(), BorderLayout.SOUTH);
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        JPanel campos = new JPanel(new GridLayout(3, 4, 5, 5));
        campos.add(new JLabel("Nome:"));
        campos.add(campoNome);
        campos.add(new JLabel("CPF:"));
        campos.add(campoCpf);
        campos.add(new JLabel("Data Nasc. (dd/mm/aaaa):"));
        campos.add(campoDataNascimento);
        campos.add(new JLabel("Telefone:"));
        campos.add(campoTelefone);
        campos.add(new JLabel("Salário:"));
        campos.add(campoSalario);
        campos.add(new JLabel("Tipo:"));
        campos.add(comboTipo);

        painelExtra.add(criarLinhaExtra("Setor:", campoExtraPeao), "Peao");
        painelExtra.add(criarLinhaExtra("CRMV:", campoExtraVeterinario), "Veterinario");
        painelExtra.add(criarLinhaExtra("Nível de acesso:", campoExtraGerente), "Gerente");

        comboTipo.addActionListener(e -> cardLayoutExtra.show(painelExtra, (String) comboTipo.getSelectedItem()));

        JButton botaoAdicionar = new JButton("Adicionar Funcionário");
        botaoAdicionar.addActionListener(e -> adicionarFuncionario());

        JPanel topo = new JPanel(new BorderLayout(5, 5));
        topo.add(campos, BorderLayout.NORTH);
        topo.add(painelExtra, BorderLayout.CENTER);

        painel.add(topo, BorderLayout.NORTH);
        painel.add(botaoAdicionar, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarLinhaExtra(String rotulo, JTextField campo) {
        JPanel linha = new JPanel(new GridLayout(1, 2, 5, 5));
        linha.add(new JLabel(rotulo));
        linha.add(campo);
        return linha;
    }

    private void adicionarFuncionario() {
        try {
            String nome = campoNome.getText().trim();
            String cpf = campoCpf.getText().trim();
            String dataNascimento = campoDataNascimento.getText().trim();
            String telefone = campoTelefone.getText().trim();
            double salario = Double.parseDouble(campoSalario.getText().trim());
            String tipo = (String) comboTipo.getSelectedItem();

            Pessoa pessoa = switch (tipo) {
                case "Peao" -> new Peao(nome, cpf, dataNascimento, telefone, salario, campoExtraPeao.getText().trim());
                case "Veterinario" -> new Veterinario(nome, cpf, dataNascimento, telefone, salario, campoExtraVeterinario.getText().trim());
                case "Gerente" -> new Gerente(nome, cpf, dataNascimento, telefone, salario, campoExtraGerente.getText().trim());
                default -> throw new DadoInvalidoException("Tipo inválido.");
            };

            fazenda.adicionarFuncionario(pessoa);
            limparCampos();
            aoAlterar.run();
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Salário deve ser um número válido.",
                    "Erro de validação", JOptionPane.ERROR_MESSAGE);
        } catch (DadoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoDataNascimento.setText("");
        campoTelefone.setText("");
        campoSalario.setText("");
        campoExtraPeao.setText("");
        campoExtraVeterinario.setText("");
        campoExtraGerente.setText("");
    }

    public void atualizar() {
        modeloTabela.setRowCount(0);
        for (Pessoa p : fazenda.getFuncionarios()) {
            modeloTabela.addRow(new Object[]{
                    p.getNome(),
                    p.getCpf(),
                    p.getSalario(),
                    p.getClass().getSimpleName(),
                    p.desempenharFuncao()
            });
        }
    }
}
