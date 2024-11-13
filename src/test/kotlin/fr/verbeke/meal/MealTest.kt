package fr.verbeke.meal

import fr.verbeke.adapters.secondary.gateways.repositories.inmemory.InMemoryMealRepository
import fr.verbeke.hexagon.models.Dish
import fr.verbeke.hexagon.models.Ingredient
import fr.verbeke.hexagon.models.Meal
import fr.verbeke.hexagon.usecases.RegisterDishCommand
import fr.verbeke.hexagon.usecases.RegisterDishCommandHandler
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month
import java.util.*
import java.util.UUID.fromString

class MealTest {

    private lateinit var mealRepository: InMemoryMealRepository
    private lateinit var registerDishCommandHandler: RegisterDishCommandHandler
    private val mealId: UUID = fromString("a750489f-1264-42ed-94ed-5a87290d0fa7")

    @BeforeEach
    fun setUp() {
        mealRepository = InMemoryMealRepository()
        registerDishCommandHandler = RegisterDishCommandHandler(mealRepository)
    }

    private fun createTuesdayMeal(): Meal {
        return Meal(
            mealId,
            LocalDateTime.of(2024, Month.NOVEMBER, 12, 15, 53, 0)
        )
    }

    @Test
    fun `should not be able to add a dish if there is no meal`() {

        // Given
        val registerDishCommand = RegisterDishCommand(
            mealId,
            listOf("Carrot")
        )

        // When / Then
        assertThatThrownBy {
            registerDishCommandHandler.handle(registerDishCommand)
        }
            .isInstanceOf(NoSuchElementException::class.java)
            .hasMessage("Meal with id $mealId not found")
    }

    @Test
    fun `should successfully add a dish of carrot to the meal`() {

        // Given
        mealRepository.feedWith(createTuesdayMeal())

        // When
        val registerDishCommand = RegisterDishCommand(
            mealId,
            listOf("Carrot")
        )
        registerDishCommandHandler.handle(registerDishCommand)

        // Then
        val actualMeals: Set<Meal> = mealRepository.getMeals()
        assertThat(actualMeals).hasSize(1)
        assertThat(actualMeals.first().id).isEqualTo(registerDishCommand.mealId)
        assertThat(actualMeals.first().dishes).hasSize(1)
        assertThat(actualMeals.first().dishes).containsExactly(Dish(listOf(Ingredient("Carrot"))))
    }

    @Test
    fun `should not add a dish to the meal if that dish is empty`() {

        // Given
        val initialMeal = createTuesdayMeal()
        mealRepository.feedWith(initialMeal)

        // When
        val registerDishCommand = RegisterDishCommand(
            mealId,
            emptyList()
        )
        registerDishCommandHandler.handle(registerDishCommand)

        // Then
        val actualMeals: Set<Meal> = mealRepository.getMeals()
        assertThat(actualMeals).containsExactly(initialMeal)
    }

    @Test
    fun `should not add a dish to the meal if the ingredient of that dish is empty`() {

        // Given
        mealRepository.feedWith(createTuesdayMeal())

        // When / Then
        val registerDishCommand = RegisterDishCommand(
            mealId,
            listOf("")
        )
        assertThatThrownBy {
            registerDishCommandHandler.handle(registerDishCommand)
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Ingredient name must not be empty or blank")
    }

}
