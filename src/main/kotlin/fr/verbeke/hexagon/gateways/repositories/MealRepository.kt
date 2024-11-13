package fr.verbeke.hexagon.gateways.repositories

import fr.verbeke.hexagon.models.Meal
import java.util.*

interface MealRepository {
    fun getById(mealId: UUID): Meal
    fun update(addDish: Meal)
}
