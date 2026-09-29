package com.br.pizzaria.core.domain.models;

import java.util.List;

public record StockCheckResult(
        boolean hasStock,
        List<String> missingIngredients
) {
    public static StockCheckResult success() {
        return new StockCheckResult(true, List.of());
    }

    public static StockCheckResult insufficient(List<String> missing) {
        return new StockCheckResult(false, missing);
    }
}
