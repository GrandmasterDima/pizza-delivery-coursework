package com.pizza.app.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class RecipeId implements Serializable {
    private Long pizzaId;
    private Long ingredientId;
}