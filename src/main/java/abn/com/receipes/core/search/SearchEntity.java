package abn.com.receipes.core.search;

import lombok.Data;

@Data
public class SearchEntity {
    private String instructions;
    private Boolean isVegetarian;
    private String ingredient;
    private Integer servings;
}
