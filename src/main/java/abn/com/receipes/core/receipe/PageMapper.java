package abn.com.receipes.core.receipe;

import com.abn.receipe.models.Page;
import org.springframework.data.domain.PageRequest;
import static java.lang.Math.max;

public class PageMapper {
    public Page toApiPage(org.springframework.data.domain.Page<?> page) {
        return new Page()
                .pageNumber(page.getNumber() + 1)
                .pageSize(page.getSize())
                .itemCount(Long.valueOf(page.getTotalElements()).intValue());
    }

    public PageRequest toPageable(Integer pageNumber, Integer pageSize) {
        return pageNumber != null ? PageRequest.of(max(1, pageNumber) -1, pageSize) : PageRequest.ofSize(pageSize);
    }
}
