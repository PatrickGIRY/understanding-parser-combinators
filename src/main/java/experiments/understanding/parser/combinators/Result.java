package experiments.understanding.parser.combinators;

import java.util.Objects;

public class Result {
    private final String remaining;

    public static Result failure(String remaining) {
        return new Result(remaining);
    }

    public static Result success(String remaining) {
        return new Result(remaining);
    }

    private Result(String remaining) {
        this.remaining = remaining;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(remaining, result.remaining);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remaining);
    }

    @Override
    public String toString() {
        return "Result{" +
                "remaining='" + remaining + '\'' +
                '}';
    }
}
