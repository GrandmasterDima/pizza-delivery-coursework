package com.pizza.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "recipes")
@Data
public class Recipe {

    @EmbeddedId
    private RecipeId id = new RecipeId();

    @ManyToOne
    @MapsId("pizzaId")
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "ingredient_amount", nullable = false, precision = 10, scale = 3)
    private BigDecimal amount;
}