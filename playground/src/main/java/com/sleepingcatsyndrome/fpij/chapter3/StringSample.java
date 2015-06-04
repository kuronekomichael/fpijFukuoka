package com.sleepingcatsyndrome.fpij.chapter3;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


class StringSample {

    public static char toChar(int aChar) {
        return Character.valueOf((char) aChar);
    }

    public static Boolean isNotDigit(Character ch) {
        return ! Character.isDigit(ch);
    }

    public static final Comparator<Person> compareAsc = (person1, person2) -> person1.ageDifference(person2);
    public static final Comparator<Person> compareDesc = compareAsc.reversed();
    public static final Comparator<Person> compareNameAplha = (person1, person2) ->
                                                            person1.getName().compareTo(person2.getName());

    public static final Function<Person, String> byName = person -> person.getName();
    public static final Function<Person, Integer> byAge = person -> person.getAge();
}
