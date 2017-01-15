package experiments.understanding.parser.combinators;

import java.util.Objects;
import java.util.function.BiFunction;

public abstract class Result<T> {

    public static <T> Result<T> failure(String messageFormat, Object... messageArguments) {
        return new Failure<>(messageFormat, messageArguments);
    }

    public static Result<Integer> success(int matched, String remaining) {
        return new Success<>(matched, remaining);
    }

    public static <T> Result<T> success(T matched, String remaining) {
        return new Success<>(matched, remaining);
    }

    private Result() {
    }

    public abstract <R> Result<R> onSuccess(BiFunction<T, String, Result<R>> mapper);

    private static class Failure<T> extends Result<T> {
        private final String message;

        private Failure(String messageFormat, Object... messageArguments) {
            this.message = String.format(messageFormat, messageArguments);
        }

        @Override
        public <R> Result<R> onSuccess(BiFunction<T, String, Result<R>> mapper) {
            return Result.failure(this.message);
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

    private static class Success<T> extends Result<T> {
        private final T matched;
        private final String remaining;

        private Success(T matched, String remaining) {
            super();
            this.matched = matched;
            this.remaining = remaining;
        }

        @Override
        public <R> Result<R> onSuccess(BiFunction<T, String, Result<R>> mapper) {
            return mapper.apply(matched, remaining);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Success success = (Success) o;
            return Objects.equals(matched, success.matched) &&
                    Objects.equals(remaining, success.remaining);
        }

        @Override
        public int hashCode() {
            return Objects.hash(matched, remaining);
        }

        @Override
        public String toString() {
            return "Success{" +
                    "matched='" + matched +
                    "', remaining='" + remaining + '\'' +
                    '}';
        }
    }
}
