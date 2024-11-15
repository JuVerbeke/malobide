package fr.verbeke.hexagon.usecases

import fr.verbeke.hexagon.gateways.repositories.MealRepository
import fr.verbeke.hexagon.models.Dish
import fr.verbeke.hexagon.models.Ingredient
import fr.verbeke.hexagon.models.Meal
import jakarta.inject.Singleton

@Singleton
class RegisterDishCommandHandler(private val mealRepository: MealRepository) {

    fun handle(registerDishCommand: RegisterDishCommand) {
        val meal: Meal = mealRepository.getById(registerDishCommand.mealId)
        val dishIngredients: List<Ingredient> = registerDishCommand.ingredients.map { Ingredient(it) }
        mealRepository.update(meal.addDish(Dish(dishIngredients)))
    }

}
