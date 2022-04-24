package org.riverdell.website.users

/**
 * @author GrowlyX
 * @since 4/18/2022
 */
enum class WebsiteUserRole(
    val display: String
)
{
    DEFAULT("Default"),
    STAFF("Staff"),
    ADMIN("Administrator");

    infix fun over(
        role: WebsiteUserRole
    ): Boolean
    {
        return role.ordinal >= ordinal
    }
}
