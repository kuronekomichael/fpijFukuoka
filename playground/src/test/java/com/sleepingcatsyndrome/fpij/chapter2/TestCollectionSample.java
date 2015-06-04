package com.sleepingcatsyndrome.fpij.chapter2;

import static org.junit.Assert.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.List;
import java.util.Optional;

public class TestCollectionSample {

    final List<String> input = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

    final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
    final List<String> editors = Arrays.asList("Brian", "Jackie", "John", "Mike");
    final List<String> comrades = Arrays.asList("Kate", "Ken", "Nick", "Paula", "Zach");

    @Test
    public void mapを使った変換() {
        List<String> upperNames = CollectionSample.uppercaseNames(input);
        assertEquals(Arrays.asList("BRIAN", "NATE", "NEAL", "RAJU", "SARA", "SCOTT"), upperNames);
    }

    @Test
    public void filterを使った検索() {
        List<String> names = CollectionSample.startWithN(input);
        assertEquals(Arrays.asList("Nate", "Neal"), names);
    }

    @Test
    public void フィルタの再利用() {
        final Predicate<String> startWithN = CollectionSample.checkIfStartsWith("N");//name -> name.startsWith("N");

        final long countFriendsStartN = friends.stream().filter(startWithN).count();
        final long countEditorsStartN = editors.stream().filter(startWithN).count();
        final long countComradesStartN = comrades.stream().filter(startWithN).count();

        assertEquals(2, countFriendsStartN);
        assertEquals(0, countEditorsStartN);
        assertEquals(1, countComradesStartN);
    }

    @Test
    public void 類似フィルタ() {
        final Function<String, Predicate<String>> startWithLetter = (String letter) -> {
            return (String name) -> name.startsWith(letter);
        };

        final Predicate<String> startWithN = startWithLetter.apply("N");

        final long countFriendsStartN = friends.stream().filter(startWithN).count();
        final long countEditorsStartN = editors.stream().filter(startWithN).count();
        final long countComradesStartN = comrades.stream().filter(startWithN).count();

        assertEquals(2, countFriendsStartN);
        assertEquals(0, countEditorsStartN);
        assertEquals(1, countComradesStartN);
    }

    @Test
    public void 要素を１つ選択() {
        Optional<String> ret = CollectionSample.pickName(input, "S");

        String nameOfStartWithR = ret.orElse("");
        assertEquals("Sara", nameOfStartWithR);
    }

    @Test
    public void 数値の集約() {
        long total = CollectionSample.totalNumberOfCharacters(input);
        assertEquals(26, total);
    }

    @Test
    public void 文字列の集約() {
        String longestName = CollectionSample.longestName(input);
        assertEquals("Brian", longestName);

        String longestName2 = CollectionSample.longestName2(input);
        assertEquals("Brian", longestName2);
    }

    @Test
    public void 要素の結合() {
        assertEquals("Brian, Nate, Neal, Raju, Sara, Scott", CollectionSample.combineWithComma(input));
    }

    @Test
    public void 要素を結合2() {
        assertEquals("BRIAN, NATE, NEAL, RAJU, SARA, SCOTT", CollectionSample.combineWithCommaAndUpperCase(input));
    }
}
