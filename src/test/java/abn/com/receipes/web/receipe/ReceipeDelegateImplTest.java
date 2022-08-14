package abn.com.receipes.web.receipe;

import abn.com.receipes.core.receipe.ReceipeService;
import abn.com.receipes.web.exceptions.NotFoundException;
import com.abn.receipe.api.ReceipesApiDelegate;
import com.abn.receipe.models.Error;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceipeDelegateImplTest {

    @InjectMocks
    private ReceipeDelegateImpl delegate;

    @Mock
    private ReceipeService service;

    MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
    }

    @Test
    void should_delete_recipe_if_id_is_found() throws Exception {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        doNothing().when(service).deleteRecipe(1);
        ResponseEntity<Void> response = delegate.deleteRecipe(1);
        assertEquals(204, response.getStatusCodeValue());
        verify(service, times(1)).deleteRecipe(1);
    }

    @Test
    void should_throw_not_found_exception_when_id_doesnt_exists() throws Exception {
        doThrow(NotFoundException.class).when(service).deleteRecipe(2);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            delegate.deleteRecipe(2);
        });
        List<Error> errors = exception.getErrors().getErrors();
        assertTrue(errors.get(0).getCode() == 404);
    }

    @Test
    void updateRecipe() {
    }

    @Test
    void getReceipes() {
    }

    @Test
    void createRecipe() {
    }
}
