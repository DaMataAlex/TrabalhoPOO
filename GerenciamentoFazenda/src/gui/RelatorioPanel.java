package gui;

import model.Fazenda;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;

public class RelatorioPanel extends JPanel {

    private final Fazenda fazenda;
    private final JTextArea areaTexto;

    public RelatorioPanel(Fazenda fazenda) {
        this.fazenda = fazenda;

        setLayout(new BorderLayout(10, 10));

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        JButton botaoAtualizar = new JButton("Atualizar Relatório");
        botaoAtualizar.addActionListener(e -> atualizar());

        add(new JScrollPane(areaTexto), BorderLayout.CENTER);
        add(botaoAtualizar, BorderLayout.SOUTH);
    }

    public void atualizar() {
        areaTexto.setText(fazenda.getRelatorioCompleto());
        areaTexto.setCaretPosition(0);
    }
}
