package fr.verbeke.adapters.primary.quarkus.controllers

import fr.verbeke.hexagon.usecases.RegisterDishCommand
import fr.verbeke.hexagon.usecases.RegisterDishCommandHandler
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.*

@Path("/meals")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class MealController {

    @Inject
    lateinit var registerDishCommandHandler: RegisterDishCommandHandler

    @POST
    @Path("/{mealId}/dishes")
    fun createDish(
        @PathParam("mealId") mealId: String,
        dishDTO: DishDTO
    ): Response {
        registerDishCommandHandler.handle(
            RegisterDishCommand(
                mealId = UUID.fromString(mealId),
                ingredients = dishDTO.ingredients.map { it.name }
            )
        )

        return Response.status(Response.Status.CREATED).build()
    }
}
