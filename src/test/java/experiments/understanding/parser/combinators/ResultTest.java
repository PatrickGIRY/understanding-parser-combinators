package experiments.understanding.parser.combinators;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultTest {

    @Test
    public void two_failures_with_different_remaining_are_not_equal() throws Exception {
        assertThat(Result.failure(null, "")).isNotEqualTo(Result.failure("", ""));
    }

    @Test
    public void two_failures_with_different_message_are_not_equal() throws Exception {
        assertThat(Result.failure(null, "Message 1")).isNotEqualTo(Result.failure(null, "Message 2"));
    }
}