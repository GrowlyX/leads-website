package org.riverdell.website.users

import org.riverdell.website.users.social.WebsiteUserSocialMedia
import java.util.UUID

/**
 * @author GrowlyX
 * @since 4/1/2022
 */
class WebsiteUser(
    val email: String,
    var username: String,
    var picture: String
)
{
    fun saveNow()
    {
        WebsiteUserRepository.repository
            .storeAsync(this.email, this)
    }

    val socialMedia =
        mutableMapOf<WebsiteUserSocialMedia, String>()

    var firstName = ""
    var lastName = ""

    var aboutMe = ""

    var bannerPng: UUID? = null
    var profilePng: UUID? = null

    var firstLogin = 0L

    var darkMode = false
    var role = WebsiteUserRole.DEFAULT
}
