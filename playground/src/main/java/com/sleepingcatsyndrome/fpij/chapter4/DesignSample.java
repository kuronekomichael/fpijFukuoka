package com.sleepingcatsyndrome.fpij.chapter4;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DesignSample {

/*
    // コンパイルエラーになるパターン
    public static void printPaths() throws IOException {
        Stream.of("/usr", "/tmp")
              .map(path -> new File(path).getCanonicalPath()) // public String getCanonicalPath() throws IOException
              .forEach(System.out::println);
    }
//*/

    // ファイルパスと例外メッセージが混ざっとる
    public static List<String> printPathsWitExceptionMessages() {
        return Stream.of("/usr", "/tmp")
              .map(path -> {
                  try {
                      return new File(path).getCanonicalPath();
                  } catch (IOException e) {
                      return e.getMessage();
                  }
              })
              .collect(Collectors.toList());
    }

    // ラムダ式の並列実行時、特定のスレッドだけRuntimeExceptionで落ちることになる
    public static List<String> printPathsWithRuntimeException() {
        return Stream.of("/usr", "/tmp")
              .map(path -> {
                  try {
                      return new File(path).getCanonicalPath();
                  } catch (IOException e) {
                      throw new RuntimeException(e);
                  }
              })
              .collect(Collectors.toList());
    }
}
