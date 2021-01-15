package pl.sda.borat.projekt_koncowy.exeception;

public class EmailExistInDatabaseException extends RuntimeException {

    private final String email;

    public EmailExistInDatabaseException(String email) {
        this.email = email;
    }

    @Override
    public String getMessage() {
        return String.format("E-mail: %s already exist in our service. Please use another one.", email);
    }
}
