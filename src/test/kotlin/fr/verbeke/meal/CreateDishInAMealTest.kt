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

class CreateDishInAMealTest {

    private lateinit var mealRepository: InMemoryMealRepository
    private lateinit var registerDishCommandHandler: RegisterDishCommandHandler
    private val tMealId: UUID = fromString("a750489f-1264-42ed-94ed-5a87290d0fa7")
    private val wMealId: UUID = fromString("7e7b8647-aa6e-44d7-8b5b-db873a3811ea")

    @BeforeEach
    fun setUp() {
        mealRepository = InMemoryMealRepository()
        registerDishCommandHandler = RegisterDishCommandHandler(mealRepository)
    }

    private fun createTuesdayMeal(): Meal {
        return Meal(
            tMealId,
            LocalDateTime.of(2024, Month.NOVEMBER, 12, 12, 53, 0)
        )
    }

    private fun createWednesdayMeal(): Meal {
        return Meal(
            wMealId,
            LocalDateTime.of(2024, Month.NOVEMBER, 13, 8, 27, 0)
        )
    }

    private fun assertMealContainsDishes(mealId: UUID, vararg expectedDishes: Dish) {
        val mealUT: Meal = mealRepository.getMeals().firstOrNull { it.id == mealId }
            ?: throw NoSuchElementException("Meal with id $mealId not found")
        assertThat(mealUT.dishes).containsExactly(*expectedDishes)
    }

    @Test
    fun `should not be able to add a dish if there is no meal`() {

        // Given
        val registerDishCommand = RegisterDishCommand(
            tMealId,
            listOf("Carrot")
        )

        // When / Then
        assertThatThrownBy {
            registerDishCommandHandler.handle(registerDishCommand)
        }
            .isInstanceOf(NoSuchElementException::class.java)
            .hasMessage("Meal with id $tMealId not found")
    }

    @Test
    fun `should not add a dish to the meal if that dish is empty`() {

        // Given
        val initialMeal = createTuesdayMeal()
        mealRepository.feedWith(initialMeal)

        // When
        val registerDishCommand = RegisterDishCommand(
            tMealId,
            emptyList()
        )

        // When / Then
        assertThatThrownBy {
            registerDishCommandHandler.handle(registerDishCommand)
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Dish must have at least one ingredient")
    }

    @Test
    fun `should not add a dish to the meal if the ingredient of that dish is empty`() {

        // Given
        mealRepository.feedWith(createTuesdayMeal())

        // When / Then
        val registerDishCommand = RegisterDishCommand(
            tMealId,
            listOf("")
        )
        assertThatThrownBy {
            registerDishCommandHandler.handle(registerDishCommand)
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Ingredient name must not be empty or blank")
    }

    @Test
    fun `should successfully add a dish of carrot to the meal`() {

        // Given
        val meal = createTuesdayMeal()
        mealRepository.feedWith(meal)

        // When
        val registerDishCommand = RegisterDishCommand(
            tMealId,
            listOf("Carrot")
        )
        registerDishCommandHandler.handle(registerDishCommand)

        // Then
        assertMealContainsDishes(meal.id, Dish(listOf(Ingredient("Carrot"))))
    }

    @Test
    fun `should successfully add a dish of potatoes and steak to the meal`() {

        // Given
        val meal = createTuesdayMeal()
        mealRepository.feedWith(meal)

        // When
        val registerDishCommand = RegisterDishCommand(
            tMealId,
            listOf("Potatoes", "Steak")
        )
        registerDishCommandHandler.handle(registerDishCommand)

        // Then
        assertMealContainsDishes(meal.id, Dish(listOf(Ingredient("Potatoes"), Ingredient("Steak"))))
    }

    @Test
    fun `should successfully add a dish of bread and nutella to a second meal`() {

        // Given
        mealRepository.feedWith(createTuesdayMeal())
        val meal = createWednesdayMeal()
        mealRepository.feedWith(meal)

        // When
        val registerDishCommand = RegisterDishCommand(
            wMealId,
            listOf("Bread", "Nutella")
        )
        registerDishCommandHandler.handle(registerDishCommand)

        // Then
        assertMealContainsDishes(meal.id, Dish(listOf(Ingredient("Bread"), Ingredient("Nutella"))))
    }

}
