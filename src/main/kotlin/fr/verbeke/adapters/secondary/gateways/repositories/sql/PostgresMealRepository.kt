package fr.verbeke.adapters.secondary.gateways.repositories.sql

import fr.verbeke.hexagon.gateways.repositories.MealRepository
import fr.verbeke.hexagon.models.Meal
import java.util.*

class PostgresMealRepository : MealRepository {
    override fun getById(mealId: UUID): Meal {
        TODO("Not yet implemented")
    }

    override fun save(addDish: Meal) {
        TODO("Not yet implemented")
    }
}
