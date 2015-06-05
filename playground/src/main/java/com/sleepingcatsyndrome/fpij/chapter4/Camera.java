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

import java.awt.Color;

@SuppressWarnings("unchecked")
class Camera {
	private Function<Color, Color> filter;

	public Camera() {
		setFilters();
	}

	public void setFilters(final Function<Color, Color>... filters) {
		filter = Stream.of(filters)
					  .reduce((filter, next) -> filter.compose(next))
					  .orElse(color -> color);
	}

	public Color capture(final Color inputColor) {
		final Color processedColor = filter.apply(inputColor);
		//TODO: etc, etc..
		return processedColor;
	}

}
