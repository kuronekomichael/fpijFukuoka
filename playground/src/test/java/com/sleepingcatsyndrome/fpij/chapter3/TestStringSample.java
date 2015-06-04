package com.sleepingcatsyndrome.fpij.chapter3;

import static org.junit.Assert.*;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStringSample {
    final String input = "wO2Ot";

    final Person John_20 = new Person("John", 20);
    final Person Sara_21 = new Person("Sara", 21);
    final Person Greg_35 = new Person("Greg", 35);
    final Person Jane_21 = new Person("Jane", 21);

    final List<Person> people = Arrays.asList(
        John_20,
        Sara_21,
        Greg_35,
        Jane_21
    );

    @Test
    public void 文字列のイテレーション() {
        List<Character> ret = input.chars()
                                   .mapToObj(StringSample::toChar)
                                   //.filter(ch -> ! Character.isDigit(ch))
                                   .filter(StringSample::isNotDigit)
                                   .collect(Collectors.toList());

        assertEquals(Arrays.asList('w', 'O', 'O', 't'), ret);
    }

    @Test
    public void コンパレータを使ったソート1() {

        List<Person> sortedPeople =
                        people.stream()
                              .sorted((person1, person2) -> person1.getAge() - person2.getAge())
                              .collect(Collectors.toList());

        assertEquals(Arrays.asList(
            John_20,
            Sara_21,
            Jane_21,
            Greg_35
        ), sortedPeople);
    }

    @Test
    public void コンパレータを使ったソート2() {

        List<Person> sortedPeople =
                        people.stream()
                              .sorted(Person::ageDifference)
                              .collect(Collectors.toList());

        assertEquals(Arrays.asList(
            John_20,
            Sara_21,
            Jane_21,
            Greg_35
        ), sortedPeople);
    }

    @Test
    public void コンパレータの再利用() {
        List<Person> sortedPeople =
                        people.stream()
                              .sorted(StringSample.compareAsc)
                              .collect(Collectors.toList());

        assertEquals(Arrays.asList(
            John_20,
            Sara_21,
            Jane_21,
            Greg_35
        ), sortedPeople);
    }

    @Test
    public void コンパレータの再利用逆順() {
        List<Person> sortedPeople =
                        people.stream()
                              .sorted(StringSample.compareDesc)
                              .collect(Collectors.toList());

        assertEquals(Arrays.asList(
            Greg_35,
            Sara_21, //IMPORTANT: Comparatorの結果を逆転するだけなので、同値であるここは変わらない！
            Jane_21, //（同上）
            John_20
        ), sortedPeople);
    }

    @Test
    public void アルファベット順でソート() {
        List<Person> sortedPeople =
                        people.stream()
                              .sorted(StringSample.compareNameAplha)
                              .collect(Collectors.toList());

        assertEquals(Arrays.asList(
            Greg_35,
            Jane_21,
            John_20,
            Sara_21
        ), sortedPeople);
    }

    @Test
    public void 最年少を絞り込む() {
        Person youngestPerson = people.stream()
                                      .min(Person::ageDifference)
                                      .orElse(null);

        assertEquals(John_20, youngestPerson);
    }

    @Test
    public void 最年長を絞り込む() {
        Person youngestPerson = people.stream()
                                      .max(Person::ageDifference)
                                      .orElse(null);

        assertEquals(Greg_35, youngestPerson);
    }

    @Test
    public void より複雑な条件でソート() {
        List<Person> sortedPeople =
                        people.stream()
                              .sorted(Comparator.comparing(StringSample.byAge)
                                                    .thenComparing(StringSample.byName))
                              .collect(Collectors.toList());

        assertEquals(Arrays.asList(
            John_20,
            Jane_21,
            Sara_21,
            Greg_35
        ), sortedPeople);
    }

    @Test
    public void Collectorsクラスの使用() {
        // サプライヤ コンテナの生成方法(例: ArrayList::new)
        // アキュームレータ コンテナに単一追加する方法(例: ArrayList::add)
        // コンバイナ コンテナ同士を結合する方法(例: ArrayList::addAll)

        List<Person> olderThan20 = people.stream()
                                         .filter(person -> person.getAge() > 20)
                                         .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        assertEquals(Arrays.asList(
            Sara_21,
            Greg_35,
            Jane_21
        ), olderThan20);
    }

    @Test
    public void 年齢別の集計() {

        Function<List<Person>, String> list2string =
                                        list -> list.stream()
                                                    .map(Person::toString)
                                                    .collect(Collectors.joining(", "));

        Map<Integer, List<Person>> peopleByAge = people.stream()
                                         .collect(Collectors.groupingBy(Person::getAge));

        String age20s = list2string.apply(peopleByAge.get(20));
        String age21s = list2string.apply(peopleByAge.get(21));
        String age35s = list2string.apply(peopleByAge.get(35));

        assertEquals(age20s, "John - 20");
        assertEquals(age21s, "Sara - 21, Jane - 21");
        assertEquals(age35s, "Greg - 35");
    }

    @Test
    public void 年齢別の集計２() {

        Function<List<String>, String> list2string = list -> list.stream()
                                                                 .collect(Collectors.joining(", "));

        Map<Integer, List<String>> peopleByAge = people.stream()
                                         .collect(
                                            Collectors.groupingBy(Person::getAge,
                                                Collectors.mapping(Person::getName, Collectors.toList())));

        String age20s = list2string.apply(peopleByAge.get(20));
        String age21s = list2string.apply(peopleByAge.get(21));
        String age35s = list2string.apply(peopleByAge.get(35));

        assertEquals(age20s, "John");
        assertEquals(age21s, "Sara, Jane");
        assertEquals(age35s, "Greg");
    }

    @Test
    public void flatMapの使い方() {

        class Team {
            private String name;
            private List<String> members;
            public Team(String theName) {
                name = theName;
                members = new ArrayList<String>();
            }
            public String getName() { return name; }
            public Boolean hasMembers() { return (members.size() > 0); }
            public Team addMember(String memberName) { members.add(memberName); return this; }
            public List<String> getMembers() { return members; }
            public String toString() {
                return String.format("Team[%s](%s)", name, members.stream().collect(Collectors.joining(", ")));
            }
        }

        //FIXME: Stream.of()が使えるクラスで試した方がいいかなー
        List<Team> teams = Arrays.asList(
            new Team("青組").addMember("よしお").addMember("たかし"),
            new Team("赤組"),
            new Team("黄組").addMember("れいこ")
        );

        List<String> allNames = teams.stream()
                                     .flatMap(team -> team.hasMembers() ? team.getMembers().stream() : Stream.of(team.getName()))
                                     .collect(Collectors.toList());

        assertEquals(Arrays.asList("よしお", "たかし", "赤組", "れいこ"), allNames);
    }

}
