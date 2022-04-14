package org.riverdell.website.security

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome

/**
 * @author GrowlyX
 * @since 4/7/2022
 */
enum class WebSecurityProvider(
    val uri: String,
    val identifier: String,
    val icon: FontAwesome.Brands,
    val enabled: Boolean = true
)
{
    GOOGLE(
        "/oauth2/authorization/google",
        "Google", FontAwesome.Brands.GOOGLE
    ),
    FACEBOOK(
        "/oauth2/authorization/facebook",
        "Facebook", FontAwesome.Brands.FACEBOOK_F
    ),

    DISCORD(
        "/oauth2/authorization/discord",
        "Discord", FontAwesome.Brands.DISCORD
    ),
    // disabled
    TWITTER(
        "/oauth2/authorization/twitter",
        "Twitter", FontAwesome.Brands.TWITTER, false
    ),
    GITHUB(
        "/oauth2/authorization/github",
        "GitHub", FontAwesome.Brands.GITHUB, false
    ),
}
