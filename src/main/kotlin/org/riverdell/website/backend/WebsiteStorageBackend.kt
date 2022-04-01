package org.riverdell.website.backend

import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import org.riverdell.website.model.WebsiteUser
import org.springframework.stereotype.Service

/**
 * @author GrowlyX
 * @since 4/1/2022
 */
class WebsiteStorageBackend
{
    fun load()
    {
        DataHandler
            .linkTypeToId<WebsiteUser>("website")
            .withConnectionPool<NoAuthMongoConnectionPool> {
                this.databaseName = "honey"
                this.hostname = "127.0.0.1"
                this.port = 27017
            }
    }
}
