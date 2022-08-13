package abn.com.receipes.web.receipe;

import abn.com.receipes.core.receipe.ReceipeService;
import abn.com.receipes.web.exceptions.BadRequestException;
import abn.com.receipes.web.exceptions.NotFoundException;
import com.abn.receipe.api.ReceipesApiDelegate;
import com.abn.receipe.models.Receipe;
import com.abn.receipe.models.ReceipeInput;
import com.abn.receipe.models.Receipes;
import com.abn.receipe.models.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ReceipeDelegateImpl implements ReceipesApiDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(ReceipeDelegateImpl.class);

    private ReceipeService service;

    public ReceipeDelegateImpl(ReceipeService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Void> deleteRecipe(Integer recipeId) {
        try {
            service.deleteRecipe(recipeId);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getErrors());
        } catch (Exception e) {
            LOG.error("Some issue occurs on execution of delete receipe", e);
            throw new RuntimeException("Some issue occurs on execution of delete receipe", e);
        }
    }

    @Override
    public ResponseEntity<Receipe> updateRecipe(Integer recipeId, ReceipeInput receipeInput) {
        try {
            Receipe receipe = service.updateRecipe(recipeId, receipeInput);
            return ResponseEntity.ok(receipe);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getErrors());
        } catch (Exception e) {
            LOG.error("Some issue occurs on execution of updating receipe", e);
            throw new RuntimeException("Some issue occurs on execution of updating receipe", e);
        }
    }

    @Override
    public ResponseEntity<Receipes> getReceipes(Integer pageNumber, Integer pageSize, Search search) {
        try {
            Receipes receipes = service.getReceipes(pageNumber, pageSize, search);
            if (Objects.isNull(receipes.getItems())) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(receipes);
        } catch (Exception e) {
            LOG.error("Some issue occurs on execution of getting receipe", e);
            throw new RuntimeException("Some issue occurs on execution of getting receipe", e);
        }
    }

    @Override
    public ResponseEntity<Receipe> createRecipe(ReceipeInput receipeInput) {
        try {
            Receipe receipe = service.createRecipe(receipeInput);
            return ResponseEntity.ok(receipe);
        }catch (BadRequestException e) {
            throw new BadRequestException(e.getErrors());
        }catch (Exception e) {
            LOG.error("Some issue occurs on execution recipe creation", e);
            throw new RuntimeException("Some issue occurs on execution recipe creation", e);
        }
    }
}
