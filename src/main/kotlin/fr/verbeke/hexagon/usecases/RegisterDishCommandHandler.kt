package fr.verbeke.hexagon.usecases

import fr.verbeke.hexagon.gateways.repositories.MealRepository
import fr.verbeke.hexagon.models.Dish
import fr.verbeke.hexagon.models.Ingredient
import fr.verbeke.hexagon.models.Meal

class RegisterDishCommandHandler(private val mealRepository: MealRepository) {

    fun handle(registerDishCommand: RegisterDishCommand) {
        val meal: Meal = mealRepository.getById(registerDishCommand.mealId)
        val dishesToAddToMeal = registerDishCommand.dishes.map { Dish(listOf(Ingredient(it))) }
        val mealUpdated = meal.addDishes(dishesToAddToMeal)
        mealRepository.save(mealUpdated)
    }

}
