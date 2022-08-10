package abn.com.receipes.core.receipe.impl;

import abn.com.receipes.core.receipe.ReceipeEntity;
import abn.com.receipes.core.receipe.ReceipeMapper;
import abn.com.receipes.core.receipe.ReceipeRepository;
import abn.com.receipes.core.receipe.ReceipeService;
import com.abn.receipe.models.Receipes;
import com.abn.receipe.models.Search;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ReceipeServiceImpl implements ReceipeService {
    private static final Logger LOG = LoggerFactory.getLogger(ReceipeServiceImpl.class);

    private ReceipeRepository repository;

    ReceipeMapper mapper;

    private static final Integer DEFAULT_PG_SIZE = 10;


    public ReceipeServiceImpl(ReceipeRepository repository) {
        this.repository = repository;
        mapper = new ReceipeMapper();
        setUpData();
    }

    private void setUpData() {
        Set<String> ing1 = new HashSet<>(Arrays.asList("100g plain flour", "2 large eggs", "300ml milk", "1 tbsp sunflower"));
        Set<String> inst1 = new HashSet<>(Arrays.asList("instruction1", "instruction2", "instruction3", "instruction4"));
        ReceipeEntity easyPancakes = new ReceipeEntity(1, "Easy pancakes", 12, ing1, true, inst1);
        Set<String> ing2 = new HashSet<>(Arrays.asList("1kg chicken", "50mg chicken massala polder", "honey"));
        Set<String> inst2 = new HashSet<>(Arrays.asList("instruction4", "instruction5", "instruction6", "instruction7"));
        ReceipeEntity chickenMassala = new ReceipeEntity(2, "Chicken Massala", 6, ing2, true, inst2);
        List<ReceipeEntity> receipes = Arrays.asList(easyPancakes, chickenMassala);
        receipes.forEach(receipe -> repository.save(receipe));
        LOG.debug("Database ready to start");
    }

    @Override
    public Receipes getReceipes(Integer pageNumber, Integer pageSize, Search search) {
        if (pageSize == null) {
            pageSize = DEFAULT_PG_SIZE;
        }
        Pageable pageable = mapper.toPageable(pageNumber, pageSize).withSort(Sort.by(Sort.Order.asc("name")));

        ReceipeEntity probe = ReceipeEntity.builder()
                .name(Strings.isNullOrEmpty(search.getName()) ? null : search.getName())
                .ingredients(Strings.isNullOrEmpty(search.getIngredients()) ? null : new HashSet<>(Arrays.asList(search.getIngredients())))
                .servings(search.getServings())
                .isVegetarian(search.getIsVegetarian())
                .instructions(new HashSet<>(Arrays.asList(search.getInstructions())))
                .build();

        ExampleMatcher containsIgnoreCase = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("servings", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("isVegetarian", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("instructions", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("ingredients", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<ReceipeEntity> example = Example.of(probe, containsIgnoreCase);
        Page<ReceipeEntity> page = repository.findAll(example, pageable);
        if (Objects.isNull(page) || page.getTotalElements() == 0) {
            LOG.warn("No data found on database with the specific example search");
            return new Receipes();
        }

        Receipes receipes = mapper.toReceipes(page);
        return receipes;
    }

}
