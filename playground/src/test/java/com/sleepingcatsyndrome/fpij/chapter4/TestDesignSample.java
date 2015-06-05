package com.sleepingcatsyndrome.fpij.chapter4;

import static org.junit.Assert.*;
import org.junit.Test;
import java.math.BigDecimal;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.awt.Color;

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
    public void Decoratorパターン1() {
        final Camera camera = new Camera();
        final Consumer<String> printCaptured = (filterInfo) ->
            System.out.println(String.format("with %s: %s", filterInfo, camera.capture(new Color(200, 100, 200))));

        printCaptured.accept("no filter");
        //TODO: STDOUTを使わずに、テストにするべし
    }

    @Test
    public void Decoratorパターン2() {
        final Camera camera = new Camera();
        final Consumer<String> printCaptured = (filterInfo) ->
            System.out.println(String.format("with %s: %s", filterInfo, camera.capture(new Color(200, 100, 200))));

        camera.setFilters(Color::brighter);

        printCaptured.accept("brighter filter");

        camera.setFilters(Color::darker);

        printCaptured.accept("darker filter");
        //TODO: STDOUTを使わずに、テストにするべし
    }

    @Test
    public void Decoratorパターン3() {
        final Camera camera = new Camera();
        final Consumer<String> printCaptured = (filterInfo) ->
            System.out.println(String.format("with %s: %s", filterInfo, camera.capture(new Color(200, 100, 200))));

        camera.setFilters(Color::darker, Color::darker);

        printCaptured.accept("darker & darker filter");
        //TODO: STDOUTを使わずに、テストにするべし
    }

    @Test
    public void defaultメソッドで遊んでみる() {

        class SeaPlane extends Vehicle implements FastFly, Sail {
            private int altitude;
            public void cruise() {
                System.out.print("SearPlane::cruise currently cruise like: ");
                if (altitude > 0) {
                    FastFly.super.cruise();
                } else {
                    Sail.super.cruise();
                }
            }
        }

        SeaPlane seaPlane = new SeaPlane();
        seaPlane.takeOff();
        seaPlane.land();
        seaPlane.turn();
        seaPlane.cruise();
    }

    @Test
    public void 単純にチェーンにしてみる() {
        new Mailer().from("build@example.com")
                     .to("michael@example.com")
                     .subject("Build Notification")
                     .body("I am lier!!!")
                     .send();
    }

    @Test
    public void ラムダ式でより流暢に() {
        FluentMailer.send(mailer ->
            mailer.from("build@example.com")
                  .to("michael@example.com")
                  .subject("Build Notification")
                  .body("I am lier!!!"));
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
        //TODO: 本当にファイルは出力しないテストにするべし
    }


}
