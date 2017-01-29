package experiments.understanding.parser.combinators;

import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    default Parser<T> orElse(Parser<T> otherParser) {
        return (String input) -> apply(input).onFailure(() -> otherParser.apply(input));
    }

    static <T> Parser<T> empty() {
        return (String input) -> input == null || input.length() == 0
                ? Result.success(null, input) : Result.failure("Empty input expected");
    }

    @SafeVarargs
    static <T> Parser<T> choice(Parser<T>... parsers) {
        return choice(Stream.of(parsers));
    }

    static <T> Parser<T> choice(Stream<Parser<T>> parserStream) {
        return parserStream.reduce(empty(), Parser::orElse);
    }

    static Parser<Integer> anyOf(int... characters) {
        return anyOf(IntStream.of(characters));
    }

    static Parser<Integer> anyOf(IntStream characterStream) {
        return choice(characterStream.mapToObj(Parser::pChar));
    }

    static Parser<Integer> anyOfRangeClosed(int startInclusive, int endInclusive) {
        return anyOf(IntStream.rangeClosed(startInclusive, endInclusive));
    }
}
