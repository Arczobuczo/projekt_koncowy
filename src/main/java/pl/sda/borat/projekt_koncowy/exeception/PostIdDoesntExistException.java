package pl.sda.borat.projekt_koncowy.exeception;

public class PostIdDoesntExistException extends RuntimeException {

    private final Long id;

    public PostIdDoesntExistException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Post id: %d doesn't exist", id);
    }
}
