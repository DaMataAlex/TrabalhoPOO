package gui;

import Cultivos.Cereal;
import Cultivos.Cultivo;
import Cultivos.Forragem;
import Cultivos.Fruta;
import Cultivos.Hortalica;
import Cultivos.Legume;
import excecoes.DadoInvalidoException;
import model.Fazenda;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

public class CultivosPanel extends JPanel {

    private final Fazenda fazenda;
    private final Runnable aoAlterar;

    private final DefaultTableModel modeloTabela;
    private final JTable tabela;

    private final JTextField campoId = new JTextField();
    private final JTextField campoArea = new JTextField();
    private final JTextField campoDataPlantio = new JTextField();
    private final JComboBox<String> comboTipo =
            new JComboBox<>(new String[]{"Cereal", "Forragem", "Fruta", "Hortalica", "Legume"});

    private final CardLayout cardLayoutExtra = new CardLayout();
    private final JPanel painelExtra = new JPanel(cardLayoutExtra);

    private final JTextField campoTipoGrao = new JTextField();
    private final JTextField campoDestinoAnimal = new JTextField();
    private final JTextField campoCiclosPorAno = new JTextField();
    private final JCheckBox checkPerene = new JCheckBox("Perene");
    private final JCheckBox checkOrganico = new JCheckBox("Cultivo orgânico");
    private final JTextField campoTipoLegume = new JTextField();
    private final JCheckBox checkTutoramento = new JCheckBox("Exige tutoramento");

    public CultivosPanel(Fazenda fazenda, Runnable aoAlterar) {
        this.fazenda = fazenda;
        this.aoAlterar = aoAlterar;

        setLayout(new BorderLayout(10, 10));

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Tipo", "Área (ha)", "Rendimento (kg)", "Colheita (dias)"}, 0) {
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
        campos.add(new JLabel("Área (ha):"));
        campos.add(campoArea);
        campos.add(new JLabel("Data plantio (dd/mm/aaaa):"));
        campos.add(campoDataPlantio);
        campos.add(new JLabel("Tipo:"));
        campos.add(comboTipo);

        painelExtra.add(criarLinhaExtra("Tipo de grão:", campoTipoGrao), "Cereal");

        JPanel painelForragem = new JPanel(new GridLayout(2, 2, 5, 5));
        painelForragem.add(new JLabel("Destino animal:"));
        painelForragem.add(campoDestinoAnimal);
        painelForragem.add(new JLabel("Ciclos por ano:"));
        painelForragem.add(campoCiclosPorAno);
        painelExtra.add(painelForragem, "Forragem");

        painelExtra.add(checkPerene, "Fruta");
        painelExtra.add(checkOrganico, "Hortalica");

        JPanel painelLegume = new JPanel(new GridLayout(2, 2, 5, 5));
        painelLegume.add(new JLabel("Tipo de legume:"));
        painelLegume.add(campoTipoLegume);
        painelLegume.add(new JLabel(""));
        painelLegume.add(checkTutoramento);
        painelExtra.add(painelLegume, "Legume");

        comboTipo.addActionListener(e -> cardLayoutExtra.show(painelExtra, (String) comboTipo.getSelectedItem()));

        JButton botaoAdicionar = new JButton("Adicionar Cultivo");
        botaoAdicionar.addActionListener(e -> adicionarCultivo());

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

    private void adicionarCultivo() {
        try {
            String id = campoId.getText().trim();
            double area = Double.parseDouble(campoArea.getText().trim());
            String dataPlantio = campoDataPlantio.getText().trim();
            String tipo = (String) comboTipo.getSelectedItem();

            Cultivo cultivo = switch (tipo) {
                case "Cereal" -> new Cereal(id, area, dataPlantio, campoTipoGrao.getText().trim());
                case "Forragem" -> new Forragem(id, area, dataPlantio, campoDestinoAnimal.getText().trim(),
                        Integer.parseInt(campoCiclosPorAno.getText().trim()));
                case "Fruta" -> new Fruta(id, area, dataPlantio, checkPerene.isSelected());
                case "Hortalica" -> new Hortalica(id, area, dataPlantio, checkOrganico.isSelected());
                case "Legume" -> new Legume(id, area, dataPlantio, campoTipoLegume.getText().trim(), checkTutoramento.isSelected());
                default -> throw new DadoInvalidoException("Tipo inválido.");
            };

            fazenda.adicionarCultivo(cultivo);
            limparCampos();
            aoAlterar.run();
            JOptionPane.showMessageDialog(this, "Cultivo cadastrado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Área e ciclos por ano devem ser números válidos.",
                    "Erro de validação", JOptionPane.ERROR_MESSAGE);
        } catch (DadoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoId.setText("");
        campoArea.setText("");
        campoDataPlantio.setText("");
        campoTipoGrao.setText("");
        campoDestinoAnimal.setText("");
        campoCiclosPorAno.setText("");
        checkPerene.setSelected(false);
        checkOrganico.setSelected(false);
        campoTipoLegume.setText("");
        checkTutoramento.setSelected(false);
    }

    public void atualizar() {
        modeloTabela.setRowCount(0);
        for (Cultivo c : fazenda.getCultivos()) {
            modeloTabela.addRow(new Object[]{
                    c.getId(),
                    c.getClass().getSimpleName(),
                    c.getAreaPlantada(),
                    c.calcularRendimento(),
                    c.getTempoColheitaDias()
            });
        }
    }
}
