package org.riverdell.website.frontend.views.user

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.vaadin.flow.component.avatar.Avatar
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.BeforeEnterObserver
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import org.riverdell.website.frontend.SiteLayout
import org.riverdell.website.loader.CompositeLoader
import org.riverdell.website.users.WebsiteUserRepository
import org.riverdell.website.users.WebsiteUserSession

/**
 * @author GrowlyX
 * @since 4/13/2022
 */
@Route(
    value = "user/:username",
    layout = SiteLayout::class
)
@AnonymousAllowed
class UserView : HorizontalLayout(), BeforeEnterObserver, CompositeLoader<String>
{
    override fun beforeEnter(
        event: BeforeEnterEvent
    )
    {
        val username = event
            .routeParameters
            .get("username")
            .orElse("")

        this.load(username)
    }

    private fun titled(
        subtitle: String
    ): Div
    {
        val div = Div()
        div.text = "User not found"
        div.element.style.set(
            "font-size", "xx-large"
        )

        val text = Paragraph()
        text.add(subtitle)

        return div
    }

    override fun load(t: String)
    {
        alignItems = FlexComponent
            .Alignment.CENTER

        if (t.isBlank())
        {
            add(
                titled(
                    "You must provide a user!"
                )
            )
            return
        }

        val user = WebsiteUserRepository
            .repository.retrieveAll()
            .firstOrNull {
                it.username.equals(t, true)
            }

        if (user == null)
        {
            add(
                titled(
                    "No user with the username \"$t\" was found!"
                )
            )
            return
        }

        add(
            Avatar(user.username, user.picture)
        )

        if (user.bannerPng != null)
        {
            add(
                Image(
                    "http://localhost:8080/resources/banners/${user.bannerPng}",
                    "Banner"
                )
            )
        }
    }
}
