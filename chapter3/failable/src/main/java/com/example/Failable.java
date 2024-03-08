package com.example;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class Failable<T> {
    private final Either<T, RuntimeException> valueOrError;

    private Failable(
            final Either<T, RuntimeException> valueOrError
    ) {
        this.valueOrError = valueOrError;
    }

    public static <T> Failable<T> withValue(
            final T value
    ) {
        return new Failable<>(Either.withLeft(value));
    }

    public static <T> Failable<T> withError(
            final RuntimeException error
    ) {
        return new Failable<>(Either.withRight(error));
    }

    public T orThrow(
            final Class<? extends RuntimeException> ErrorClass
    ) {
        return valueOrError.map(
                (value) -> value,
                (error) -> {
                    try {
                        throw (RuntimeException)ErrorClass
                                .getConstructor(String.class)
                                .newInstance(error.getMessage());
                    } catch (InvocationTargetException |
                             InstantiationException |
                             IllegalAccessException |
                             IllegalArgumentException |
                             NoSuchMethodException exception) {
                        throw new RuntimeException(exception);
                    }
                });
    }

    public T orElse(final T otherValue) {
        return valueOrError.map(value -> value,
                error -> otherValue);
    }

    public <U> Failable<U> mapValue(
            final Function<? super T, ? extends U> mapper
    ) {
        return new Failable<>(valueOrError.mapLeft(mapper));
    }

    public Failable<T> mapError(
            final Function<? super RuntimeException,
                    ? extends RuntimeException> mapper
    ) {
        if (valueOrError.hasLeftValue()) {
            final var error =
                    new RuntimeException(mapper
                            .apply(new RuntimeException(""))
                            .getMessage());

            return Failable.withError(error);
        } else {
            return new Failable<>(valueOrError.mapRight(mapper));
        }
    }
}

