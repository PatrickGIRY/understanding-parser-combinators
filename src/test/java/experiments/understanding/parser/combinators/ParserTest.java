package experiments.understanding.parser.combinators;


import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {

    private Parser<Integer> parseA;

    @Before
    public void setUp() throws Exception {
        parseA = Parser.pChar('A');

    }

    @Test
    public void should_return_failure_with_null_remaining_when_input_is_null() throws Exception {
        assertThat(parseA.apply(null)).isEqualTo(Result.failure("No more input"));
    }

    @Test
    public void should_return_failure_with_empty_remaining_when_input_is_empty() throws Exception {
        assertThat(parseA.apply("")).isEqualTo(Result.failure("No more input"));
    }

    @Test
    public void should_return_success_with_remaining_when_input_start_by_A() throws Exception {
        assertThat(parseA.apply("ABC")).isEqualTo(Result.success('A', "BC"));
    }

    @Test
    public void should_return_success_with_remaining_when_input_start_by_E() throws Exception {
        Parser parseE = Parser.pChar('E');
        assertThat(parseE.apply("EBC")).isEqualTo(Result.success('E', "BC"));
    }

    @Test
    public void should_return_failure_with_input_as_remaining_when_input_not_start_by_A() throws Exception {
        assertThat(parseA.apply("ZBC")).isEqualTo(Result.failure("Expecting A, got Z"));
    }

    @Test
    public void should_return_success_with_pair_and_remaining_when_input_start_by_AB() throws Exception {
        Parser<Integer> parseB = Parser.pChar('B');
        Parser<Pair<Integer, Integer>> parseAB = parseA.andThen(parseB);

        assertThat(parseAB.apply("ABC")).isEqualTo(Result.success(Pair.of('A', 'B'), "C"));
    }

    @Test
    public void should_return_success_with_pair_and_remaining_when_input_start_by_ABC() throws Exception {
        Parser<Integer> parseB = Parser.pChar('B');
        Parser<Pair<Integer, Integer>> parseAB = parseA.andThen(parseB);
        Parser<Integer> parseC = Parser.pChar('C');
        Parser<Pair<Pair<Integer, Integer>, Integer>> parseABC = parseAB.andThen(parseC);

        assertThat(parseABC.apply("ABCD")).isEqualTo(Result.success(Pair.of(Pair.of('A', 'B'), 'C'), "D"));
    }


    @Test
    public void should_return_failure_with_input_as_remaining_when_input_not_start_by_AB() throws Exception {
        Parser<Integer> parseB = Parser.pChar('B');
        Parser<Pair<Integer, Integer>> parseAB = parseA.andThen(parseB);

        assertThat(parseAB.apply("ZBC")).isEqualTo(Result.failure("Expecting A, got Z"));
        assertThat(parseAB.apply("AZC")).isEqualTo(Result.failure("Expecting B, got Z"));
    }

    @Test
    public void should_return_success_with_expected_character_and_remaining_when_input_start_by_A_or_B() throws Exception {
        Parser<Integer> parseA = Parser.pChar('A');
        Parser<Integer> parseB = Parser.pChar('B');
        Parser<Integer> parseAorElseB = parseA.orElse(parseB);

        assertThat(parseAorElseB.apply("AZZ")).isEqualTo(Result.success('A', "ZZ"));
        assertThat(parseAorElseB.apply("BZZ")).isEqualTo(Result.success('B', "ZZ"));

    }

    @Test
    public void should_failure_with_input_as_remaining_when_input_not_start_by_A_or_B() throws Exception {
        Parser<Integer> parseA = Parser.pChar('A');
        Parser<Integer> parseB = Parser.pChar('B');
        Parser<Integer> parseAorElseB = parseA.orElse(parseB);

        assertThat(parseAorElseB.apply("CZZ")).isEqualTo(Result.failure("Expecting B, got C"));
    }

    @Test
    public void should_return_success_with_expected_characters_and_remaining_when_input_start_by_A_and_B_or_C() throws Exception {
        Parser<Integer> parseA = Parser.pChar('A');
        Parser<Integer> parseB = Parser.pChar('B');
        Parser<Integer> parseC = Parser.pChar('C');
        Parser<Pair<Integer, Integer>> parseAAndThenBorElseC = parseA.andThen(parseB.orElse(parseC));

        assertThat(parseAAndThenBorElseC.apply("ABZ")).isEqualTo(Result.success(Pair.of('A', 'B'), "Z"));
        assertThat(parseAAndThenBorElseC.apply("ACZ")).isEqualTo(Result.success(Pair.of('A', 'C'), "Z"));
    }

    @Test
    public void should_return_failure_with_input_as_remaining_when_input_not_start_by_A_and_B_or_C() throws Exception {
        Parser<Integer> parseA = Parser.pChar('A');
        Parser<Integer> parseB = Parser.pChar('B');
        Parser<Integer> parseC = Parser.pChar('C');
        Parser<Pair<Integer, Integer>> parseAAndThenBorElseC = parseA.andThen(parseB.orElse(parseC));

        assertThat(parseAAndThenBorElseC.apply("QBZ")).isEqualTo(Result.failure("Expecting A, got Q"));
        assertThat(parseAAndThenBorElseC.apply("AQZ")).isEqualTo(Result.failure("Expecting C, got Q"));
    }

    @Test
    public void should_return_success_with_empty_remaining_when_input_is_empty() throws Exception {
        assertThat(Parser.empty().apply("")).isEqualTo(Result.success(null, ""));
    }

    @Test
    public void should_return_success_with_null_remaining_when_input_is_null() throws Exception {
        assertThat(Parser.empty().apply(null)).isEqualTo(Result.success(null, null));
    }

    @Test
    public void should_return_failure_when_input_is_not_empty() throws Exception {
        assertThat(Parser.empty().apply("A")).isEqualTo(Result.failure("Empty input expected"));
    }

    @Test
    public void should_choice_between_parsers() throws Exception {
        Parser<Integer> parseA = Parser.pChar('A');
        Parser<Integer> parseB = Parser.pChar('B');
        Parser<Integer> parseC = Parser.pChar('C');
        Parser<Integer> choice = Parser.choice(parseA, parseB, parseC);

        assertThat(choice.apply("AZZ")).isEqualTo(Result.success('A', "ZZ"));
        assertThat(choice.apply("BZZ")).isEqualTo(Result.success('B', "ZZ"));
        assertThat(choice.apply("CZZ")).isEqualTo(Result.success('C', "ZZ"));
        assertThat(choice.apply("ZZZ")).isEqualTo(Result.failure("Expecting C, got Z"));
        assertThat(Parser.choice(parseA).apply("AZZ")).isEqualTo(Result.success('A', "ZZ"));
        assertThat(Parser.choice().apply("")).isEqualTo(Result.success(null, ""));
        assertThat(Parser.choice().apply("AZZ")).isEqualTo(Result.failure("Empty input expected"));
    }

    @Test
    public void should_parse_any_of_characters() throws Exception {
        Parser<Integer> anyOf = Parser.anyOf('A', 'B', 'C');

        assertThat(anyOf.apply("AZZ")).isEqualTo(Result.success('A', "ZZ"));
        assertThat(anyOf.apply("BZZ")).isEqualTo(Result.success('B', "ZZ"));
        assertThat(anyOf.apply("CZZ")).isEqualTo(Result.success('C', "ZZ"));
        assertThat(anyOf.apply("ZZZ")).isEqualTo(Result.failure("Expecting C, got Z"));
        assertThat(Parser.anyOf('A').apply("AZZ")).isEqualTo(Result.success('A', "ZZ"));
        assertThat(Parser.anyOf().apply("")).isEqualTo(Result.success(null, ""));
        assertThat(Parser.anyOf().apply("AZZ")).isEqualTo(Result.failure("Empty input expected"));
    }

    @Test
    public void should_return_success_when_input_start_by_lowercase() throws Exception {
        Parser<Integer> parseLowercase = Parser.anyOfRangeClosed('a', 'z');

        assertThat(parseLowercase.apply("aBC")).isEqualTo(Result.success('a', "BC"));
    }

    @Test
    public void should_return_failure_when_input_not_start_by_lowercase() throws Exception {
        Parser<Integer> parseLowercase = Parser.anyOfRangeClosed('a', 'z');

        assertThat(parseLowercase.apply("ABC")).isEqualTo(Result.failure("Expecting z, got A"));
    }

    @Test
    public void should_return_success_when_input_start_by_digit() throws Exception {
        Parser<Integer> parseDigit = Parser.anyOfRangeClosed('0', '9');

        assertThat(parseDigit.apply("1BC")).isEqualTo(Result.success('1', "BC"));
    }

    @Test
    public void should_return_failure_when_input_not_start_by_digit() throws Exception {
        Parser<Integer> parseDigit = Parser.anyOfRangeClosed('0', '9');

        assertThat(parseDigit.apply("ABC")).isEqualTo(Result.failure("Expecting 9, got A"));
    }
}