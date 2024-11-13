package fr.verbeke.hexagon.models

data class Dish(val ingredients: List<Ingredient>) {
    init {
        require(ingredients.isNotEmpty()) { "Dish must have at least one ingredient" }
    }
}
