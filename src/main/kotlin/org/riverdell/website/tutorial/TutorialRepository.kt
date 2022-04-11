package org.riverdell.website.tutorial

import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.DataStoreType
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import org.riverdell.website.repository.Repositories
import org.riverdell.website.users.WebsiteUser

/**
 * @author GrowlyX
 * @since 4/10/2022
 */
object TutorialRepository
{
    val repository = Repositories
        .store<Tutorial>("uniqueId")
}