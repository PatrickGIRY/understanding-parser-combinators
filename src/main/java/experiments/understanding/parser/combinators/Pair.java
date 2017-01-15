package experiments.understanding.parser.combinators;

import java.util.Objects;

public class Pair<T, U> {
    private final T first;
    private final U second;

    public static Pair<Integer, Integer> of(int first, int second) {
        return new Pair<>(first, second);
    }

    public static <T> Pair<T, Integer> of(T first, int second) {
        return new Pair<>(first, second);
    }

    public static <T, U> Pair<T, U> of(T first, U second) {
        return new Pair<>(first, second);
    }

    private Pair(T first, U second) {

        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
