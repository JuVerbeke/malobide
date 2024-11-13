package fr.verbeke.meal

import fr.verbeke.hexagon.models.Dish
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class DishTest {

    @Test
    fun `should have ingredient`() {
        assertThatThrownBy {
            Dish(listOf())
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Dish must have at least one ingredient")
    }
}
