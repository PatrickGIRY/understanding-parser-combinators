package experiments.understanding.parser.combinators;

public class Parser {

    private Parser() {
    }

    public static Result pChar(int match, String input) {
        if (input != null && input.length() > 0) {
            int first = input.codePointAt(0);
            if (first == match) {
                return Result.success(first, input.substring(1));
            } else {
                return Result.failure("Expecting %c, got %c", match, first);
            }
        } else {
            return Result.failure("No more input");
        }
    }
}
