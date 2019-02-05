package com.przemek.recipe.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@ToString
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String url;

    @Lob
    private String directions;

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.EAGER)
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    public void setDescription(String description) {
        this.description = description;
    }


    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this); //for bidirectional relation
    }

    public void addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
    }

}
