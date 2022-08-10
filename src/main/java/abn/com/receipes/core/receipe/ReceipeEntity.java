package abn.com.receipes.core.receipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor()
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
    @ElementCollection
    @CollectionTable(name = "ingredients_tb", joinColumns = @JoinColumn(name = "id"))
    private Set<String> ingredients;

    @Column(name = "vegetarian_flag")
    private Boolean isVegetarian;

    @Column(name = "instructions")
    @ElementCollection
    @CollectionTable(name = "instructions_tb", joinColumns = @JoinColumn(name = "id"))
    private Set<String> instructions;

}
