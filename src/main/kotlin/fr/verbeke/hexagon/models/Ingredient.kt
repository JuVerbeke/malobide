package fr.verbeke.hexagon.models

data class Ingredient(val name: String) {
    init {
        require(name.isNotBlank()) { "Ingredient name must not be empty or blank" }
    }
}
