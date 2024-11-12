package fr.verbeke.hexagon.usecases

import fr.verbeke.hexagon.gateways.repositories.MealRepository
import fr.verbeke.hexagon.models.Meal

class RegisterDishCommandHandler(private val mealRepository: MealRepository) {

    fun handle(registerDishCommand: RegisterDishCommand) {
        val meal: Meal = mealRepository.getById(registerDishCommand.mealId)
        mealRepository.save(meal.addDish(registerDishCommand.dish))
    }

}
