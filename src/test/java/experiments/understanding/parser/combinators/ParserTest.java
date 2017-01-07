package experiments.understanding.parser.combinators;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {

    private Function<String, Result> parseA;

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
        Function<String, Result> parseE = Parser.pChar('E');
        assertThat(parseE.apply("EBC")).isEqualTo(Result.success('E', "BC"));
    }

    @Test
    public void should_return_failure_with_input_as_remaining_when_input_not_start_by_A() throws Exception {
        assertThat(parseA.apply("ZBC")).isEqualTo(Result.failure("Expecting A, got Z"));
    }
}
