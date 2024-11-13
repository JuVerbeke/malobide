package fr.verbeke.adapters.primary.quarkus.controllers

data class DishDTO(
    val ingredients: List<IngredientDTO>
) {
    constructor() : this(emptyList())
}

data class IngredientDTO(
    val name: String
)
