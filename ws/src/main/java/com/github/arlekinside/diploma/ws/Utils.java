package com.github.arlekinside.diploma.ws;

public class Utils {

    public static <T> T nvl(T input, T def) {
        if (input == null) {
            return def;
        }
        return input;
    }

}
