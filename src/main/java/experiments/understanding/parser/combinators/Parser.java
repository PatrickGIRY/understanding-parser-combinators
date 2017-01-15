package experiments.understanding.parser.combinators;

@FunctionalInterface
public interface Parser<T> {

    static Parser<Integer> pChar(int match) {
        return (String input) -> {
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
        };
    }

    Result<T> apply(String input);

    default <U> Parser<Pair<T, U>> andThen(Parser<U> otherParser) {
        return (String input) -> apply(input) //
                .onSuccess((T match, String remaining) -> otherParser.apply(remaining)  //
                        .onSuccess((U match2, String remaining2) ->  //
                                Result.success(Pair.of(match, match2), remaining2)));
    }
}
