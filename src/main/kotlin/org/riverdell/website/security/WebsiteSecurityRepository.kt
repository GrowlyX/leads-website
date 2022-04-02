package org.riverdell.website.security

import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.DataStoreType
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import org.riverdell.website.model.WebsiteUser
import java.util.UUID

/**
 * @author GrowlyX
 * @since 4/2/2022
 */
object WebsiteSecurityRepository
{
    val repository = DataHandler
        .linkTypeToId<WebsiteUser>("website")
        .withConnectionPool<NoAuthMongoConnectionPool> {
            this.databaseName = "honey"
            this.hostname = "127.0.0.1"
            this.port = 27017
        }
        .createStoreType<UUID, WebsiteUser>(
            DataStoreType.MONGO
        )
}
