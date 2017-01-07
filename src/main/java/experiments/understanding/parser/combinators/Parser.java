package experiments.understanding.parser.combinators;

import java.util.function.Function;

public class Parser {

    private final Function<String, Result> innerFn;

    private Parser(Function<String, Result> innerFn) {
        this.innerFn = innerFn;
    }

    public static Parser pChar(int match) {
        return new Parser((String input) -> {
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
        });
    }

    public Result apply(String input) {
        return innerFn.apply(input);
    }
}
