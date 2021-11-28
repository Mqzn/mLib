package dev.mqzn.lib.utils;

import java.util.concurrent.ThreadLocalRandom;

public final class Numbers {

    private Numbers() {}

    public static boolean isDivisibleBy(int num, int divisor) {
        return num%divisor == 0;
    }

    public static boolean isEven(int num) {
        return num%2 == 0;
    }

    public static boolean isOdd(int num) {
        return num%2 != 0;
    }

    public static int generateRandomInt(int length) {
        StringBuilder gen = new StringBuilder();

        for (int i = 0; i < length; i++) {
            gen.append(ThreadLocalRandom.current().nextInt(10));
        }

        return Integer.parseInt(gen.toString());
    }


}
