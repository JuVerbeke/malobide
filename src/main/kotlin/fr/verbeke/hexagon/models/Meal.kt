package fr.verbeke.hexagon.models

import jakarta.annotation.Generated
import java.time.LocalDateTime
import java.util.*

class Meal(val id: UUID, private val date: LocalDateTime) {

    private val _dishes: MutableList<Dish> = mutableListOf()
    val dishes: List<Dish> = _dishes

    fun addDishes(dishes: List<Dish>): Meal {
        _dishes.addAll(dishes)
        return this
    }

    @Generated
    override fun toString(): String {
        return "Meal(id=$id, date=$date, _dishes=$_dishes, dishes=$dishes)"
    }
}
