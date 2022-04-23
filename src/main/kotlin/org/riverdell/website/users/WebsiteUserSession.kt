package org.riverdell.website.users

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope
import java.util.concurrent.CompletableFuture
import javax.management.Notification
import kotlin.random.Random

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

        val name = (principal
            .getAttribute<String>("name")
            ?: principal
                .getAttribute("given_name")
            ?: "new user ${
                Random.nextInt(1000000, 9999999)
            }").replace(
                " ", "-"
            )

        val picture = principal
            .getAttribute<String>("picture")
            ?: "https://ui-avatars.com/api/?name=${
                name.replace(" ", "+")
            }"

        return WebsiteUserRepository
            .getOrCreate(email, name, picture)
            .thenApply {
                val description = principal
                    .getAttribute<String>("description")
                    ?: ""

                it.aboutMe = description

                if (
                    WebsiteUserAdmins
                        .administrators
                        .contains(email)
                )
                {
                    it.role = WebsiteUserRole.ADMIN
                } else if (
                    WebsiteUserAdmins
                        .staff
                        .contains(email)
                )
                {
                    it.role = WebsiteUserRole.STAFF
                }

                if (it.role over WebsiteUserRole.STAFF)
                {
                    WebsiteUserRepository.repository
                        .storeAsync(it.email, it)
                }

                return@thenApply it
            }
    }

    fun loggedIn(): Boolean
    {
        val authentication = SecurityContextHolder
            .getContext().authentication

        return authentication != null &&
                authentication.principal is OAuth2AuthenticatedPrincipal
    }
}
