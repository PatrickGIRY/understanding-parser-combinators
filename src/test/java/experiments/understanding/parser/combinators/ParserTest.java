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
        Parser<Pair<Integer, Integer>> parseAThenB = parseA.andThen(parseB);

        assertThat(parseAThenB.apply("ABC")).isEqualTo(Result.success(Pair.of('A', 'B'), "C"));
    }

    @Test
    public void should_return_success_with_pair_and_remaining_when_input_start_by_ABC() throws Exception {
        Parser<Integer> parseB = Parser.pChar('B');
        Parser<Pair<Integer, Integer>> parseAThenB = parseA.andThen(parseB);
        Parser<Integer> parseC = Parser.pChar('C');
        Parser<Pair<Pair<Integer, Integer>, Integer>> parseAThenBThenC = parseAThenB.andThen(parseC);

        assertThat(parseAThenBThenC.apply("ABCD")).isEqualTo(Result.success(Pair.of(Pair.of('A', 'B'), 'C'), "D"));
    }


    @Test
    public void should_return_failure_with_input_as_remaining_when_input_not_start_by_AB() throws Exception {
        Parser<Integer> parseB = Parser.pChar('B');
        Parser<Pair<Integer, Integer>> parseAThenB = parseA.andThen(parseB);

        assertThat(parseAThenB.apply("ZBC")).isEqualTo(Result.failure("Expecting A, got Z"));
        assertThat(parseAThenB.apply("AZC")).isEqualTo(Result.failure("Expecting B, got Z"));
    }
}
