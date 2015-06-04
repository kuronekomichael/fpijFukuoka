package com.sleepingcatsyndrome.fpij.chapter4;

import static org.junit.Assert.*;
import org.junit.Test;
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

public class TestDesignSample {

    final List<Asset> inputs = Arrays.asList(
        new Asset(Asset.AssetType.BOND, 123),
        new Asset(Asset.AssetType.STOCK, 40),
        new Asset(Asset.AssetType.BOND, 9273),
        new Asset(Asset.AssetType.BOND, 59)
    );

    @Test
    public void Assetの例() {
        assertEquals(9495, Asset.totalAssetValues(inputs));
    }

    @Test
    public void Asset集計でStrategyパターンを利用() {
        assertEquals(9455, Asset.totalAssetValues(inputs, asset -> asset.getType() == Asset.AssetType.BOND));
        assertEquals(40, Asset.totalAssetValues(inputs, asset -> asset.getType() == Asset.AssetType.STOCK));
    }

    @Test
    public void 委譲() {
        Function<String, BigDecimal> priceFinder = ticker -> ticker.equals("yahoo") ? new BigDecimal("6.01"): new BigDecimal("1.55");
        final 純資産総額の計算 calculator =  new 純資産総額の計算(priceFinder);
        final BigDecimal yahoo株式資産総額 = calculator.株式資産総額を算出("yahoo", 12030);
        final BigDecimal softbank株式資産総額 = calculator.株式資産総額を算出("softbank", 1900);

        assertEquals(new BigDecimal("72300.30"), yahoo株式資産総額);
        assertEquals(new BigDecimal("2945.00"), softbank株式資産総額);
    }

    @Test
    public void 例外処理１()  {
        List<String> paths = DesignSample.printPathsWitExceptionMessages();

        assertEquals(Arrays.asList("/usr", "/private/tmp"), paths);
    }

    @Test
    public void 例外処理２()  {
        List<String> paths = DesignSample.printPathsWithRuntimeException();

        assertEquals(Arrays.asList("/usr", "/private/tmp"), paths);
    }

    @Test
    public void 例外処理でUseInstanceを使う例()  {
        try {
            FileWriterEAM.use("sample.txt", writerEAM -> writerEAM.writeStuff("sweet"));
        } catch (IOException ie) {
            fail(ie.getMessage());
        }
    }


}
