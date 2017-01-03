package experiments.understanding.parser.combinators;

public class Parser {
    public static Result pChar(int match, String input) {
        if (input != null && input.length() > 0) {
            int first = input.codePointAt(0);
            if (first == match) {
                return Result.success(input.substring(1));
            } else {
                return Result.failure(input, "Expecting %c, got %c", match, first);
            }
        } else {
            return Result.failure(input, "No more input");
        }
    }
}
