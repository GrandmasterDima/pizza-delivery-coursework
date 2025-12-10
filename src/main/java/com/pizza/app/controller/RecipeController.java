package com.pizza.app.controller;

import com.pizza.app.entity.Recipe;
import com.pizza.app.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RecipeController {

    private final RecipeRepository recipeRepository;

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping("/pizza/{pizzaId}")
    public List<Recipe> getIngredientsByPizzaId(@PathVariable Long pizzaId) {
        return recipeRepository.findByPizzaId(pizzaId);
    }

    @PostMapping
    public Recipe addIngredientToPizza(@RequestBody Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}