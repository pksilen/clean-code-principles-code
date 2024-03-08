package com.example;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Either<L, R> {
    private final Optional<L> maybeLeftValue;
    private final Optional<R> maybeRightValue;

    private Either(
            final Optional<L> maybeLeftValue,
            final Optional<R> maybeRightValue
    ) {
        this.maybeLeftValue = maybeLeftValue;
        this.maybeRightValue = maybeRightValue;
    }

    public static <L, R> Either<L, R> withLeft(
            final L value
    ) {
        return new Either<>(Optional.of(value), Optional.empty());
    }

    public static <L, R> Either<L, R> withRight(
            final R value
    ) {
        return new Either<>(Optional.empty(), Optional.of(value));
    }

    public boolean hasLeftValue() {
        return maybeLeftValue.isPresent();
    }

    public boolean hasRightValue() {
        return maybeRightValue.isPresent();
    }

    public <T> Either<T, R> mapLeft(
            Function<? super L, ? extends T> mapper
    ) {
        return new Either<>(maybeLeftValue.map(mapper),
                maybeRightValue);
    }

    public <T> Either<L, T> mapRight(
            Function<? super R, ? extends T> mapper)
    {
        return new Either<>(maybeLeftValue,
                maybeRightValue.map(mapper));
    }

    public <T> T map(
            Function<? super L, ? extends T> leftValueMapper,
            Function<? super R, ? extends T> rightValueMapper)
    {
        return maybeLeftValue.<T>map(leftValueMapper)
                .orElseGet(() ->
                        maybeRightValue.map(rightValueMapper).get());
    }

    public void apply(
            Consumer<? super L> leftValueConsumer,
            Consumer<? super R> rightValueConsumer
    ) {
        maybeLeftValue.ifPresent(leftValueConsumer);
        maybeRightValue.ifPresent(rightValueConsumer);
    }
}
