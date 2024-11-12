package fr.verbeke.meal

import fr.verbeke.adapters.secondary.gateways.repositories.inmemory.InMemoryMealRepository
import fr.verbeke.hexagon.models.Dish
import fr.verbeke.hexagon.models.Meal
import fr.verbeke.hexagon.usecases.RegisterDishCommand
import fr.verbeke.hexagon.usecases.RegisterDishCommandHandler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month
import java.util.*
import java.util.UUID.fromString

class MealTest {

    private val mealRepository: InMemoryMealRepository = InMemoryMealRepository()
    private val registerDishCommandHandler: RegisterDishCommandHandler = RegisterDishCommandHandler(mealRepository)
    private val mealId: UUID = fromString("a750489f-1264-42ed-94ed-5a87290d0fa7")

    @Test
    fun `should successfully add a dish of carrot to the meal`() {

        // Given
        mealRepository.feedWith(
            Meal(
                mealId,
                LocalDateTime.of(2024, Month.NOVEMBER, 12, 15, 53, 0)
            )
        )

        // When
        val registerDishCommand = RegisterDishCommand(
            mealId,
            Dish(
                "Carrot",
            )
        )
        registerDishCommandHandler.handle(registerDishCommand)

        // Then
        val actualMeals: Set<Meal> = mealRepository.getMeals()
        assertThat(actualMeals).hasSize(1)
        assertThat(actualMeals.first().id).isEqualTo(registerDishCommand.mealId)
        assertThat(actualMeals.first().dishes).hasSize(1)
        assertThat(actualMeals.first().dishes).containsExactly(Dish("Carrot"))
    }
}
