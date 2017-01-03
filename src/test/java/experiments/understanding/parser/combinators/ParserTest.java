package experiments.understanding.parser.combinators;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {

    @Test
    public void should_return_failure_with_null_remaining_when_input_is_null() throws Exception {
        assertThat(Parser.pChar('A', null)).isEqualTo(Result.failure(null, "No more input"));
    }

    @Test
    public void should_return_failure_with_empty_remaining_when_input_is_empty() throws Exception {
        assertThat(Parser.pChar('A', "")).isEqualTo(Result.failure("", "No more input"));
    }

    @Test
    public void should_return_success_with_remaining_when_input_start_by_A() throws Exception {
        assertThat(Parser.pChar('A', "ABC")).isEqualTo(Result.success("BC"));
    }

    @Test
    public void should_return_success_with_remaining_when_input_start_by_E() throws Exception {
        assertThat(Parser.pChar('E', "EBC")).isEqualTo(Result.success("BC"));
    }

    @Test
    public void should_return_failure_with_input_as_remaining_when_input_not_start_by_A() throws Exception {
        assertThat(Parser.pChar('A', "ZBC")).isEqualTo(Result.failure("ZBC", "Expecting A, got Z"));
    }
}
