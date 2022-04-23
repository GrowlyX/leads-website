package org.riverdell.website.users

import org.riverdell.website.repository.Repositories
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * @author GrowlyX
 * @since 4/7/2022
 */
object WebsiteUserRepository
{
    val repository = Repositories
        .store<WebsiteUser>("email")

    fun getOrCreate(
        email: String, firstName: String, picture: String
    ): CompletableFuture<WebsiteUser>
    {
        return repository.retrieveAsync(email)
            .thenApply {
                if (it == null)
                {
                    val user = WebsiteUser(
                        email, firstName, picture
                    )

                    user.firstLogin =
                        System.currentTimeMillis()

                    this.repository.store(
                        email, user
                    )

                    return@thenApply user
                }

                return@thenApply it
            }
    }
}
