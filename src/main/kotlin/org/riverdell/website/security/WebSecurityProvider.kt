package org.riverdell.website.security

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome.Brands

/**
 * @author GrowlyX
 * @since 4/7/2022
 */
enum class WebSecurityProvider(
    val enabled: Boolean = true,
    val iconRaw: Brands? = null
)
{
    Google,
    Facebook,
    Discord,
    Okta(
        iconRaw = Brands.USB
    ),

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

    val icon = iconRaw ?: FontAwesome
        .Brands.valueOf(
            name.uppercase()
        )
}
