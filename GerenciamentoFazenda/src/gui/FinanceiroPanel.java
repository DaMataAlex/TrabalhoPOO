package gui;

import model.CentroDeProducao;
import model.Fazenda;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class FinanceiroPanel extends JPanel {

    private final Fazenda fazenda;

    private final JLabel labelReceita = new JLabel();
    private final JLabel labelLeite = new JLabel();
    private final JLabel labelLa = new JLabel();
    private final JLabel labelOvos = new JLabel();
    private final JLabel labelFolhaPagamento = new JLabel();

    public FinanceiroPanel(Fazenda fazenda) {
        this.fazenda = fazenda;

        setLayout(new BorderLayout(10, 10));

        JPanel painelDados = new JPanel(new GridLayout(0, 1, 5, 10));
        Font fonte = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
        for (JLabel label : new JLabel[]{labelReceita, labelLeite, labelLa, labelOvos, labelFolhaPagamento}) {
            label.setFont(fonte);
            painelDados.add(label);
        }

        JButton botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.addActionListener(e -> atualizar());

        add(painelDados, BorderLayout.NORTH);
        add(botaoAtualizar, BorderLayout.SOUTH);
    }

    public void atualizar() {
        CentroDeProducao producao = fazenda.getCentroDeProducao();

        labelReceita.setText(String.format("Receita total com vendas: R$ %.2f", producao.getTotalArrecadadoVendas()));
        labelLeite.setText(String.format("Leite acumulado: %.2f L", producao.getTotalLeiteAcumulado()));
        labelLa.setText(String.format("Lã acumulada: %.2f kg", producao.getTotalLaAcumulado()));
        labelOvos.setText(String.format("Ovos acumulados: %d un", producao.getTotalOvosAcumulados()));

        double folhaPagamento = fazenda.getFuncionarios().stream()
                .mapToDouble(p -> p.getSalario())
                .sum();
        labelFolhaPagamento.setText(String.format("Folha de pagamento mensal: R$ %.2f", folhaPagamento));
    }
}
