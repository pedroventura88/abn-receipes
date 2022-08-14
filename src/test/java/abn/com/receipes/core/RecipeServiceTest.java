package abn.com.receipes.core;


import abn.com.receipes.core.db.DBMock;
import abn.com.receipes.core.receipe.ReceipeMapper;
import abn.com.receipes.core.receipe.ReceipeRepository;
import abn.com.receipes.core.receipe.ReceipeService;
import com.abn.receipe.models.Receipes;
import com.abn.receipe.models.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecipeServiceTest {

    @Autowired
    private ReceipeService service;

    @Autowired
    private ReceipeRepository repository;

    private DBMock mockData;

    @InjectMocks
    private ReceipeMapper mapper = new ReceipeMapper();

    @BeforeEach
    void setUp() {
        mockData = new DBMock(mapper);
        mockData.listRecipes().forEach(recipe -> repository.save(recipe));
    }

    @Test
    void should_get_all_available_recipes() {
        Receipes receipes = service.getReceipes(1, 10, new Search());
        assertEquals(4, receipes.getItems().size());
    }

    @Test
    void should_get_all_available_vegetarian_recipes() {
        Receipes vegetarianReceipes = service.getReceipes(1, 10, vegetarianSearch());
        assertEquals(2, vegetarianReceipes.getItems().size());
    }

    @Test
    void should_get_recipes_that_serving_4_and_have_potatoes_as_ingredient() {
        Receipes recipesServing4andHavePotatoes = service.getReceipes(1,10, serving4andHavePotatoesSearch());
        assertEquals(1, recipesServing4andHavePotatoes.getItems().size());
        assertEquals(4, recipesServing4andHavePotatoes.getItems().get(0).getId());
    }

    private Search vegetarianSearch() {
        Search search = new Search();
        search.setIsVegetarian(true);
        return search;
    }

    private Search serving4andHavePotatoesSearch() {
        Search search = new Search();
        search.setServings(4);
        search.setIngredients("potatoes");
        return search;
    }

}
