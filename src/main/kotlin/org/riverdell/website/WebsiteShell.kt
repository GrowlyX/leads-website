package org.riverdell.website

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.server.PWA
import org.riverdell.website.WebsiteShell.NAME
import org.riverdell.website.WebsiteShell.NAME_SHORT

/**
 * @author GrowlyX
 * @since 3/30/2022
 */
@PWA(
    name = NAME, shortName = NAME_SHORT
)
object WebsiteShell : AppShellConfigurator
{
    const val NAME = "RiverDell Leads Backbone"
    const val NAME_SHORT = "RDL Backbone"
}
