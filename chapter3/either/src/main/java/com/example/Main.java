package com.example;

class Error extends RuntimeException {
}

public class Main {
    public static void main(String[] args) {
        final Either<Integer, Error> intOrError = Either.withLeft(3);
        final Either<Integer, Error> intOrError2 = Either.withRight(new Error());

        System.out.println(intOrError.hasLeftValue()); // Prints true
        System.out.println(intOrError2.hasRightValue()); // Prints true

        // Prints true
        System.out.println(
                intOrError.mapLeft(number -> number * 2).hasLeftValue()
        );

        // Prints 6
        System.out.println(
                intOrError.<Integer>map(number -> number * 2, error -> 0)
        );

        // Prints 0
        System.out.println(
                intOrError2.<Integer>map(number -> number * 2, error -> 0)
        );
    }
}
