package com.sleepingcatsyndrome.fpij.chapter4;

import java.math.BigDecimal;
import java.io.IOException;
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

class 純資産総額の計算 {
    private Function<String, BigDecimal> priceFinder;

    public 純資産総額の計算(final Function<String, BigDecimal> aPriceFinder) {
        priceFinder = aPriceFinder;
    }

    public BigDecimal 株式資産総額を算出(final String ticker, final int shares) {
        return priceFinder.apply(ticker).multiply(BigDecimal.valueOf(shares));
    }
}
