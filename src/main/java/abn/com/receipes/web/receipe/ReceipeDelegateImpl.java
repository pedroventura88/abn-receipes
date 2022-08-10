package abn.com.receipes.web.receipe;

import abn.com.receipes.core.receipe.ReceipeService;
import com.abn.receipe.api.ReceipesApiDelegate;
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
    public ResponseEntity<Receipes> getReceipes(Integer pageNumber, Integer pageSize, Search search) {
        try {
            Receipes receipes = service.getReceipes(pageNumber, pageSize, search);
            if (Objects.isNull(receipes.getItems())) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(receipes);
        } catch (Exception e) {
            LOG.error("Some issue occurs on execution of getting receipe", e);
            throw new RuntimeException("TREAT HERE", e);
        }
    }
}
