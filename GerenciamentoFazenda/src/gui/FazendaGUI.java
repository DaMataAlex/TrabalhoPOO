package gui;

import Animais.Ave;
import Animais.Bovino;
import Animais.Ovino;
import Animais.Suino;
import Cultivos.Cereal;
import Cultivos.Hortalica;
import Pessoas.Peao;
import Pessoas.Veterinario;
import excecoes.DadoInvalidoException;
import excecoes.IdDuplicadoException;
import model.Fazenda;
import utils.GerenciadorArquivo;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FazendaGUI extends JFrame {

    private final Fazenda fazenda;

    private RelatorioPanel relatorioPanel;
    private AnimaisPanel animaisPanel;
    private FuncionariosPanel funcionariosPanel;
    private CultivosPanel cultivosPanel;
    private VendaPanel vendaPanel;
    private FinanceiroPanel financeiroPanel;

    public FazendaGUI() {
        super("Sistema de Gerenciamento de Fazenda");
        this.fazenda = GerenciadorArquivo.carregar();
        popularDadosIniciaisSeVazio();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        relatorioPanel = new RelatorioPanel(fazenda);
        animaisPanel = new AnimaisPanel(fazenda, this::atualizarTudo);
        funcionariosPanel = new FuncionariosPanel(fazenda, this::atualizarTudo);
        cultivosPanel = new CultivosPanel(fazenda, this::atualizarTudo);
        vendaPanel = new VendaPanel(fazenda, this::atualizarTudo);
        financeiroPanel = new FinanceiroPanel(fazenda);

        abas.addTab("Relatório", relatorioPanel);
        abas.addTab("Animais", animaisPanel);
        abas.addTab("Funcionários", funcionariosPanel);
        abas.addTab("Cultivos", cultivosPanel);
        abas.addTab("Venda", vendaPanel);
        abas.addTab("Financeiro", financeiroPanel);

        add(abas);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salvarESair();
            }
        });

        atualizarTudo();
    }

    private void popularDadosIniciaisSeVazio() {
        if (!fazenda.getAnimais().isEmpty() || !fazenda.getFuncionarios().isEmpty()) {
            return;
        }
        try {
            fazenda.adicionarFuncionario(new Peao("João", "123.456.789-00", "01/01/1980", "(99) 99999-9999", 2000.0, "Setor A"));
            fazenda.adicionarFuncionario(new Veterinario("Dra. Ana", "987.654.321-00", "10/05/1975", "(88) 88888-8888", 5000.0, "CRMV-123"));
            fazenda.adicionarAnimal(new Bovino("BOV-01", 350.0, 28, 31));
            fazenda.adicionarAnimal(new Bovino("BOV-02", 328.0, 26, 28));
            fazenda.adicionarAnimal(new Ovino("OV-01", 55.0, 12, 3.2));
            fazenda.adicionarAnimal(new Ovino("OV-02", 75.0, 13, 2.9));
            fazenda.adicionarAnimal(new Suino("SUI-01", 160, 8));
            fazenda.adicionarAnimal(new Ave("AVE-01", 2.0, 4, 1));
            fazenda.adicionarCultivo(new Cereal("CEREAL-01", 10.5, "15/03/2026", "Milho"));
            fazenda.adicionarCultivo(new Hortalica("HORT-01", 2.0, "01/04/2026", true));
        } catch (DadoInvalidoException | IdDuplicadoException e) {
            JOptionPane.showMessageDialog(this, "Erro ao popular dados iniciais: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTudo() {
        relatorioPanel.atualizar();
        animaisPanel.atualizar();
        funcionariosPanel.atualizar();
        cultivosPanel.atualizar();
        vendaPanel.atualizar();
        financeiroPanel.atualizar();
    }

    private void salvarESair() {
        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja salvar os dados antes de sair?",
                "Salvar e sair",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (opcao == JOptionPane.CANCEL_OPTION || opcao == JOptionPane.CLOSED_OPTION) {
            return;
        }
        if (opcao == JOptionPane.YES_OPTION) {
            GerenciadorArquivo.salvar(fazenda);
        }
        dispose();
        System.exit(0);
    }
}
