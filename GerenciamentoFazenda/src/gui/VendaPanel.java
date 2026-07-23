package gui;

import Animais.Animal;
import excecoes.AnimalNaoComercializavelException;
import excecoes.AnimalNaoEncontradoException;
import excecoes.DadoInvalidoException;
import model.Comercializavel;
import model.Fazenda;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class VendaPanel extends JPanel {

    private final Fazenda fazenda;
    private final Runnable aoAlterar;

    private final DefaultTableModel modeloTabela;
    private final JTable tabela;
    private final JCheckBox checkAbatido = new JCheckBox("Vender abatido");

    public VendaPanel(Fazenda fazenda, Runnable aoAlterar) {
        this.fazenda = fazenda;
        this.aoAlterar = aoAlterar;

        setLayout(new BorderLayout(10, 10));

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Tipo", "Peso (kg)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton botaoVender = new JButton("Vender Selecionado");
        botaoVender.addActionListener(e -> venderSelecionado());

        JPanel rodape = new JPanel();
        rodape.add(checkAbatido);
        rodape.add(botaoVender);
        add(rodape, BorderLayout.SOUTH);
    }

    private void venderSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um animal na tabela.");
            return;
        }
        String id = (String) modeloTabela.getValueAt(linha, 0);
        try {
            double valor = fazenda.venderAnimal(id, checkAbatido.isSelected());
            aoAlterar.run();
            JOptionPane.showMessageDialog(this, String.format("Venda realizada! Valor: R$ %.2f", valor));
        } catch (AnimalNaoEncontradoException | AnimalNaoComercializavelException | DadoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizar() {
        modeloTabela.setRowCount(0);
        for (Animal a : fazenda.getAnimais()) {
            if (a instanceof Comercializavel) {
                modeloTabela.addRow(new Object[]{a.getId(), a.getClass().getSimpleName(), a.getPeso()});
            }
        }
    }
}
