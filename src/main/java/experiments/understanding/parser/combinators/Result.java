package experiments.understanding.parser.combinators;

import java.util.Objects;

public abstract class Result {

    public static Result failure(String messageFormat, Object... messageArguments) {
        return new Failure(messageFormat, messageArguments);
    }

    public static Result success(int matched, String remaining) {
        return new Success(matched, remaining);
    }

    private Result() {
    }

    private static class Failure extends Result {
        private final String message;

        private Failure(String messageFormat, Object... messageArguments) {
            this.message = String.format(messageFormat, messageArguments);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Failure failure = (Failure) o;
            return Objects.equals(message, failure.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(message);
        }

        @Override
        public String toString() {
            return "Failure{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }

    private static class Success extends Result {
        private final int matched;
        private final String remaining;

        private Success(int matched, String remaining) {
            super();
            this.matched = matched;
            this.remaining = remaining;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Success success = (Success) o;
            return matched == success.matched &&
                    Objects.equals(remaining, success.remaining);
        }

        @Override
        public int hashCode() {
            return Objects.hash(matched, remaining);
        }

        @Override
        public String toString() {
            return "Success{" +
                    "matched='" + (char) matched +
                    "', remaining='" + remaining + '\'' +
                    '}';
        }
    }
}
