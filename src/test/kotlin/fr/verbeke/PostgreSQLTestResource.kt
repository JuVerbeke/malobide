package fr.verbeke

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class PostgreSQLTestResource : QuarkusTestResourceLifecycleManager {

    private val postgresContainer = PostgreSQLContainer(DockerImageName.parse("postgres:latest"))

    override fun start(): Map<String, String> {
        postgresContainer.start()
        return mapOf(
            "quarkus.datasource.jdbc.url" to postgresContainer.jdbcUrl,
            "quarkus.datasource.username" to postgresContainer.username,
            "quarkus.datasource.password" to postgresContainer.password
        )
    }

    override fun stop() {
        postgresContainer.stop()
    }
}
