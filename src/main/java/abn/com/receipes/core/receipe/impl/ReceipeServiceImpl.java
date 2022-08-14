package abn.com.receipes.core.receipe.impl;

import abn.com.receipes.core.receipe.ReceipeEntity;
import abn.com.receipes.core.receipe.ReceipeMapper;
import abn.com.receipes.core.receipe.ReceipeRepository;
import abn.com.receipes.core.receipe.ReceipeService;
import abn.com.receipes.web.exceptions.BadRequestException;
import abn.com.receipes.web.exceptions.ExceptionFormatter;
import abn.com.receipes.web.exceptions.NotFoundException;
import com.abn.receipe.models.Receipe;
import com.abn.receipe.models.ReceipeInput;
import com.abn.receipe.models.Receipes;
import com.abn.receipe.models.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Strings.isNullOrEmpty;


@Service
public class ReceipeServiceImpl implements ReceipeService {
    private static final Logger LOG = LoggerFactory.getLogger(ReceipeServiceImpl.class);

    private ReceipeRepository repository;

    ReceipeMapper mapper;

    private static final Integer DEFAULT_PG_SIZE = 10;


    public ReceipeServiceImpl(ReceipeRepository repository) {
        this.repository = repository;
        mapper = new ReceipeMapper();
    }

    @Override
    public Receipe createRecipe(ReceipeInput receipeInput) {
        LOG.info("Starting creation of new receipe");
        validateAttributes(receipeInput);

        ReceipeEntity entity = ReceipeEntity.builder()
                .name(receipeInput.getName())
                .servings(receipeInput.getServings())
                .isVegetarian(receipeInput.getIsVegetarian())
                .instructions(toString(receipeInput.getInstructions()))
                .ingredients(toString(receipeInput.getIngredients()))
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        repository.save(entity);
        LOG.info("Recipe '{}' created successfully with Id {}", entity.getName(), entity.getId());
        return mapper.toApi(entity);
    }

    @Override
    public void deleteRecipe(Integer recipeId) throws Exception {
        LOG.info("Starting deletion of receipeId {}", recipeId);
        Optional<ReceipeEntity> entity = repository.findById(recipeId);
        validateId(recipeId, entity);
        repository.delete(entity.get());
        LOG.info("Receipe {} with id {} deleted successfully", entity.get().getName(), entity.get().getId());
    }

    @Override
    public Receipe updateRecipe(Integer recipeId, ReceipeInput receipeInput) throws Exception {
        LOG.info("Starting update of receipeId {}", recipeId);
        Optional<ReceipeEntity> entity = repository.findById(recipeId);
        validateId(recipeId, entity);

        ReceipeEntity updatedEntity = ReceipeEntity.builder()
                .id(entity.get().getId())
                .updateTime(LocalDateTime.now())
                .createTime(entity.get().getCreateTime())
                .name(isNullOrEmpty(receipeInput.getName()) ? entity.get().getName() : receipeInput.getName())
                .ingredients(receipeInput.getIngredients().size() == 0 ? entity.get().getIngredients() : toString(receipeInput.getIngredients()))
                .instructions(receipeInput.getInstructions().size() == 0 ? entity.get().getInstructions() : toString(receipeInput.getInstructions()))
                .servings(Objects.isNull(receipeInput.getServings()) ? entity.get().getServings() : receipeInput.getServings())
                .isVegetarian(Objects.isNull(receipeInput.getIsVegetarian()) ? entity.get().getIsVegetarian() : receipeInput.getIsVegetarian())
                .build();

        repository.save(updatedEntity);
        LOG.info("Recipe with id {} updated successfully", updatedEntity.getId());
        return mapper.toApi(updatedEntity);
    }

    @Override
    public Receipes getReceipes(Integer pageNumber, Integer pageSize, Search search) {
        LOG.info("Getting receipes");
        if (pageSize == null) {
            pageSize = DEFAULT_PG_SIZE;
        }
        Pageable pageable = mapper.toPageable(pageNumber, pageSize).withSort(Sort.by(Sort.Order.asc("id")));

        ReceipeEntity probe = ReceipeEntity.builder()
                .instructions(search.getInstructions())
                .isVegetarian(search.getIsVegetarian())
                .servings(search.getServings())
                .name(search.getName())
                .ingredients(search.getIngredients())
                .build();

        ExampleMatcher containsIgnoreCase = ExampleMatcher.matching()
                .withMatcher("servings_nbr", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("ingredients", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("vegetarian_flag", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("instructions", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<ReceipeEntity> example = Example.of(probe, containsIgnoreCase);
        Page<ReceipeEntity> page = repository.findAll(example, pageable);
        if (Objects.isNull(page) || page.getTotalElements() == 0) {
            LOG.warn("No data found on database with the specific example search");
            return new Receipes();
        }

        LOG.info("Found {} records", page.getTotalElements());
        Receipes receipes = mapper.toReceipes(page);
        return receipes;
    }

    private void validateId(Integer recipeId, Optional<ReceipeEntity> entity) {
        if (Objects.isNull(entity) || !entity.isPresent()) {
            LOG.error("Id {} not found on database", recipeId);
            ExceptionFormatter ex = new ExceptionFormatter(HttpStatus.NOT_FOUND.value(), "Id " + recipeId + " not found on database", "Id:" + recipeId);
            throw new NotFoundException(ex.buildErrors());
        }
        LOG.info("Recipe {} is present", entity.get().getName());
    }

    private void validateAttributes(ReceipeInput receipeInput) {
        if (isNullOrEmpty(receipeInput.getName()) || Objects.isNull(receipeInput.getInstructions()) ||
                Objects.isNull(receipeInput.getIngredients()) || (receipeInput.getIngredients().size()) == 0 ||
                receipeInput.getInstructions().size() == 0 || Objects.isNull(receipeInput.getIsVegetarian()) ||
                Objects.isNull(receipeInput.getServings())) {
            LOG.error("There is some invalid attribute values {}", receipeInput.toString());
            ExceptionFormatter ex = new ExceptionFormatter(HttpStatus.BAD_REQUEST.value(), "Some of the mandatory fields are null or empty", receipeInput.toString());
            throw new BadRequestException(ex.buildErrors());
        }
        LOG.info("All attributes were validated");
    }


    private String toString(List<?> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(data -> {
            sb.append(data).append("|");
        });
        return sb.toString().endsWith("|") ? sb.deleteCharAt(sb.length() - 1).toString() : sb.toString();
    }

}
