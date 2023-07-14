public class IllegalWrittenIDException extends RuntimeException {           //wyjątek odnosi się do podania nieprawidłowego ID
    public IllegalWrittenIDException() {}
    public IllegalWrittenIDException(String text) {
        super(text);
    }
}
