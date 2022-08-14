package abn.com.receipes.core.db;

import abn.com.receipes.core.receipe.ReceipeEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DBMock {

    public List<ReceipeEntity> getRecipes() {
        ReceipeEntity easyPancakes = new ReceipeEntity();
        easyPancakes.setId(1);
        easyPancakes.setName("Easy pancake test");
        easyPancakes.setServings(12);
        easyPancakes.setIngredients("100g plain flour|2 large eggs|300ml milk|1 tbsp sunflower");
        easyPancakes.setIsVegetarian(true);
        easyPancakes.setInstructions("instruction1|instruction2|instruction3|instruction4");
        easyPancakes.setCreateTime(LocalDateTime.now());
        easyPancakes.setUpdateTime(LocalDateTime.now());

        ReceipeEntity chickenMassala = new ReceipeEntity();
        chickenMassala.setId(2);
        chickenMassala.setName("Chicken Massala test");
        chickenMassala.setServings(6);
        chickenMassala.setIngredients("1kg chicken|50mg chicken massala polder|honey");
        chickenMassala.setIsVegetarian(false);
        chickenMassala.setInstructions("instruction4|instruction5|instruction6|instruction7");
        chickenMassala.setCreateTime(LocalDateTime.now());
        chickenMassala.setUpdateTime(LocalDateTime.now());

        ReceipeEntity roastBeef = new ReceipeEntity();
        roastBeef.setId(3);
        roastBeef.setName("Roast Beef test");
        roastBeef.setServings(6);
        roastBeef.setIngredients("2kg topside of beef|1 tbsp vegetable oil or sunflower oil|1 tbsp English mustard powder");
        roastBeef.setIsVegetarian(false);
        roastBeef.setInstructions("instruction66|instruction54|instruction12|instruction44");
        roastBeef.setCreateTime(LocalDateTime.now());
        roastBeef.setUpdateTime(LocalDateTime.now());

        ReceipeEntity vegetarianFajitas = new ReceipeEntity();
        vegetarianFajitas.setId(4);
        vegetarianFajitas.setName("Vegetarian fajitas test");
        vegetarianFajitas.setServings(4);
        vegetarianFajitas.setIngredients("400g can black beans, drained|small bunch coriander, finely chopped|4 large or 8-12 small flour tortillas|potatoes");
        vegetarianFajitas.setIsVegetarian(true);
        vegetarianFajitas.setInstructions("fajita mix, firsts|black beans second|put into microwave|eat");
        vegetarianFajitas.setCreateTime(LocalDateTime.now());
        vegetarianFajitas.setUpdateTime(LocalDateTime.now());
        return Arrays.asList(easyPancakes, chickenMassala, roastBeef, vegetarianFajitas);
    }


}
