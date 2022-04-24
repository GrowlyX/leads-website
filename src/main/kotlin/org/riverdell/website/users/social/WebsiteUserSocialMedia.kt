package org.riverdell.website.users.social

/**
 * @author GrowlyX
 * @since 4/19/2022
 */
enum class WebsiteUserSocialMedia(
    val fancy: String,
    val template: String
)
{
    TWITTER("Twitter", "https://twitter.com/%s"),
    GITHUB("GitHub", "https://github.com/%s"),
    WEBSITE("Website", "%s")
}
