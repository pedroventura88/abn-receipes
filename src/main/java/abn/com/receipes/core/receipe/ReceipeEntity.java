package abn.com.receipes.core.receipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "receipe")
public class ReceipeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "servings_nbr")
    private Integer servings;

    @Column(name = "ingredients")
    private String ingredients;

    @Transient
    private List<String> ingredientsList;

    @Column(name = "vegetarian_flag")
    private Boolean isVegetarian;

    @Column(name = "instructions")
    private String instructions;

    @Transient
    private List<String> instructionsList;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public List<String> getIngredientsList() {
        return Arrays.asList(ingredients.split("\\|")).stream().map(i -> i.trim()).collect(Collectors.toList());
    }

    public List<String> getInstructionsList() {
        return Arrays.asList(instructions.split("\\|")).stream().map(i -> i.trim()).collect(Collectors.toList());
    }

}
