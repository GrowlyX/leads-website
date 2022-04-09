package org.riverdell.website.security

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon

/**
 * @author GrowlyX
 * @since 4/7/2022
 */
enum class WebSecurityProvider(
    val uri: String,
    val identifier: String,
    val icon: FontAwesome.Brands
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
//    GITHUB(
//        "/oauth2/authorization/github",
//        "GitHub", "fa-brands fa-github"
//    ),
//    DISCORD(
//        "/oauth2/authorization/discord",
//        "Discord", "fa-brands fa-discord"
//    )
}
