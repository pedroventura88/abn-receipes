package abn.com.receipes.core.db;

import abn.com.receipes.core.receipe.ReceipeEntity;
import abn.com.receipes.core.receipe.ReceipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public class DBDataInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(DBDataInitializer.class);

    private ReceipeRepository repository;

    public DBDataInitializer(ReceipeRepository repository) {
        this.repository = repository;
        setUpData();
    }

    private void setUpData() {
        ReceipeEntity easyPancakes = new ReceipeEntity();
        easyPancakes.setId(1);
        easyPancakes.setName("Easy pancake");
        easyPancakes.setServings(12);
        easyPancakes.setIngredients("100g plain flour|2 large eggs|300ml milk|1 tbsp sunflower");
        easyPancakes.setIsVegetarian(true);
        easyPancakes.setInstructions("instruction1|instruction2|instruction3|instruction4");
        easyPancakes.setCreateTime(LocalDateTime.now());
        easyPancakes.setUpdateTime(LocalDateTime.now());

        ReceipeEntity chickenMassala = new ReceipeEntity();
        chickenMassala.setId(2);
        chickenMassala.setName("Chicken Massala");
        chickenMassala.setServings(6);
        chickenMassala.setIngredients("1kg chicken|50mg chicken massala polder|honey");
        chickenMassala.setIsVegetarian(false);
        chickenMassala.setInstructions("instruction4|instruction5|instruction6|instruction7");
        chickenMassala.setCreateTime(LocalDateTime.now());
        chickenMassala.setUpdateTime(LocalDateTime.now());

        ReceipeEntity roastBeef = new ReceipeEntity();
        roastBeef.setId(3);
        roastBeef.setName("Roast Beef");
        roastBeef.setServings(6);
        roastBeef.setIngredients("2kg topside of beef|1 tbsp vegetable oil or sunflower oil|1 tbsp English mustard powder");
        roastBeef.setIsVegetarian(false);
        roastBeef.setInstructions("instruction66|instruction54|instruction12|instruction44");
        roastBeef.setCreateTime(LocalDateTime.now());
        roastBeef.setUpdateTime(LocalDateTime.now());


        List<ReceipeEntity> receipes = Arrays.asList(easyPancakes, chickenMassala, roastBeef);
        receipes.forEach(receipe -> repository.save(receipe));
        LOG.debug("Database ready");
    }


}
