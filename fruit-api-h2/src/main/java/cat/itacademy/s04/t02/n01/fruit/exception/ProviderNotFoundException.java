package cat.itacademy.s04.t02.n01.fruit.exception;

public class ProviderNotFoundException extends RuntimeException {
    public ProviderNotFoundException(Long id) {super("Provider not found with id: " + id);
    }
}
