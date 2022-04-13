package org.riverdell.website.users

import java.util.UUID

/**
 * @author GrowlyX
 * @since 4/1/2022
 */
class WebsiteUser(
    val uniqueId: UUID,
    val email: String,
    var username: String,
    val picture: String
)
{
    var firstName = ""
    var lastName = ""

    var bannerPng: UUID? = null

    fun isStaff() = WebsiteUserStaff
        .staffAccess.contains(this.email)
}
