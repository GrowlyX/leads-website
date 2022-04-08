package org.riverdell.website.model

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope
import java.util.concurrent.CompletableFuture


/**
 * @author GrowlyX
 * @since 4/7/2022
 */
@Component
@SessionScope
open class WebsiteUserSession
{
    fun getUser(): CompletableFuture<WebsiteUser>
    {
        val authentication = SecurityContextHolder
            .getContext().authentication

        val principal =
            authentication.principal as OAuth2AuthenticatedPrincipal

        val email = principal
            .getAttribute<String>("email")
            ?: throw IllegalStateException(
                "OAuth principal does not contain email"
            )

        val name = principal
            .getAttribute<String>("name")
            ?: principal
                .getAttribute("given_name")
            ?: "New User"

        val picture = principal
            .getAttribute<String>("picture")!!

        return WebsiteUserRepository
            .getOrCreate(email, name, picture)

    }

    fun loggedIn(): Boolean
    {
        return SecurityContextHolder
            .getContext()
            .authentication != null
    }
}
