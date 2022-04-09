package org.riverdell.website.model

import java.util.UUID

/**
 * @author GrowlyX
 * @since 4/1/2022
 */
class WebsiteUser(
    val uniqueId: UUID,
    var email: String,
    val username: String,
    val picture: String
)
{
    var firstName = ""
    var lastName = ""

    fun isStaff() = WebsiteUserStaff
        .staffAccess.contains(this.email)
}
