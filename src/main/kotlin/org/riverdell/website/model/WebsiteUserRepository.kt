package org.riverdell.website.model

import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.DataStoreType
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * @author GrowlyX
 * @since 4/7/2022
 */
object WebsiteUserRepository
{
    val repository = DataHandler
        .linkTypeToId<WebsiteUser>("email")
        .withConnectionPool<NoAuthMongoConnectionPool> {
            this.databaseName = "website"
            this.hostname = "127.0.0.1"
            this.port = 27017
        }
        .createStoreType<String, WebsiteUser>(
            DataStoreType.MONGO
        )

    fun getOrCreate(
        email: String, firstName: String, picture: String
    ): CompletableFuture<WebsiteUser>
    {
        return repository.retrieveAsync(email)
            .thenApply {
                if (it == null)
                {
                    val user = WebsiteUser(
                        UUID.randomUUID(),
                        email, firstName, picture
                    )

                    this.repository.store(
                        email, user
                    )

                    return@thenApply user
                }

                return@thenApply it
            }
    }
}
