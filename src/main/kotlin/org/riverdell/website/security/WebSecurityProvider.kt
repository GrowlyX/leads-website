package org.riverdell.website.security

import com.vaadin.flow.component.icon.VaadinIcon

/**
 * @author GrowlyX
 * @since 4/7/2022
 */
enum class WebSecurityProvider(
    val uri: String,
    val identifier: String,
    val icon: VaadinIcon
)
{
    GOOGLE(
        "/oauth2/authorization/google",
        "Google", VaadinIcon.GOOGLE_PLUS_SQUARE
    ),
    FACEBOOK(
        "/oauth2/authorization/facebook",
        "Facebook", VaadinIcon.FACEBOOK
    ),
//    GITHUB(
//        "/oauth2/authorization/github",
//        "GitHub", "fa-brands fa-github"
//    ),
//    DISCORD(
//        "/oauth2/authorization/discord",
//        "Discord", "fa-brands fa-discord"
//    )
}
