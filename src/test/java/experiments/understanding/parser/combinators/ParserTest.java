package experiments.understanding.parser.combinators;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {

    @Test
    public void should_return_failure_with_null_remaining_when_input_is_null() throws Exception {
        assertThat(Parser.pCharA(null)).isEqualTo(Result.failure(null));
    }

}
