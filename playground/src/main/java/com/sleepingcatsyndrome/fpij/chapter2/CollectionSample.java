package com.sleepingcatsyndrome.fpij.chapter2;

import java.math.BigDecimal;
import java.util.function.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


class CollectionSample {

    public static List<String> uppercaseNames(List<String> names) {
        return names.stream()
                    .map(name -> name.toUpperCase())
                    .collect(Collectors.toList());
    }

    public static List<String> startWithN(List<String> names) {
        return names.stream()
                    .filter(name -> name.startsWith("N"))
                    .collect(Collectors.toList());
    }

    public static Predicate<String> checkIfStartsWith(final String letter) {
        return name -> name.startsWith(letter);
    }

    public static final Optional<String> pickName(List<String> names, final String startingLetter) {
        return names.stream()
                    .filter(name -> name.startsWith(startingLetter))
                    .findFirst();
    }

    public static long totalNumberOfCharacters(List<String> names) {
        return names.stream()
                    .mapToInt(name -> name.length())
                    .sum();
    }

    public static String longestName(List<String> names) {
        return names.stream()
                    .reduce((name1, name2) -> name1.length() >= name2.length() ? name1 : name2)
                    .orElse("");
    }

    public static String longestName2(List<String> names) {
        return names.stream()
                    .reduce("", (name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
    }

    public static String combineWithComma(List<String> names) {
        return String.join(", ", names);
    }

    public static String combineWithCommaAndUpperCase(List<String> names) {
        return names.stream()
                    .map(String::toUpperCase)
                    .collect(Collectors.joining(", "));
    }
}
