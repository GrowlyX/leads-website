package org.riverdell.website

import com.vaadin.flow.component.dependency.NpmPackage
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.page.Meta
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.server.AppShellSettings
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import org.riverdell.website.WebsiteShell.META_TITLE
import org.riverdell.website.WebsiteShell.NAME
import org.riverdell.website.WebsiteShell.NAME_SHORT

/**
 * @author GrowlyX
 * @since 3/30/2022
 */
@PWA(
    name = NAME,
    shortName = NAME_SHORT
)
@NpmPackage(
    value = "line-awesome",
    version = "1.3.0"
)
@Theme(
    value = "leads",
    variant = Lumo.DARK
)
@PageTitle(META_TITLE)
object WebsiteShell : AppShellConfigurator
{
    const val NAME = "RDL Backbone"
    const val NAME_SHORT = "RDL Backbone"

    const val META_TITLE = "RD Leads"

    override fun configurePage(
        settings: AppShellSettings
    )
    {
        settings.addMetaTag("og:title", "RD Leads")
        settings.addMetaTag("og:description", "Something something something")

        settings.addLink("shortcut icon", "icons/favicon.ico")
        settings.addFavIcon("icon", "icons/icon-192.png", "192x192")
    }
}
