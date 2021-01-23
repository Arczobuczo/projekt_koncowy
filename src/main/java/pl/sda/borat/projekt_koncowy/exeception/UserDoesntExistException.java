package pl.sda.borat.projekt_koncowy.exeception;

public class UserDoesntExistException extends RuntimeException {

    private String email;

    public UserDoesntExistException(String email) {
        this.email = email;
    }

    @Override
    public String getMessage() {
        return String.format("User doesn't exist %s", email);
    }
}
