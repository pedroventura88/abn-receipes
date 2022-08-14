package abn.com.receipes.core.receipe;

import com.abn.receipe.models.Receipe;
import com.abn.receipe.models.ReceipeInput;
import com.abn.receipe.models.Receipes;
import com.abn.receipe.models.Search;

public interface ReceipeService {
    Receipes getReceipes(Integer pageNumber, Integer pageSize, Search search);
    Receipe createRecipe(ReceipeInput receipeInput) throws Exception;
    void deleteRecipe(Integer recipeId) throws Exception;
    Receipe updateRecipe(Integer recipeId, ReceipeInput receipeInput) throws Exception;
}
