package org.riverdell.website.users

import java.awt.Color

/**
 * @author GrowlyX
 * @since 4/18/2022
 */
enum class WebsiteUserRole(
    val color: Color,
    val display: String
)
{
    DEFAULT(Color.GREEN, "Default"),
    STAFF(Color.CYAN, "Staff"),
    ADMIN(Color.RED, "Administrator");

    infix fun over(
        role: WebsiteUserRole
    ): Boolean
    {
        return role.ordinal >= ordinal
    }
}
