package org.riverdell.website.security

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome

/**
 * @author GrowlyX
 * @since 4/7/2022
 */
enum class WebSecurityProvider(
    val enabled: Boolean = true
)
{
    Google,
    Facebook,
    Discord,

    // disabled
    Microsoft(
        enabled = false
    ),
    Twitter(
        enabled = false
    ),
    GitHub(
        enabled = false
    );

    val uri =
        "oauth2/authorization/${
            name.lowercase()
        }"

    val icon = FontAwesome
        .Brands.valueOf(
            name.uppercase()
        )
}
