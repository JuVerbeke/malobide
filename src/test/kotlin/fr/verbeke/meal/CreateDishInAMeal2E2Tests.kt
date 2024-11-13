package fr.verbeke.meal

import fr.verbeke.adapters.primary.quarkus.controllers.DishDTO
import fr.verbeke.adapters.primary.quarkus.controllers.IngredientDTO
import fr.verbeke.hexagon.gateways.repositories.MealRepository
import fr.verbeke.hexagon.models.Ingredient
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

@QuarkusTest
class CreateDishInAMeal2E2Tests {

    @Inject
    lateinit var repository: MealRepository

    var objectMapper: ObjectMapper = ObjectMapper()

    @Test
    fun `wip - should create a dish in a meal - inmemorydb`() {

        val myDishDTO = DishDTO(listOf(IngredientDTO("Carrot")))

        val asString = objectMapper.writeValueAsString(myDishDTO)
        given()
            .contentType(ContentType.JSON)
            .body(
                """
                {
                    "ingredients": [
                        {
                            "name": "Carrot"
                        }
                    ]
                }
            """.trimIndent()
            )
            .`when`()
            .post("/meals/a750489f-1264-42ed-94ed-5a87290d0fa7/dishes")
            .then()
            .statusCode(201)

        var meal = repository.getById(UUID.fromString("abc"))
        assertThat(meal.dishes).isNotEmpty
        assertThat(meal.dishes.size).isEqualTo(1)
        assertThat(meal.dishes[0].ingredients).containsExactly(Ingredient("Carrot"))
    }
}
