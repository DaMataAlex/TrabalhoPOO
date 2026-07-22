package model;

import Animais.Animal;
import Cultivos.Cultivo;
import Pessoas.Pessoa;
import excecoes.DadoInvalidoException;

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

    public void adicionarFuncionario(Pessoa pessoa) throws DadoInvalidoException {
        if (pessoa.getSalario() < 0) {
            throw new DadoInvalidoException("O salário do funcionário " + pessoa.getNome() + " não pode ser negativo!");
        }
        this.funcionarios.add(pessoa);
    }

    public void adicionarAnimal(Animal animal) throws DadoInvalidoException {
        if (animal.getPeso() <= 0) {
            throw new DadoInvalidoException("O animal de ID " + animal.getId() + " possui um peso inválido (deve ser maior que zero)!");
        }
        this.animais.add(animal);
    }

    public void adicionarCultivo(Cultivo cultivo) {
        this.cultivos.add(cultivo);
    }

    public List<Pessoa> getFuncionarios() { return funcionarios; }
    public List<Animal> getAnimais() { return animais; }
    public List<Cultivo> getCultivos() { return cultivos; }
    public CentroDeProducao getCentroDeProducao() { return centroDeProducao; }
}