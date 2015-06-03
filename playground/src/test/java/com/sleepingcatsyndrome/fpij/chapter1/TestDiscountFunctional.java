package com.sleepingcatsyndrome.fpij.chapter1;

import static org.junit.Assert.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class TestDiscountFunctional {
    @Test
    public void test1() {
        List<BigDecimal> input = Arrays.asList(
            new BigDecimal("10"),
            new BigDecimal("30"),
            new BigDecimal("17"),
            new BigDecimal("20"),
            new BigDecimal("15"),
            new BigDecimal("18"),
            new BigDecimal("45"),
            new BigDecimal("12")
        );
        BigDecimal total = DiscountFunctional.totalOfDiscountedPrices(input);
        assertEquals(new BigDecimal("67.5"), total);
    }
}
