package abn.com.receipes.web.receipe;

import abn.com.receipes.core.db.DBMock;
import abn.com.receipes.core.receipe.ReceipeMapper;
import abn.com.receipes.core.receipe.ReceipeService;
import abn.com.receipes.web.exceptions.NotFoundException;
import com.abn.receipe.models.Receipe;
import com.abn.receipe.models.ReceipeInput;
import com.abn.receipe.models.Receipes;
import com.abn.receipe.models.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceipeDelegateImplTest {

    @InjectMocks
    private ReceipeDelegateImpl delegate;

    @Mock
    private ReceipeService service;

    MockHttpServletRequest request;

    private DBMock mock;

    @InjectMocks
    private ReceipeMapper mapper = new ReceipeMapper();

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        mock = new DBMock(mapper);
    }

    @Test
    void should_delete_recipe_if_id_is_found_and_return_204() throws Exception {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        doNothing().when(service).deleteRecipe(1);
        ResponseEntity<Void> response = delegate.deleteRecipe(1);
        assertEquals(204, response.getStatusCodeValue());
        verify(service, times(1)).deleteRecipe(1);
    }

    @Test
    void should_throw_not_found_exception_when_id_doesnt_exists() throws Exception {
        doThrow(NotFoundException.class).when(service).deleteRecipe(2);

        Exception exception = assertThrows(NotFoundException.class, () -> {
            delegate.deleteRecipe(2);
        });
        assertEquals(NotFoundException.class, exception.getClass());
        verify(service, times(1)).deleteRecipe(2);
    }

    @Test
    void should_update_recipe_when_the_id_exists_on_database_and_return_200() throws Exception {
        Integer recipeId = 1;
        doReturn(updatedRecipe(recipeId, buildInput())).when(service).updateRecipe(recipeId, buildInput());
        ResponseEntity<Receipe> response = delegate.updateRecipe(1, buildInput());
        assertEquals(200, response.getStatusCodeValue());
        verify(service, times(1)).updateRecipe(recipeId, buildInput());
    }

    @Test
    void should_throw_not_found_exception_on_update_when_id_doesnt_exists() throws Exception {
        doThrow(NotFoundException.class).when(service).updateRecipe(20, buildInput());
        Exception exception = assertThrows(NotFoundException.class, () -> {
            delegate.updateRecipe(20, buildInput());
        });
        assertEquals(NotFoundException.class, exception.getClass());
        verify(service, times(1)).updateRecipe(20, buildInput());
    }

    @Test
    void should_get_all_recipes_when_no_search_parameter_is_passed_and_return_200() {
        doReturn(mock.getReceipes()).when(service).getReceipes(1, 10, new Search());
        ResponseEntity<Receipes> response = delegate.getReceipes(1,10,new Search());
        assertEquals(4,response.getBody().getItems().size());
        verify(service, times(1)).getReceipes(1,10, new Search());
    }

    @Test
    void createRecipe() throws Exception {
        doReturn(mock.getReceipes().getItems().get(0)).when(service).createRecipe(buildInput());
        ResponseEntity<Receipe> response = delegate.createRecipe(buildInput());
        assertEquals("Easy pancake test", response.getBody().getName());
        verify(service, times(1)).createRecipe(buildInput());
    }

    private Receipe updatedRecipe(Integer recipeId, ReceipeInput input) {
        Receipe recipe = new Receipe();
        recipe.setId(recipeId);
        recipe.setName(input.getName());
        recipe.setServings(input.getServings());
        recipe.setCreateTime(LocalDateTime.of(2022, 1, 1, 1, 1));
        recipe.setUpdateTime(LocalDateTime.of(2022, 1, 1, 1, 1));
        recipe.setIngredients(input.getIngredients());
        recipe.setInstructions(input.getInstructions());
        recipe.setIsVegetarian(input.getIsVegetarian());
        return recipe;
    }

    private ReceipeInput buildInput() {
        ReceipeInput input = new ReceipeInput();
        input.setName("Recipe 1");
        input.setIngredients(Arrays.asList("ingr1", "ingr2"));
        input.setInstructions(Arrays.asList("instr1", "instr2"));
        input.setIsVegetarian(true);
        input.setServings(2);
        return input;
    }

}
