package fr.verbeke.adapters.secondary.gateways.repositories.inmemory

import fr.verbeke.hexagon.gateways.repositories.MealRepository
import fr.verbeke.hexagon.models.Meal
import java.util.*

class InMemoryMealRepository : MealRepository {

    private val meals: MutableMap<UUID, Meal> = mutableMapOf()

    fun feedWith(meal: Meal) {
        meals[meal.id] = meal
    }

    fun getMeals(): Set<Meal> {
        return meals.values.toSet()
    }

    override fun getById(mealId: UUID): Meal {
        return meals[mealId] ?: throw NoSuchElementException("Meal with id $mealId not found")
    }

    override fun update(meal: Meal) {
        meals[meal.id] = meal
    }
}
