package abn.com.receipes.core.receipe;

import com.abn.receipe.models.Receipes;
import com.abn.receipe.models.Search;

public interface ReceipeService {
    Receipes getReceipes(Integer pageNumber, Integer pageSize, Search search);
}
