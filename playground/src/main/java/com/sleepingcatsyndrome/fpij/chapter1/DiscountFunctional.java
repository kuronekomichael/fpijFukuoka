package com.sleepingcatsyndrome.fpij.chapter1;

import java.math.BigDecimal;
import java.util.List;

class DiscountFunctional {
    public static BigDecimal totalOfDiscountedPrices(List<BigDecimal> prices) {
        //TODO: 未実装
        //return BigDecimal.ZERO;
        return prices.stream()
                     .filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
                     .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                     .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
