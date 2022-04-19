package org.riverdell.website.users

import java.util.UUID

/**
 * @author GrowlyX
 * @since 4/1/2022
 */
class WebsiteUser(
    val email: String,
    var username: String,
    val picture: String
)
{
    var firstName = ""
    var lastName = ""

    var aboutMe = ""

    var bannerPng: UUID? = null

    var darkMode = false
    var role = WebsiteUserRole.DEFAULT
}
