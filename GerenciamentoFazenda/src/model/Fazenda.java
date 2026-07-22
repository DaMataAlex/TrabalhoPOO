package model;

import Animais.Animal;
import Animais.Ave;
import Animais.Bovino;
import Animais.Ovino;
import Cultivos.Cultivo;
import Pessoas.Pessoa;
import excecoes.DadoInvalidoException;
import excecoes.AnimalNaoComercializavelException;
import excecoes.AnimalNaoEncontradoException;
import excecoes.IdDuplicadoException;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Fazenda implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Pessoa> funcionarios;
    private List<Animal> animais;
    private List<Cultivo> cultivos;
    private CentroDeProducao centroDeProducao;

    public Fazenda() {
        this.funcionarios = new ArrayList<>();
        this.animais = new ArrayList<>();
        this.cultivos = new ArrayList<>();
        this.centroDeProducao = new CentroDeProducao();
    }

    // ==================== ADICIONAR ====================

    public void adicionarFuncionario(Pessoa pessoa) throws DadoInvalidoException {
        if (pessoa.getSalario() < 0) {
            throw new DadoInvalidoException("Salário de " + pessoa.getNome() + " não pode ser negativo!");
        }
        if (pessoa.getNome() == null || pessoa.getNome().trim().isEmpty()) {
            throw new DadoInvalidoException("Nome do funcionário não pode ser vazio!");
        }
        funcionarios.add(pessoa);
    }

    public void adicionarAnimal(Animal animal) throws DadoInvalidoException, IdDuplicadoException {
        if (animal.getPeso() <= 0) {
            throw new DadoInvalidoException("Peso do animal " + animal.getId() + " deve ser maior que zero!");
        }
        if (animal.getId() == null || animal.getId().trim().isEmpty()) {
            throw new DadoInvalidoException("ID do animal não pode ser vazio!");
        }

        for (Animal a : animais) {
            if (a.getId().equalsIgnoreCase(animal.getId())) {
                throw new IdDuplicadoException("Já existe um animal com o ID: " + animal.getId());
            }
        }
        animais.add(animal);
    }

    public void adicionarCultivo(Cultivo cultivo) {
        cultivos.add(cultivo);
    }

    // ==================== BUSCAR E REMOVER ====================

    public Animal buscarAnimal(String id) throws AnimalNaoEncontradoException {
        for (Animal a : animais) {
            if (a.getId().equalsIgnoreCase(id)) {
                return a;
            }
        }
        throw new AnimalNaoEncontradoException("Animal com ID '" + id + "' não foi encontrado.");
    }

    public void removerAnimal(String id) throws AnimalNaoEncontradoException {
        Animal animal = buscarAnimal(id);
        animais.remove(animal);
    }

    // ==================== ROTINA DIÁRIA ====================

    public String executarRotinaDiaria() {
        StringBuilder sb = new StringBuilder();
        double custoTotal = 0.0;
        double totalLeite = 0.0;
        double totalLa = 0.0;
        int totalOvos = 0;

        // Alimentação
        sb.append("--- ALIMENTAÇÃO ---\n");
        for (Animal a : animais) {
            double custo = a.calcularCustoAlimentacaoDiario();
            custoTotal += custo;
            sb.append("  ").append(a.getId()).append(" - R$ ").append(String.format("%.2f", custo)).append("\n");
        }
        sb.append("Custo total de alimentação: R$ ").append(String.format("%.2f", custoTotal)).append("\n");

        // Coleta
        sb.append("\n--- COLETA ---\n");
        for (Animal a : animais) {
            if (a instanceof Bovino b) {
                double leite = b.coletarLeite();
                centroDeProducao.adicionarLeiteTotal(leite);
                totalLeite += leite;
                sb.append("  ").append(a.getId()).append(" - Leite: ").append(leite).append(" L\n");
            }
            if (a instanceof Ovino o) {
                double la = o.tosarLa();
                centroDeProducao.adicionarLaTotal(la);
                totalLa += la;
                sb.append("  ").append(a.getId()).append(" - Lã: ").append(la).append(" kg\n");
            }
            if (a instanceof Ave av) {
                int ovos = av.coletarOvos();
                centroDeProducao.adicionarOvosTotal(ovos);
                totalOvos += ovos;
                sb.append("  ").append(a.getId()).append(" - Ovos: ").append(ovos).append(" un\n");
            }
        }

        sb.append("\nTotal coletado: Leite=").append(totalLeite)
                .append("L, Lã=").append(totalLa).append("kg, Ovos=").append(totalOvos).append("un");
        return sb.toString();
    }

    // ==================== VENDA ====================

    public double venderAnimal(String id, boolean abatido)
            throws AnimalNaoEncontradoException, AnimalNaoComercializavelException, DadoInvalidoException {

        Animal animal = buscarAnimal(id);

        if (!(animal instanceof Comercializavel)) {
            throw new AnimalNaoComercializavelException(
                    "O animal " + id + " não implementa a interface Comercializavel e não pode ser vendido."
            );
        }

        double valor = ((Comercializavel) animal).calcularValorVenda(abatido);
        centroDeProducao.adicionarValorVenda(valor);
        animais.remove(animal);
        return valor;
    }

    // ==================== RELATÓRIOS ====================

    public String getRelatorioCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO COMPLETO DA FAZENDA ===\n\n");

        sb.append("--- FUNCIONÁRIOS ---\n");
        if (funcionarios.isEmpty()) {
            sb.append("  Nenhum funcionário.\n");
        } else {
            for (Pessoa p : funcionarios) {
                sb.append("  - ").append(p.getNome()).append(" | CPF: ").append(p.getCpf())
                        .append(" | Função: ").append(p.desempenharFuncao()).append("\n");
            }
        }

        sb.append("\n--- ANIMAIS ---\n");
        if (animais.isEmpty()) {
            sb.append("  Nenhum animal.\n");
        } else {
            for (Animal a : animais) {
                sb.append("  - ID: ").append(a.getId()).append(" | Peso: ").append(a.getPeso())
                        .append("kg | Idade: ").append(a.getIdadeMeses()).append(" meses | Som: ")
                        .append(a.emitirSom()).append("\n");
            }
        }

        sb.append("\n--- CULTIVOS ---\n");
        if (cultivos.isEmpty()) {
            sb.append("  Nenhum cultivo.\n");
        } else {
            for (Cultivo c : cultivos) {
                sb.append("  - ID: ").append(c.getId()).append(" | Área: ").append(c.getAreaPlantada())
                        .append(" ha | Rendimento estimado: ").append(c.calcularRendimento())
                        .append(" kg | Colheita em: ").append(c.getTempoColheitaDias()).append(" dias\n");
            }
        }

        sb.append("\n--- PRODUÇÃO ACUMULADA ---\n");
        sb.append("  Leite: ").append(centroDeProducao.getTotalLeiteAcumulado()).append(" L\n");
        sb.append("  Lã: ").append(centroDeProducao.getTotalLaAcumulado()).append(" kg\n");
        sb.append("  Ovos: ").append(centroDeProducao.getTotalOvosAcumulados()).append(" un\n");
        sb.append("  Receita total com vendas: R$ ")
                .append(String.format("%.2f", centroDeProducao.getTotalArrecadadoVendas())).append("\n");

        // Custo mensal com funcionários (apenas para demonstrar)
        double custoFunc = 0;
        for (Pessoa p : funcionarios) {
            custoFunc += p.getSalario();
        }
        sb.append("\n--- CUSTOS ---\n");
        sb.append("  Folha de pagamento mensal: R$ ").append(String.format("%.2f", custoFunc)).append("\n");

        return sb.toString();
    }

    // ==================== GETTERS ====================

    public List<Pessoa> getFuncionarios() { return funcionarios; }
    public List<Animal> getAnimais() { return animais; }
    public List<Cultivo> getCultivos() { return cultivos; }
    public CentroDeProducao getCentroDeProducao() { return centroDeProducao; }
}