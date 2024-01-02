package gamelogic;

public class GameError extends Exception {

    public GameError(String s) {
        super(s);
        printError();
    }

    public GameError(String s, Exception e) {
        super(s, e);
    }

    public void printError() {
        System.err.println(getMessage());
    }
}
