package experiments.understanding.parser.combinators;

import java.util.Objects;

public class Result {
    private final String remaining;

    public static Result failure(String remaining, String messageFormat, Object... messageArguments) {
        return new Failure(remaining, messageFormat, messageArguments);
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

    private static class Failure extends Result {
        private final String message;

        private Failure(String remaining, String messageFormat, Object... messageArguments) {
            super(remaining);
            this.message = String.format(messageFormat, messageArguments);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Failure failure = (Failure) o;
            return Objects.equals(message, failure.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), message);
        }

        @Override
        public String toString() {
            return "Failure{" +
                    "remaining='" + super.remaining + '\'' +
                    "message='" + message + '\'' +
                    '}';
        }
    }
}
