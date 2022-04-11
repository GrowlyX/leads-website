package org.riverdell.website.repository

import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.DataStoreType
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import io.github.nosequel.data.store.StoreType

/**
 * @author GrowlyX
 * @since 4/10/2022
 */
object Repositories
{
    val handler = DataHandler
        .withConnectionPool<NoAuthMongoConnectionPool> {
            this.databaseName = "website"
            this.hostname = "127.0.0.1"
            this.port = 27017
        }

    inline fun <reified T> store(
        identifier: String
    ): StoreType<String, T>
    {
        this.handler.linkTypeToId<T>(identifier)

        return this.handler
            .createStoreType(DataStoreType.MONGO)
    }
}