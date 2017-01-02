package experiments.understanding.parser.combinators;

public class Parser {
    public static Result pChar(int match, String input) {
        if (input != null && input.length() > 0 && input.codePointAt(0) == match) {
            return Result.success(input.substring(1));
        } else {
            return Result.failure(input);
        }
    }
}
