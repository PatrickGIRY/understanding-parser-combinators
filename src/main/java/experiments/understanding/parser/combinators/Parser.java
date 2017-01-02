package experiments.understanding.parser.combinators;

public class Parser {
    public static Result pCharA(String input) {
        if (input != null && !input.isEmpty()) {
            return Result.success(input.substring(1));
        } else {
            return Result.failure(input);
        }
    }
}
