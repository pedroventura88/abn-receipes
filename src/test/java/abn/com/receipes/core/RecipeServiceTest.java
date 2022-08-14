package abn.com.receipes.core;


import abn.com.receipes.core.db.DBMock;
import abn.com.receipes.core.receipe.ReceipeEntity;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RecipeServiceTest {

    @Autowired
    private ReceipeService service;

    @MockBean
    private ReceipeRepository repository;

    private DBMock mockData;

    @InjectMocks
    private ReceipeMapper mapper = new ReceipeMapper();

    @BeforeEach
    void setUp() {
        mockData = new DBMock(mapper);
    }

    @Test
    void should_get_all_available_recipes() {
        doReturn(new PageImpl<>(mockData.listRecipes())).when(repository).findAll(any(), any(Pageable.class));
        Receipes receipes = service.getReceipes(1, 10, new Search());
        assertEquals(4, receipes.getItems().size());
        verify(repository, times(1)).findAll(any(), any(Pageable.class));
    }

//    @Test
//    void should_get_all_available_recipes() {
//        doReturn(new PageImpl<>(mockData.listRecipes())).when(repository).findAll(any(), any(Pageable.class));
//        Receipes receipes = service.getReceipes(1, 10, new Search());
//        assertEquals(4, receipes.getItems().size());
//        verify(repository, times(1)).findAll(any(), any(Pageable.class));
//    }

//    @Test
//    void should_get_all_available_vegetarian_recipes() {
//        doReturn(pageAllVegetarians()).when(repository).findAll(any(), any(Pageable.class));
//        Receipes receipes = service.getReceipes(1,10, new Search());
//        assertEquals(2, receipes.getItems().size());
//        verify(repository, times(1)).findAll(any(), any(Pageable.class));
//    }
//
//    @Test
//    void should_get_recipes_that_serving_4_and_have_potatoes_as_ingredient() {
//        doReturn(pageServing4AndHavePotatoes()).when(repository).findAll(any(), any(Pageable.class));
//        Receipes receipes = service.getReceipes(1, 10, new Search());
//        assertEquals(1, receipes.getItems().size());
//        assertEquals(4, receipes.getItems().get(0).getId());
//        verify(repository, times(1)).findAll(any(), any(Pageable.class));
//    }

    private Page<ReceipeEntity> pageServing4AndHavePotatoes() {
        List<ReceipeEntity> recipes = mockData.listRecipes().stream()
                .filter(recipe -> recipe.getServings() == 4)
                .filter(recipe -> recipe.getIngredients().contains("potatoes"))
                .collect(Collectors.toList());
        return new PageImpl<>(recipes);
    }

    private Page<ReceipeEntity> pageAllVegetarians() {
        List<ReceipeEntity> receipes = mockData.listRecipes().stream().filter(recipe -> recipe.getIsVegetarian().equals(true)).collect(Collectors.toList());
        return new PageImpl<>(receipes);
    }

}
