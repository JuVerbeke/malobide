package fr.verbeke.adapters.secondary.gateways.repositories.sql

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "meals")
class MealEntity {

    @Id
    @Column(name = "id")
    lateinit var id: String

    @Column(name = "date")
    lateinit var date: String

    @Column(name = "dishes")
    var dishes: String? = null
}
