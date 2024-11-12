package fr.verbeke.hexagon.models

import java.time.LocalDateTime
import java.util.*

class Meal(val id: UUID, private val date: LocalDateTime) {

    private val _dishes: MutableList<Dish> = mutableListOf()
    val dishes: List<Dish> = _dishes

    fun addDish(dish: Dish): Meal {
        _dishes.add(dish)
        return this
    }

    override fun toString(): String {
        return "Meal(id=$id, date=$date, _dishes=$_dishes, dishes=$dishes)"
    }
}
