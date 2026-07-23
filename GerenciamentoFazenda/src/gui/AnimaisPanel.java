package gui;

import Animais.Animal;
import Animais.Ave;
import Animais.Bovino;
import Animais.Ovino;
import Animais.Suino;
import excecoes.AnimalNaoEncontradoException;
import excecoes.DadoInvalidoException;
import excecoes.IdDuplicadoException;
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

public class AnimaisPanel extends JPanel {

    private final Fazenda fazenda;
    private final Runnable aoAlterar;

    private final DefaultTableModel modeloTabela;
    private final JTable tabela;

    private final JTextField campoId = new JTextField();
    private final JTextField campoPeso = new JTextField();
    private final JTextField campoIdade = new JTextField();
    private final JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Bovino", "Ovino", "Suino", "Ave"});

    private final CardLayout cardLayoutExtra = new CardLayout();
    private final JPanel painelExtra = new JPanel(cardLayoutExtra);
    private final JTextField campoExtraBovino = new JTextField();
    private final JTextField campoExtraOvino = new JTextField();
    private final JTextField campoExtraAve = new JTextField();

    public AnimaisPanel(Fazenda fazenda, Runnable aoAlterar) {
        this.fazenda = fazenda;
        this.aoAlterar = aoAlterar;

        setLayout(new BorderLayout(10, 10));

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Tipo", "Peso (kg)", "Idade (meses)", "Som"}, 0) {
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

        JPanel campos = new JPanel(new GridLayout(2, 4, 5, 5));
        campos.add(new JLabel("ID:"));
        campos.add(campoId);
        campos.add(new JLabel("Peso (kg):"));
        campos.add(campoPeso);
        campos.add(new JLabel("Idade (meses):"));
        campos.add(campoIdade);
        campos.add(new JLabel("Tipo:"));
        campos.add(comboTipo);

        painelExtra.add(criarLinhaExtra("Produção leite (L/dia):", campoExtraBovino), "Bovino");
        painelExtra.add(criarLinhaExtra("Lã disponível (kg):", campoExtraOvino), "Ovino");
        painelExtra.add(new JPanel(), "Suino");
        painelExtra.add(criarLinhaExtra("Ovos/dia:", campoExtraAve), "Ave");

        comboTipo.addActionListener(e -> cardLayoutExtra.show(painelExtra, (String) comboTipo.getSelectedItem()));

        JButton botaoAdicionar = new JButton("Adicionar Animal");
        botaoAdicionar.addActionListener(e -> adicionarAnimal());

        JButton botaoRemover = new JButton("Remover Selecionado");
        botaoRemover.addActionListener(e -> removerSelecionado());

        JPanel botoes = new JPanel();
        botoes.add(botaoAdicionar);
        botoes.add(botaoRemover);

        JPanel topo = new JPanel(new BorderLayout(5, 5));
        topo.add(campos, BorderLayout.NORTH);
        topo.add(painelExtra, BorderLayout.CENTER);

        painel.add(topo, BorderLayout.NORTH);
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarLinhaExtra(String rotulo, JTextField campo) {
        JPanel linha = new JPanel(new GridLayout(1, 2, 5, 5));
        linha.add(new JLabel(rotulo));
        linha.add(campo);
        return linha;
    }

    private void adicionarAnimal() {
        try {
            String id = campoId.getText().trim();
            double peso = Double.parseDouble(campoPeso.getText().trim());
            int idade = Integer.parseInt(campoIdade.getText().trim());
            String tipo = (String) comboTipo.getSelectedItem();

            Animal animal = switch (tipo) {
                case "Bovino" -> new Bovino(id, peso, idade, Double.parseDouble(campoExtraBovino.getText().trim()));
                case "Ovino" -> new Ovino(id, peso, idade, Double.parseDouble(campoExtraOvino.getText().trim()));
                case "Suino" -> new Suino(id, peso, idade);
                case "Ave" -> new Ave(id, peso, idade, Integer.parseInt(campoExtraAve.getText().trim()));
                default -> throw new DadoInvalidoException("Tipo inválido.");
            };

            fazenda.adicionarAnimal(animal);
            limparCampos();
            aoAlterar.run();
            JOptionPane.showMessageDialog(this, "Animal cadastrado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Peso, idade e valores extras devem ser números válidos.",
                    "Erro de validação", JOptionPane.ERROR_MESSAGE);
        } catch (DadoInvalidoException | IdDuplicadoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um animal na tabela.");
            return;
        }
        String id = (String) modeloTabela.getValueAt(linha, 0);
        try {
            fazenda.removerAnimal(id);
            aoAlterar.run();
        } catch (AnimalNaoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoId.setText("");
        campoPeso.setText("");
        campoIdade.setText("");
        campoExtraBovino.setText("");
        campoExtraOvino.setText("");
        campoExtraAve.setText("");
    }

    public void atualizar() {
        modeloTabela.setRowCount(0);
        for (Animal a : fazenda.getAnimais()) {
            modeloTabela.addRow(new Object[]{
                    a.getId(),
                    a.getClass().getSimpleName(),
                    a.getPeso(),
                    a.getIdadeMeses(),
                    a.emitirSom()
            });
        }
    }
}
