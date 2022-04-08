package org.riverdell.website.security

/**
 * @author GrowlyX
 * @since 4/7/2022
 */
enum class WebSecurityProvider(
    val uri: String,
    val identifier: String,
    val icon: String = ""
)
{
    GOOGLE(
        "/oauth2/authorization/google",
        "Google"
    ),
    GITHUB(
        "/oauth2/authorization/github",
        "GitHub"
    )
}
