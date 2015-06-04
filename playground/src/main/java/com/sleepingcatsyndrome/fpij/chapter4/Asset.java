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

public class Asset {
    public enum AssetType {
        BOND,
        STOCK
    };

    private final AssetType type;
    private final int value;

    public Asset(final AssetType assetType, final int assetValue) {
        type = assetType;
        value = assetValue;
    }
    public AssetType getType() { return type; }
    public int getValue() { return value; }

    public static int totalAssetValues(final List<Asset> assets) {
        //return assets.stream().mapToInt(Asset::getValue).sum();
        return totalAssetValues(assets, asset -> true);
    }

    public static int totalAssetValues(final List<Asset> assets, final Predicate<Asset> assetSelector) {
        return assets.stream().filter(assetSelector).mapToInt(Asset::getValue).sum();
    }

}
