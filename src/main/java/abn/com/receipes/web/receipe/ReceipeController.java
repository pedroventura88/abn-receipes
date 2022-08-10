package abn.com.receipes.web.receipe;

import com.abn.receipe.api.ReceipesApiController;
import com.abn.receipe.api.ReceipesApiDelegate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ReceipeController extends ReceipesApiController {
    public ReceipeController(ReceipesApiDelegate delegate) {
        super(delegate);
    }
}
