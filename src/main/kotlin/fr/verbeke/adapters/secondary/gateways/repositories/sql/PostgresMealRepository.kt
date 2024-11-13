package fr.verbeke.adapters.secondary.gateways.repositories.sql

import com.fasterxml.jackson.databind.ObjectMapper
import fr.verbeke.hexagon.gateways.repositories.MealRepository
import fr.verbeke.hexagon.models.Dish
import fr.verbeke.hexagon.models.Meal
import jakarta.inject.Singleton
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import java.time.LocalDateTime
import java.util.*

@Singleton
class PostgresMealRepository : MealRepository {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    var objectMapper: ObjectMapper = ObjectMapper()

    @Transactional
    override fun getById(mealId: UUID): Meal {

        val mealEntity: MealEntity = entityManager.find(MealEntity::class.java, mealId.toString())
            ?: throw NoSuchElementException("Meal with ID $mealId not found")

        return Meal(
            UUID.fromString(mealEntity.id),
            LocalDateTime.parse(mealEntity.date),
        )
    }

    @Transactional
    override fun update(meal: Meal) {

        val newMealEntity = toMealEntity(meal)

        val mealEntity: MealEntity = entityManager.find(MealEntity::class.java, newMealEntity.id)
            ?: throw NoSuchElementException("Meal with ID ${newMealEntity.id} not found")

        mealEntity.date = newMealEntity.date
        mealEntity.dishes = newMealEntity.dishes
    }

    private fun toMealEntity(meal: Meal): MealEntity {
        return MealEntity().apply {
            id = meal.id.toString()
            date = meal.date.toString()
            dishes = serializeDishesToJson(meal.dishes)
        }
    }

    private fun serializeDishesToJson(dishes: List<Dish>): String? {
        return objectMapper.writeValueAsString(dishes)
    }
}
