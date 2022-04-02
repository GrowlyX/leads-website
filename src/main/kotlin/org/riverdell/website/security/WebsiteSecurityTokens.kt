package org.riverdell.website.security

import org.riverdell.website.model.WebsiteUser

/**
 * @author GrowlyX
 * @since 4/2/2022
 */
data class WebsiteSecurityTokens(
    val accessToken: String,
    val tokenType: String,
    val refreshToken: String,
    val user: WebsiteUser
)


