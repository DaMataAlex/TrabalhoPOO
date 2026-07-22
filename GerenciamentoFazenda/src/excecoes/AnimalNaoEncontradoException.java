package excecoes;

public class AnimalNaoEncontradoException extends RuntimeException {
    public AnimalNaoEncontradoException(String message) {
        super(message);
    }
}
